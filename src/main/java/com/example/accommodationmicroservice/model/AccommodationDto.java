package com.example.accommodationmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationDto {
    private Long id;
    private String name;
    private String location;
    private String facilities;
    private String photo;
    private int minGuests;
    private int maxGuests;
    private int availableBeds;
    private Long accommodationGradeId;
    private boolean isAuto;
    private Long userId;
    private float avgGrade;
    private double price;

}
