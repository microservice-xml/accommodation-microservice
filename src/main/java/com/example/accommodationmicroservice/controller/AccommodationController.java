package com.example.accommodationmicroservice.controller;

import com.example.accommodationmicroservice.model.Accommodation;
import com.example.accommodationmicroservice.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/accommodation")
@RequiredArgsConstructor
public class AccommodationController {
    @Autowired
    AccommodationService accommodationService;
    @GetMapping("/all")
    public ResponseEntity<List<Accommodation>> findAll(){
        return new ResponseEntity<>(accommodationService.findAll(), OK);
    }
    @PostMapping("/add-accommodation")
    public ResponseEntity<Accommodation> addAccommodation(@RequestBody Accommodation accommodation) {
        return new ResponseEntity<>(accommodationService.addAccommodation(accommodation), CREATED);
    }
}
