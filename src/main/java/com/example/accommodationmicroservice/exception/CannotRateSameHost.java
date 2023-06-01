package com.example.accommodationmicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CannotRateSameHost extends RuntimeException{
    public CannotRateSameHost() {
        super("Sorry, but u can not rate same Host again!");
    }
}
