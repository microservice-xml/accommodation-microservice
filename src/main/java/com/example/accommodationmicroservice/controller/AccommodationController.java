package com.example.accommodationmicroservice.controller;

import com.example.accommodationmicroservice.dto.AccommodationSearchDto;
import com.example.accommodationmicroservice.model.Accommodation;
import com.example.accommodationmicroservice.model.AccommodationDto;
import com.example.accommodationmicroservice.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/search")
    public ResponseEntity<List<AccommodationDto>> search(@RequestBody AccommodationSearchDto accommodationSearchDto) {
        return ResponseEntity.status(OK).body(accommodationService.search(accommodationSearchDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Accommodation>> findAllByUserId(@PathVariable Long id) {
        return  ResponseEntity.status(OK).body(accommodationService.findAllByUserId(id));
    }

    @GetMapping("/by/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(accommodationService.findById(id));
    }

}
