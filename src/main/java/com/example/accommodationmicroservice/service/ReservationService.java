package com.example.accommodationmicroservice.service;

import com.example.accommodationmicroservice.model.Rate;
import com.example.accommodationmicroservice.model.Reservation;
import com.example.accommodationmicroservice.repository.RateRepository;
import communication.ListReservation;
import communication.LongId;
import communication.ReservationServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.accommodationmicroservice.mapper.ReservationMapper.convertReservationGrpcToReservation;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final RateRepository rateRepository;

    @Value("${reservation-api.grpc.address}")
    private String reservationApiGrpcAddress;

    private ReservationServiceGrpc.ReservationServiceBlockingStub getStub() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(reservationApiGrpcAddress, 9095)
                .usePlaintext()
                .build();
        return ReservationServiceGrpc.newBlockingStub(channel);
    }
    public List<Reservation> findAllByHostId(Long hostId){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(reservationApiGrpcAddress, 9095)
                .usePlaintext()
                .build();
        ReservationServiceGrpc.ReservationServiceBlockingStub blockingStub = ReservationServiceGrpc.newBlockingStub(channel);

        ListReservation reservations = blockingStub.findAllByHostId(LongId.newBuilder().setId(hostId).build());
        List<Reservation> retVal = new ArrayList<>();
        for(communication.Reservation res : reservations.getReservationsList()){
            retVal.add(convertReservationGrpcToReservation(res));
        }
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
        }
        return retVal;
    }
    public boolean checkReservationHistory(Long hostId, Long guestId) {
        List<Reservation> reservations = findAllByHostId(hostId);

        for(Reservation res : reservations){
            if(res.getUserId().equals(guestId)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCanUserRate(Long accommodationId, Long guestId) {
        List<Rate> rates = rateRepository.findAllByGuestIdAndAccommodationId(guestId, accommodationId);
        return rates.isEmpty();
    }
}
