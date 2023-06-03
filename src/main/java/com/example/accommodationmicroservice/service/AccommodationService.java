package com.example.accommodationmicroservice.service;

import com.example.accommodationmicroservice.dto.AccommodationSearchDto;
import com.example.accommodationmicroservice.event.*;
import com.example.accommodationmicroservice.exception.AccommodationNotFound;
import com.example.accommodationmicroservice.model.Accommodation;
import com.example.accommodationmicroservice.repository.AccommodationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import communication.AccommodationServiceGrpc;
import communication.SearchRequest;
import communication.SearchResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationService {
    @Autowired
    AccommodationRepository accommodationRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<Accommodation> findAll(){
        return accommodationRepository.findAll();
    }
    public Accommodation addAccommodation(Accommodation accommodation){
        return accommodationRepository.save(accommodation);
    }

    public List<Accommodation> search(AccommodationSearchDto accommodationSearchDto) {
        // fix bug where date with 1 day before is sent
        accommodationSearchDto.setStart(accommodationSearchDto.getStart().plusDays(1));
        accommodationSearchDto.setEnd(accommodationSearchDto.getEnd().plusDays(1));
        var accommodations = accommodationRepository.findAllByLocationIgnoreCaseAndMinGuestsLessThanEqualAndMaxGuestsGreaterThanEqual(accommodationSearchDto.getLocation(), accommodationSearchDto.getGuestCount(), accommodationSearchDto.getGuestCount());
        if (accommodations.size() == 0) {
            return new ArrayList<>();
        }
        var accommodationIds = accommodations.stream().map(Accommodation::getId).toList();

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9095)
                .usePlaintext()
                .build();

        AccommodationServiceGrpc.AccommodationServiceBlockingStub blockingStub = AccommodationServiceGrpc.newBlockingStub(channel);
        SearchRequest req = SearchRequest
                .newBuilder()
                .setStartYear(accommodationSearchDto.getStart().getYear())
                .setStartMonth(accommodationSearchDto.getStart().getMonthValue())
                .setStartDay(accommodationSearchDto.getStart().getDayOfMonth())
                .setEndYear(accommodationSearchDto.getEnd().getYear())
                .setEndMonth(accommodationSearchDto.getEnd().getMonthValue())
                .setEndDay(accommodationSearchDto.getEnd().getDayOfMonth())
                .addAllAccommodationIds(accommodationIds)
                .build();
        SearchResponse response = blockingStub.searchByAvailabilityRange(req);
        return accommodationRepository.findAllById(response.getAccommodationIdsList());
    }

    public List<Accommodation> findAllByUser(long userId) {
        return accommodationRepository.findAllByUserId(userId);
    }

    public communication.BooleanResponse CheckForDelete(Long userId) {
        List<Accommodation> usersAccommodations = findAllByUser(userId);
        if (usersAccommodations.size() == 0) {
            publishMessage(new AccommodationDeleted(LocalDateTime.now(), EventType.ACCOMMODATION_DELETED));
            return communication.BooleanResponse.newBuilder().setAvailable(true).build();
        }
        var accommodationIds = usersAccommodations.stream().map(Accommodation::getId).toList();

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9095)
                .usePlaintext()
                .build();
        AccommodationServiceGrpc.AccommodationServiceBlockingStub blockingStub = AccommodationServiceGrpc.newBlockingStub(channel);

        communication.CheckDeleteRequest request = communication.CheckDeleteRequest
                .newBuilder()
                .addAllAccommodationIds(accommodationIds)
                .setStartYear(LocalDate.now().getYear())
                .setStartMonth(LocalDate.now().getMonthValue())
                .setStartDay(LocalDate.now().getDayOfMonth())
                .build();
        publishMessage(new AccommodationDeleteStarted(LocalDateTime.now(), EventType.DELETE_ACCOMMODATION_STARTED, accommodationIds));
        communication.BooleanResponse response = blockingStub.checkForDelete(request);

        if (response.getAvailable()) {
            deleteAccommodations(accommodationIds);
        } else {
            publishMessage(new AccommodationDeleteFailed(LocalDateTime.now(), EventType.DELETE_ACCOMMODATION_FAILED, userId));
        }

        return response;
    }

    /*@RabbitListener(queues = {"myQueue"})
    public void consume(String message) {
        try {
            BaseEvent baseEvent = objectMapper.readValue(message, BaseEvent.class);
            correspond(message, baseEvent.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void correspond(String jsonObject, EventType event) throws JsonProcessingException, JsonMappingException {
        switch(event) {
            case DELETE_SLOT_FAILED -> {
                SlotsDeleteFailed slotsFailedEvent = objectMapper.readValue(jsonObject, SlotsDeleteFailed.class);
                rollback(slotsFailedEvent.getAccommodationIds());
            }
            case DELETE_USER_STARTED -> {
                UserDeleteStarted startedEvent = objectMapper.readValue(jsonObject, UserDeleteStarted.class);
                deleteAccommodations(findAllByUserId(startedEvent.getUserId()).stream().map(Accommodation::getId).toList());
            }
        }
    }

    public void rollback(List<Long> accommodationIds) {

        Accommodation checkAccommodation = null;

        for(Long accId : accommodationIds) {
            Accommodation acc = accommodationRepository.findById(accId).get();
            acc.setDeleted(false);
            accommodationRepository.save(acc);

            checkAccommodation = acc;
        }

        if (checkAccommodation != null) {
            publishMessage(new AccommodationDeleteFailed(LocalDateTime.now(), EventType.DELETE_ACCOMMODATION_FAILED, checkAccommodation.getUserId()));
        }

    }

    public void deleteAccommodations(List<Long> accommodationIds) {
        for(Long accId : accommodationIds) {
            Accommodation acc = accommodationRepository.findById(accId).get();
            acc.setDeleted(true);
            accommodationRepository.save(acc);
        }

        publishMessage(new AccommodationDeleteStarted(LocalDateTime.now(), EventType.DELETE_ACCOMMODATION_STARTED, accommodationIds));
    }

    public List<Accommodation> findAllByUserId(Long userId) {
        return accommodationRepository.findAllByUserId(userId);
    }
    public void publishMessage(BaseEvent event){ // Assuming you have an instance of MyMessage
        try {
            String json = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend("myQueue", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Accommodation findById(Long id) {
        return accommodationRepository.findById(id).orElseThrow(()-> new AccommodationNotFound());
    }
}