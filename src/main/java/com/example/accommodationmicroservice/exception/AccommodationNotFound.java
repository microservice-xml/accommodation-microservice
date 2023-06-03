package com.example.accommodationmicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccommodationNotFound extends RuntimeException{
    public AccommodationNotFound(){
        super("Accommodation not found. ");
    }
}
