package com.example.accommodationmicroservice.controller;

import com.example.accommodationmicroservice.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final AccommodationService accommodationService;

    /*@GetMapping("/")
    public void sendMessage() {
        accommodationService.produce();
    }*/
}
