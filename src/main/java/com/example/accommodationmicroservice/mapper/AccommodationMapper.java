package com.example.accommodationmicroservice.mapper;

import com.example.accommodationmicroservice.model.Accommodation;
import com.example.accommodationmicroservice.model.AccommodationDto;
import communication.AccommodationFull;
import communication.AccommodationWithGrade;
import communication.ListAccommodation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccommodationMapper {
    public static AccommodationFull convertAccommodationToAccommodationGrpc(Accommodation accommodation){
        return AccommodationFull.newBuilder()
                .setId(accommodation.getId())
                .setName(accommodation.getName())
                .setLocation(accommodation.getLocation())
                .setFacilities(accommodation.getFacilities() == null ? "" : accommodation.getFacilities())
                .setPhoto(accommodation.getPhoto())
                .setMinGuests(accommodation.getMinGuests())
                .setMaxGuests(accommodation.getMaxGuests())
                .setAvailableBeds(accommodation.getAvailableBeds())
                .setAccommodationGradeId(accommodation.getAccommodationGradeId() == null ? 0 : accommodation.getAccommodationGradeId())
                .setIsAuto(accommodation.isAuto())
                .setUserId(accommodation.getUserId())
                .build();
    }

    public static AccommodationWithGrade convertAccommodationToAccommodationWithGradeGrpc(Accommodation accommodation){
        return AccommodationWithGrade.newBuilder()
                .setId(accommodation.getId())
                .setName(accommodation.getName())
                .setLocation(accommodation.getLocation())
                .setFacilities(accommodation.getFacilities() == null ? "" : accommodation.getFacilities())
                .setPhoto(accommodation.getPhoto())
                .setMinGuests(accommodation.getMinGuests())
                .setMaxGuests(accommodation.getMaxGuests())
                .setAvailableBeds(accommodation.getAvailableBeds())
                .setAccommodationGradeId(accommodation.getAccommodationGradeId() == null ? 0 : accommodation.getAccommodationGradeId())
                .setIsAuto(accommodation.isAuto())
                .setUserId(accommodation.getUserId())
                .setAvgGrade(accommodation.getAvgGrade())
                .build();
    }

    public static Accommodation convertAccommodationGrpcToAccommodation(communication.AccommodationFull accommodation){
        return Accommodation.builder()
                .id(accommodation.getId())
                .name(accommodation.getName())
                .location(accommodation.getLocation())
                .facilities(accommodation.getFacilities())
                .photo(accommodation.getPhoto())
                .minGuests(accommodation.getMinGuests())
                .maxGuests(accommodation.getMaxGuests())
                .availableBeds(accommodation.getAvailableBeds())
                .accommodationGradeId(accommodation.getAccommodationGradeId())
                .isAuto(accommodation.getIsAuto())
                .userId(accommodation.getUserId())
                .build();
    }

    public static List<AccommodationFull> convertAccommodationsToAccommodationsGrpc(List<Accommodation> accommodations) {
        List<AccommodationFull> grpcAccommodations = new ArrayList<>();

        for (Accommodation accommodation : accommodations) {
            AccommodationFull grpcAccommodation = AccommodationFull.newBuilder()
                    .setId(accommodation.getId())
                    .setName(accommodation.getName())
                    .setLocation(accommodation.getLocation())
                    .setFacilities(accommodation.getFacilities() == null ? "" : accommodation.getFacilities())
                    .setPhoto(accommodation.getPhoto())
                    .setMinGuests(accommodation.getMinGuests())
                    .setMaxGuests(accommodation.getMaxGuests())
                    .setAvailableBeds(accommodation.getAvailableBeds())
                    .setAccommodationGradeId(accommodation.getAccommodationGradeId() == null ? 0 : accommodation.getAccommodationGradeId())
                    .setIsAuto(accommodation.isAuto())
                    .setUserId(accommodation.getUserId())
                    .build();

            grpcAccommodations.add(grpcAccommodation);
        }

        return grpcAccommodations;
    }

    public static AccommodationFull convertAccommodationDtoToAccommodationGrpc(AccommodationDto accommodation){
        return AccommodationFull.newBuilder()
                .setId(accommodation.getId())
                .setName(accommodation.getName())
                .setLocation(accommodation.getLocation())
                .setFacilities(accommodation.getFacilities() == null ? "" : accommodation.getFacilities())
                .setPhoto(accommodation.getPhoto())
                .setMinGuests(accommodation.getMinGuests())
                .setMaxGuests(accommodation.getMaxGuests())
                .setAvailableBeds(accommodation.getAvailableBeds())
                .setAccommodationGradeId(accommodation.getAccommodationGradeId() == null ? 0 : accommodation.getAccommodationGradeId())
                .setIsAuto(accommodation.isAuto())
                .setUserId(accommodation.getUserId())
                .setAvgGrade(accommodation.getAvgGrade())
                .setPrice(accommodation.getPrice())
                .build();
    }


}
