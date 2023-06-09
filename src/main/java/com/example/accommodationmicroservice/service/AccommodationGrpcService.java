package com.example.accommodationmicroservice.service;

import com.example.accommodationmicroservice.mapper.AccommodationMapper;
import com.example.accommodationmicroservice.model.Accommodation;
import com.example.accommodationmicroservice.model.AccommodationDto;
import com.google.protobuf.Empty;
import communication.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.example.accommodationmicroservice.dto.AccommodationSearchDto;
import com.example.accommodationmicroservice.model.Accommodation;
import communication.SearchAccommodationDto;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.accommodationmicroservice.mapper.AccommodationMapper.*;

@GrpcService
@RequiredArgsConstructor
public class AccommodationGrpcService extends communication.AccommodationServiceGrpc.AccommodationServiceImplBase {
    private final AccommodationService accommodationService;

    @Value("${reservation-api.grpc.address}")
    private String reservationApiGrpcAddress;

    private Logger logger = LoggerFactory.getLogger(AccommodationGrpcService.class);

//    @Autowired
//    public AccommodationGrpcService(@Value("${reservation-api.grpc.address}") String reservationApiGrpcAddress, AccommodationService accommodationService) {
//        this.reservationApiGrpcAddress = reservationApiGrpcAddress;
//        this.accommodationService = accommodationService;
//    }

    @Override
    public void findAllByUser(communication.UserId userId, StreamObserver<communication.ListAccommodation> responseStreamObserver) {
        logger.trace("Request to find all accommodations with user id {} was made", userId.getUserId());
        var accommodations = accommodationService.findAllByUser(userId.getUserId());
        List<communication.AccommodationFull> accommodationFulls = new ArrayList<>();
        for (Accommodation a: accommodations) {
            accommodationFulls.add(convertAccommodationToAccommodationGrpc(a));
        }
        var res = communication.ListAccommodation.newBuilder().addAllAccommodations(accommodationFulls).build();
        responseStreamObserver.onNext(res);
        responseStreamObserver.onCompleted();
    }

    @Override
    public void search(communication.SearchAccommodationDto searchAccommodationDto, StreamObserver<communication.ListAccommodation> responseStreamObserver) {
        logger.trace("Request to search accommodations was made");
        AccommodationSearchDto dto = AccommodationSearchDto.builder()
                .location(searchAccommodationDto.getLocation())
                .guestCount(searchAccommodationDto.getGuestCount())
                .start(LocalDate.of(searchAccommodationDto.getStartYear(), searchAccommodationDto.getStartMonth(), searchAccommodationDto.getStartDay()))
                .end(LocalDate.of(searchAccommodationDto.getEndYear(), searchAccommodationDto.getEndMonth(), searchAccommodationDto.getEndDay()))
                .build();
        var accommodations = accommodationService.search(dto);
        List<communication.AccommodationFull> accommodationFulls = new ArrayList<>();
        for (AccommodationDto a: accommodations) {
            accommodationFulls.add(convertAccommodationDtoToAccommodationGrpc(a));
        }
        var res = communication.ListAccommodation.newBuilder().addAllAccommodations(accommodationFulls).build();
        responseStreamObserver.onNext(res);
        responseStreamObserver.onCompleted();
    }

    @Override
    public void addAccommodation(communication.AccommodationFull request,
                                 io.grpc.stub.StreamObserver<communication.MessageResponse> responseObserver) {
        logger.trace("Request to add an accommodation with name {} was made", request.getName());
        Accommodation accommodation = AccommodationMapper.convertAccommodationGrpcToAccommodation(request);
        Accommodation acc = accommodationService.addAccommodation(accommodation);
        sendAccommodationToReservation(acc);
        MessageResponse response;
        if(acc!=null)
            response = MessageResponse.newBuilder().setMessage("Accommodation created.").build();
        else
            response = MessageResponse.newBuilder().setMessage("Creating accommodation failed.").build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private void sendAccommodationToReservation(Accommodation acc) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(reservationApiGrpcAddress, 9095)
                .usePlaintext()
                .build();
        AccommodationServiceGrpc.AccommodationServiceBlockingStub blockingStub =  AccommodationServiceGrpc.newBlockingStub(channel);
        MessageResponse res = blockingStub.addAccommodationToReservation(AccommodationRes.newBuilder().setAccId(acc.getId()).setCity(acc.getLocation()).build());

        System.out.println(res.getMessage());
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
        }
    }

    @Override
    public void sendAccommodations(communication.UserId request,
                                   io.grpc.stub.StreamObserver<communication.ListAccommodation> responseObserver) {
        logger.trace("Request to send accommodations for user with id {} was made", request.getUserId());
        List<Accommodation> accommodations = accommodationService.findAllByUserId(request.getUserId());
        responseObserver.onNext(ListAccommodation.newBuilder()
                .addAllAccommodations(AccommodationMapper.convertAccommodationsToAccommodationsGrpc(accommodations))
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void findAll(communication.Empty request,
                        io.grpc.stub.StreamObserver<communication.ListAccommodation> responseObserver) {
        logger.trace("Request to find all accommodations was made");
        List<Accommodation> accommodations = accommodationService.findAll();
        ListAccommodation accList = ListAccommodation.newBuilder().addAllAccommodations(convertAccommodationsToAccommodationsGrpc(accommodations)).build();
        responseObserver.onNext(accList);
        responseObserver.onCompleted();
    }

    @Override
    public void findById(communication.UserIdRequest request,
                         io.grpc.stub.StreamObserver<communication.AccommodationWithGrade> responseObserver) {
        logger.trace("Request to find the accommodation with id {} was made", request.getId());
        Accommodation accommodation = accommodationService.findById(request.getId());
        responseObserver.onNext(AccommodationMapper.convertAccommodationToAccommodationWithGradeGrpc(accommodation));
        responseObserver.onCompleted();
    }

    @Override
    public void recommend(communication.UserId request,
                          io.grpc.stub.StreamObserver<communication.RecResponse> responseObserver){
        logger.trace("Request to find all recommended accommodations for user with id {} was made", request.getUserId());
        List<Accommodation> accommodations = accommodationService.recommend(request.getUserId());
        responseObserver.onNext(AccommodationMapper.convertToListAccommodations(accommodations));
        responseObserver.onCompleted();
    }

}
