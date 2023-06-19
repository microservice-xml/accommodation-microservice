package com.example.accommodationmicroservice.service;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
@RequiredArgsConstructor
public class UserAccommodationGrpcService extends communication.UserAccommodationServiceGrpc.UserAccommodationServiceImplBase{

    private final AccommodationService accommodationService;
    private Logger logger = LoggerFactory.getLogger(UserAccommodationGrpcService.class);

    @Override
    public void checkForDelete(communication.UserIdRequest request,
                               io.grpc.stub.StreamObserver<communication.AccommodationResponse> responseObserver) {
        logger.trace("Request to check if user with id {} can be deleted was made", request.getId());
        responseObserver.onNext(accommodationService.CheckForDelete(request.getId()));
        responseObserver.onCompleted();
    }
}
