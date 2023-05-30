package com.example.accommodationmicroservice.mapper;

import communication.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationStatusMapper {
    public static com.example.accommodationmicroservice.enums.ReservationStatus convertReservationStatusGrpcToReservationStatus(ReservationStatus status){
        if(status.equals(ReservationStatus.ACCEPTED))
            return  com.example.accommodationmicroservice.enums.ReservationStatus.ACCEPTED;
        else if(status.equals(ReservationStatus.PENDING))
            return  com.example.accommodationmicroservice.enums.ReservationStatus.PENDING;
        else if(status.equals(ReservationStatus.DECLINED))
            return  com.example.accommodationmicroservice.enums.ReservationStatus.DECLINED;
        else
            return  com.example.accommodationmicroservice.enums.ReservationStatus.CANCELED;

    }

    public static ReservationStatus convertReservationStatusToReservationStatusGrpc( com.example.accommodationmicroservice.enums.ReservationStatus status){
        if(status.equals( com.example.accommodationmicroservice.enums.ReservationStatus.ACCEPTED))
            return ReservationStatus.ACCEPTED;
        else if(status.equals(com.example.accommodationmicroservice.enums.ReservationStatus.PENDING))
            return ReservationStatus.PENDING;
        else if(status.equals(com.example.accommodationmicroservice.enums.ReservationStatus.DECLINED))
            return ReservationStatus.DECLINED;
        else
            return ReservationStatus.CANCELED;

    }
}
