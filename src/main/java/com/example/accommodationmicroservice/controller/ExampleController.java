package com.example.accommodationmicroservice.controller;

import com.example.accommodationmicroservice.model.Accommodation;
import javax.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accommodation")
@RequiredArgsConstructor
public class ExampleController {

    @GetMapping
    public String ispisi()
    {
        return "Ok";
    }

    @GetMapping("/get/{id}")
    public String vratiId(@PathParam("id") int id) {
        return "Volim penis" + id;
    }

    @PostMapping
    public String kreirano(@RequestBody Accommodation accommodation) {
        return accommodation.toString();
    }
}
