package com.example.accommodationmicroservice.service;


import com.example.accommodationmicroservice.dto.NotificationDto;
import com.example.accommodationmicroservice.exception.CannotRateSameHost;
import com.example.accommodationmicroservice.exception.ThisGuestHaventReservation;
import com.example.accommodationmicroservice.model.Accommodation;
import com.example.accommodationmicroservice.model.Rate;
import com.example.accommodationmicroservice.model.User;
import com.example.accommodationmicroservice.repository.AccommodationRepository;
import com.example.accommodationmicroservice.repository.RateRepository;
import com.example.accommodationmicroservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RateService {
    @Autowired
    RateRepository rateRepository;
    private final ReservationService reservationService;
    private final UserRepository userRepository;

    private final AccommodationRepository accommodationRepository;

    public List<Rate> findAll() {
        return rateRepository.findAll();
    }

    public float calculateAvgRate(Rate rate) {
        List<Rate> rates = rateRepository.findAllByAccommodationId(rate.getAccommodationId());
        float sum = 0;
        for(Rate rat : rates) {
            sum+= rat.getRateValue();
        }
        return sum / rates.size();
    }

    public Rate rateAccommodation(Rate rate) {
        if(reservationService.checkCanUserRate(rate.getAccommodationId(), rate.getGuestId())) {
            if(reservationService.checkReservationHistory(rate.getHostId(), rate.getGuestId()))
            {
                Rate newRate = rateRepository.save(rate);
                Accommodation accommodation = accommodationRepository.findById(rate.getAccommodationId()).get();
                accommodation.setAvgGrade(calculateAvgRate(rate));
                accommodationRepository.save(accommodation);
                createNotification(rate.getHostId(), "Someone is rated your accommodation with name "+accommodation.getName() +", current average rating that accommodation is "+accommodation.getAvgGrade(),"newRateAcc");
                return newRate;
            } else {
                throw new ThisGuestHaventReservation();
            }
        }
        throw new CannotRateSameHost();
    }

    public Rate changeAccommodationRate(Rate rate) {
        Optional<Rate> newRate = rateRepository.findById(rate.getId());
        newRate.get().setRateValue(rate.getRateValue());
        Rate savingRate = rateRepository.save(newRate.get());
        Accommodation accommodation = accommodationRepository.findById(rate.getAccommodationId()).get();
        accommodation.setAvgGrade(calculateAvgRate(rate));
        accommodationRepository.save(accommodation);
        return savingRate;
    }

    public Rate deleteAccommodationRate(Long id) {
        Optional<Rate> rateOptional = rateRepository.findById(id);
        if (rateOptional.isPresent()) {
            Rate rate = rateOptional.get();
            rateRepository.delete(rate);
            Accommodation accommodation = accommodationRepository.findById(rate.getAccommodationId()).get();
            accommodation.setAvgGrade(calculateAvgRate(rate));
            accommodationRepository.save(accommodation);
            return rate;
        }
        return null;
    }

    public Rate getById(Long id) {
        Optional<Rate> rate = rateRepository.findById(id);
        if (rate.isEmpty()) {
            return null;
        }
        return rate.get();
    }

    public List<Rate> findAllByAccommodationId(Long id) {
        return rateRepository.findAllByAccommodationId(id);
    }

    public void createNotification(Long userId, String message, String type) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<NotificationDto> requestBody = new HttpEntity<>(NotificationDto.builder().userId(userId).type(type).message(message).build());
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8088/notification", HttpMethod.POST, requestBody, String.class);
    }
}
