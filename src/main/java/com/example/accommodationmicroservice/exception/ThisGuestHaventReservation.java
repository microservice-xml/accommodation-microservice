package com.example.accommodationmicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ThisGuestHaventReservation extends RuntimeException {
    public ThisGuestHaventReservation() {
        super("This guest has not made any reservations with this host.");
    }
}
