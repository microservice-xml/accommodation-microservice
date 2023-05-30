package com.example.accommodationmicroservice.controller;

import com.example.accommodationmicroservice.model.Rate;
import com.example.accommodationmicroservice.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rate-accommodation")
@RequiredArgsConstructor
public class RateController {
    @Autowired
    RateService rateService;
    @GetMapping("")
    public ResponseEntity findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(rateService.findAll());
    }
    @PostMapping("")
    public ResponseEntity rateAccommodation(@RequestBody Rate rate){
        return ResponseEntity.status(HttpStatus.CREATED).body(rateService.rateAccommodation(rate));
    }
    @PutMapping("/{id}")
    public ResponseEntity changeRate(@RequestBody Rate rate){
        return ResponseEntity.status(HttpStatus.CREATED).body(rateService.changeAccommodationRate(rate));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteRate(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(rateService.deleteAccommodationRate(id));
    }
}
