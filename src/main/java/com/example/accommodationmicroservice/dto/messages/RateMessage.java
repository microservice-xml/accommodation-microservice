package com.example.accommodationmicroservice.dto.messages;

import com.example.accommodationmicroservice.event.EventType;
import com.example.accommodationmicroservice.model.Rate;
import lombok.Data;

@Data
public class RateMessage {

    private long accommodationId;

    private long userId;

    private int grade;

    private EventType type;

    public RateMessage() {
        type = EventType.ACCOMMODATION_CREATED;
    }

    public RateMessage(long userId, long accommodationId, int value, EventType type) {
        this.accommodationId = accommodationId;
        this.userId = userId;
        this.grade = value;
        this.type = type;
    }

    public RateMessage(Rate rate, EventType type) {
        this.accommodationId = rate.getAccommodationId();
        this.userId = rate.getGuestId();
        this.grade = rate.getRateValue();
        this.type = type;
    }
}
