package com.example.accommodationmicroservice.repository;

import com.example.accommodationmicroservice.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    List<Accommodation> findAllByLocationIgnoreCaseAndMinGuestsLessThanEqualAndMaxGuestsGreaterThanEqual(String location, int minGuests, int maxGuests);

    List<Accommodation> findByIdIn(List<Long> id);
}
