package com.example.accommodationmicroservice.mapper;

import com.example.accommodationmicroservice.model.Rate;
import communication.AccommodationRate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RateAccommodationMapper {
    public static communication.AccommodationRate convertFromMessageToRate(Rate rate) {
        String rateDate = rate.getRateDate().toString();

        communication.AccommodationRate request;
        request = communication.AccommodationRate.newBuilder()
                //.setId(rate.getId())
                .setAccommodationId(rate.getAccommodationId())
                .setHostId(rate.getHostId())
                .setGuestId(rate.getGuestId())
                .setRateValue(rate.getRateValue())
                .setRateDate(rateDate)
                .build();

        return request;
    }

    public static communication.AccommodationRate convertFromMessageToRateWithId(Rate rate) {
        String rateDate = rate.getRateDate().toString();

        communication.AccommodationRate request;
        request = communication.AccommodationRate.newBuilder()
                .setId(rate.getId())
                .setAccommodationId(rate.getAccommodationId())
                .setHostId(rate.getHostId())
                .setGuestId(rate.getGuestId())
                .setRateValue(rate.getRateValue())
                .setRateDate(rateDate)
                .build();

        return request;
    }

    public static List<AccommodationRate> convertFromMessageListToRateWithIdList(List<Rate> rates) {
        List<communication.AccommodationRate> requests = new ArrayList<>();

        for (Rate rate : rates) {
            String rateDate = rate.getRateDate().toString();

            communication.AccommodationRate request = communication.AccommodationRate.newBuilder()
                    .setId(rate.getId())
                    .setAccommodationId(rate.getAccommodationId())
                    .setHostId(rate.getHostId())
                    .setGuestId(rate.getGuestId())
                    .setRateValue(rate.getRateValue())
                    .setRateDate(rateDate)
                    .build();

            requests.add(request);
        }

        return requests;
    }


    public static Rate convertRateRequestToEntity(communication.AccommodationRate request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate rateDate = LocalDate.parse(request.getRateDate(), formatter);

        return Rate.builder()
                .accommodationId(request.getAccommodationId())
                .hostId(request.getHostId())
                .guestId(request.getGuestId())
                .rateValue(request.getRateValue())
                .rateDate(rateDate)
                .build();
    }

    public static Rate convertRateRequestToEntityWithId(communication.AccommodationRate request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate rateDate = LocalDate.parse(request.getRateDate(), formatter);

        return Rate.builder()
                .id(request.getId())
                .accommodationId(request.getAccommodationId())
                .hostId(request.getHostId())
                .guestId(request.getGuestId())
                .rateValue(request.getRateValue())
                .rateDate(rateDate)
                .build();
    }
}
