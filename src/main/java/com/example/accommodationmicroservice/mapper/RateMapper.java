package com.example.accommodationmicroservice.mapper;

import com.example.accommodationmicroservice.model.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class RateMapper {
    public static Rate convertRateRequestToEntity(final communication.Rate request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate rateDate = LocalDate.parse(request.getRateDate(), formatter);

        return Rate.builder()
                .hostId(request.getHostId())
                .guestId(request.getGuestId())
                .rateValue(request.getRateValue())
                .rateDate(rateDate)
                .build();
    }

    public static Rate convertRateRequestToEntityWithId(final communication.Rate request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate rateDate = LocalDate.parse(request.getRateDate(), formatter);

        return Rate.builder()
                .id(request.getId())
                .hostId(request.getHostId())
                .guestId(request.getGuestId())
                .rateValue(request.getRateValue())
                .rateDate(rateDate)
                .build();
    }

    public static communication.Rate convertFromMessageToRate(Rate rate) {
        String rateDate = rate.getRateDate().toString();

        communication.Rate request;
        request = communication.Rate.newBuilder()
                //.setId(rate.getId())
                .setHostId(rate.getHostId())
                .setGuestId(rate.getGuestId())
                .setRateValue(rate.getRateValue())
                .setRateDate(rateDate)
                .build();

        return request;
    }

    public static communication.Rate convertFromMessageToRateWithId(Rate rate) {
        String rateDate = rate.getRateDate().toString();

        communication.Rate request;
        request = communication.Rate.newBuilder()
                .setId(rate.getId())
                .setHostId(rate.getHostId())
                .setGuestId(rate.getGuestId())
                .setRateValue(rate.getRateValue())
                .setRateDate(rateDate)
                .build();

        return request;
    }
}
