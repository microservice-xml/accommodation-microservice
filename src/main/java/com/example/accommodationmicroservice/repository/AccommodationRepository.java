package com.example.accommodationmicroservice.repository;

import com.example.accommodationmicroservice.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}
