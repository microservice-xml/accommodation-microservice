package com.example.accommodationmicroservice.model;

import com.example.accommodationmicroservice.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    private String id;

    private LocalDate start;

    private LocalDate end;

    private Long userId;

    private ReservationStatus status;

    private String slotId;

    private int numberOfGuests;

    private Long hostId;
}
