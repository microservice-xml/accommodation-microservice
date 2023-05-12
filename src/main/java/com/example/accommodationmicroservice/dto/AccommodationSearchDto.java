package com.example.accommodationmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccommodationSearchDto {
    private String location;
    private int guestCount;
    private LocalDate start;
    private LocalDate end;
}
