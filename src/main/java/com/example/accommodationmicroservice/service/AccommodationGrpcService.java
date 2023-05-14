package com.example.accommodationmicroservice.service;

import com.example.accommodationmicroservice.mapper.AccommodationMapper;
import com.example.accommodationmicroservice.model.Accommodation;
import communication.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

import static com.example.accommodationmicroservice.mapper.AccommodationMapper.convertAccommodationToAccommodationGrpc;

@GrpcService
@RequiredArgsConstructor
public class AccommodationGrpcService extends AccommodationServiceGrpc.AccommodationServiceImplBase {
    private final AccommodationService accommodationService;

    @Override
    public void findAllByUser(UserId userId, StreamObserver<ListAccommodation> responseStreamObserver) {
        var accommodations = accommodationService.findAllByUser(userId.getUserId());
        List<AccommodationFull> accommodationFulls = new ArrayList<>();
        for (Accommodation a: accommodations) {
            accommodationFulls.add(convertAccommodationToAccommodationGrpc(a));
        }
        var res = communication.ListAccommodation.newBuilder().addAllAccommodations(accommodationFulls).build();
        responseStreamObserver.onNext(res);
        responseStreamObserver.onCompleted();
    }

    @Override
    public void addAccommodation(communication.AccommodationFull request,
                                 io.grpc.stub.StreamObserver<communication.MessageResponse> responseObserver) {
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
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9095)
                .usePlaintext()
                .build();
        AccommodationServiceGrpc.AccommodationServiceBlockingStub blockingStub =  AccommodationServiceGrpc.newBlockingStub(channel);
        MessageResponse res = blockingStub.addAccommodationToReservation(AccommodationRes.newBuilder().setAccId(acc.getId()).setCity(acc.getLocation()).build());

        System.out.println(res.getMessage());
    }

}
