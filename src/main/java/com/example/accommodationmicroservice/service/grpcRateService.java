package com.example.accommodationmicroservice.service;

import com.example.accommodationmicroservice.mapper.RateAccommodationMapper;
import com.example.accommodationmicroservice.model.Rate;
import com.example.accommodationmicroservice.repository.RateRepository;
import communication.MessageResponse;
import communication.rateServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

import static com.example.accommodationmicroservice.mapper.RateMapper.*;

@GrpcService
@RequiredArgsConstructor
public class grpcRateService extends rateServiceGrpc.rateServiceImplBase{

    private final RateService rateService;

    private final RateRepository rateRepository;

    @Override
    public void rateAccommodation(communication.AccommodationRate request,
                                  io.grpc.stub.StreamObserver<communication.MessageResponse> responseObserver) {
        Rate rate = RateAccommodationMapper.convertRateRequestToEntity(request);
        Rate r = rateService.rateAccommodation(rate);

        MessageResponse response;
        if(r!=null)
            response = MessageResponse.newBuilder().setMessage("Accommodation rate added.").build();
        else
            response = MessageResponse.newBuilder().setMessage("Adding accomomdation rate failed.").build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    @Override
    public void changeAccommodationRate(communication.AccommodationRate request,
                                        io.grpc.stub.StreamObserver<communication.AccommodationRate> responseObserver) {
        Rate rate = RateAccommodationMapper.convertRateRequestToEntityWithId(request);
        Rate r = rateService.changeAccommodationRate(rate);

        responseObserver.onNext(RateAccommodationMapper.convertFromMessageToRateWithId(r));
        responseObserver.onCompleted();
    }
    @Override
    public void deleteAccommodationRate(communication.UserIdRequest request,
                                        io.grpc.stub.StreamObserver<communication.AccommodationRate> responseObserver) {
        Rate rate = rateService.deleteAccommodationRate(request.getId());

        responseObserver.onNext(RateAccommodationMapper.convertFromMessageToRateWithId(rate));
        responseObserver.onCompleted();
    }

    @Override
    public void findAllByAccommodationId(communication.UserIdRequest request,
                                         io.grpc.stub.StreamObserver<communication.ListAccommodationRate> responseObserver) {
        List<Rate> rates = rateService.findAllByAccommodationId(request.getId());

        List<communication.AccommodationRate> convertedRates = RateAccommodationMapper.convertFromMessageListToRateWithIdList(rates);
        communication.ListAccommodationRate.Builder listRateResponseBuilder = communication.ListAccommodationRate.newBuilder();
        listRateResponseBuilder.addAllAccommodationRates(convertedRates);
        communication.ListAccommodationRate listRateResponse = listRateResponseBuilder.build();

        responseObserver.onNext(listRateResponse);
        responseObserver.onCompleted();
    }
}
