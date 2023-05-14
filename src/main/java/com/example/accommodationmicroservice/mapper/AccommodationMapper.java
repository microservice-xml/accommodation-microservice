package com.example.accommodationmicroservice.mapper;

import com.example.accommodationmicroservice.model.Accommodation;
import communication.AccommodationFull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
