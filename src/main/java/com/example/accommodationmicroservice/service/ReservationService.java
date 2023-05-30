package com.example.accommodationmicroservice.service;

import com.example.accommodationmicroservice.model.Reservation;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {
//    private ReservationServiceGrpc.ReservationServiceBlockingStub getStub() {
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9095)
//                .usePlaintext()
//                .build();
//        return ReservationServiceGrpc.newBlockingStub(channel);
//    }
//    public List<Reservation> findAllByHostId(Long hostId){
//        ReservationServiceGrpc.ReservationServiceBlockingStub blockingStub = getStub();
//
//        ListReservation reservations = blockingStub.findAllByHostId(LongId.newBuilder().setId(hostId).build());
//        List<Reservation> retVal = new ArrayList<>();
//        for(communication.Reservation res : reservations.getReservationsList()){
//            retVal.add(convertReservationGrpcToReservation(res));
//        }
//        return retVal;
//    }
//
//    public boolean checkReservationHistory(Long hostId, Long guestId) {
//        List<Reservation> reservations = findAllByHostId(hostId);
//
//        for(Reservation res : reservations){
//            if(res.getUserId().equals(guestId)) {
//                return true;
//            }
//        }
//        return false;
//    }

}
