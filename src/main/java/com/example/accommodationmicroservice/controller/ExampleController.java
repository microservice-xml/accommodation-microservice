package com.example.accommodationmicroservice.controller;

import com.example.accommodationmicroservice.model.Accommodation;
import javax.websocket.server.PathParam;

import com.example.accommodationmicroservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accommodation")
@RequiredArgsConstructor
public class ExampleController {

    private final ReservationService reservationService;

    @GetMapping
    public String ispisi()
    {
        return "Ok";
    }

    @GetMapping("/get/{id}")
    public String vratiId(@PathParam("id") int id) {
        return "Volim penis" + id;
    }

    @GetMapping("/host/{id}")
    public ResponseEntity test(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.findAllByHostId(id));
    }

    @PostMapping
    public String kreirano(@RequestBody Accommodation accommodation) {
        return accommodation.toString();
    }
}
