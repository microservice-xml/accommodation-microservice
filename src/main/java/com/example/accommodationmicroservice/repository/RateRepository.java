package com.example.accommodationmicroservice.repository;


import com.example.accommodationmicroservice.model.Accommodation;
import com.example.accommodationmicroservice.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateRepository extends JpaRepository<Rate, Long> {
    List<Rate> findALlByHostId(Long id);

    List<Rate> findAllByGuestId(Long id);

    List<Rate> findAllByAccommodationId(Long id);
}