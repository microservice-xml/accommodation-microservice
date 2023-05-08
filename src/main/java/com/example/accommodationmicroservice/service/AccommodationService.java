package com.example.accommodationmicroservice.service;

import com.example.accommodationmicroservice.model.Accommodation;
import com.example.accommodationmicroservice.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationService {
    @Autowired
    AccommodationRepository accommodationRepository;
    public List<Accommodation> findAll(){
        return accommodationRepository.findAll();
    }
    public Accommodation addAccommodation(Accommodation accommodation){
        return accommodationRepository.save(accommodation);
    }
}
