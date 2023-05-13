package com.example.accommodationmicroservice.service;

import com.example.accommodationmicroservice.dto.AccommodationSearchDto;
import com.example.accommodationmicroservice.model.Accommodation;
import com.example.accommodationmicroservice.repository.AccommodationRepository;
import communication.AccommodationServiceGrpc;
import communication.SearchRequest;
import communication.SearchResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationService {
    @Autowired
    AccommodationRepository accommodationRepository;
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
}