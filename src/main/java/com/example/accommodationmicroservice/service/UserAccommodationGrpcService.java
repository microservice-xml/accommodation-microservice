package com.example.accommodationmicroservice.service;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class UserAccommodationGrpcService extends communication.UserAccommodationServiceGrpc.UserAccommodationServiceImplBase{

    private final AccommodationService accommodationService;

    @Override
    public void checkForDelete(communication.UserIdRequest request,
                               io.grpc.stub.StreamObserver<communication.BooleanResponse> responseObserver) {
        responseObserver.onNext(accommodationService.CheckForDelete(request.getId()));
        responseObserver.onCompleted();
    }
}
