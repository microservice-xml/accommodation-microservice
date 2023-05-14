package com.example.accommodationmicroservice.service;

import com.example.accommodationmicroservice.dto.AccommodationSearchDto;
import com.example.accommodationmicroservice.model.Accommodation;
import communication.SearchAccommodationDto;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.accommodationmicroservice.mapper.AccommodationMapper.convertAccommodationToAccommodationGrpc;

@GrpcService
@RequiredArgsConstructor
public class AccommodationGrpcService extends communication.AccommodationServiceGrpc.AccommodationServiceImplBase {
    private final AccommodationService accommodationService;

    @Override
    public void findAllByUser(communication.UserId userId, StreamObserver<communication.ListAccommodation> responseStreamObserver) {
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
        AccommodationSearchDto dto = AccommodationSearchDto.builder()
                .location(searchAccommodationDto.getLocation())
                .guestCount(searchAccommodationDto.getGuestCount())
                .start(LocalDate.of(searchAccommodationDto.getStartYear(), searchAccommodationDto.getStartMonth(), searchAccommodationDto.getStartDay()))
                .end(LocalDate.of(searchAccommodationDto.getEndYear(), searchAccommodationDto.getEndMonth(), searchAccommodationDto.getEndDay()))
                .build();
        var accommodations = accommodationService.search(dto);
        List<communication.AccommodationFull> accommodationFulls = new ArrayList<>();
        for (Accommodation a: accommodations) {
            accommodationFulls.add(convertAccommodationToAccommodationGrpc(a));
        }
        var res = communication.ListAccommodation.newBuilder().addAllAccommodations(accommodationFulls).build();
        responseStreamObserver.onNext(res);
        responseStreamObserver.onCompleted();
    }
}
