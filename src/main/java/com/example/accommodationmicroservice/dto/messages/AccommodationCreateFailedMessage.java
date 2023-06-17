package com.example.accommodationmicroservice.dto.messages;

import com.example.accommodationmicroservice.event.EventType;
import lombok.Data;

@Data
public class AccommodationCreateFailedMessage {

    private String name;

    private EventType type;

    public AccommodationCreateFailedMessage() {
        type = EventType.ACCOMMODATION_CREATE_FAILED;
    }

    public AccommodationCreateFailedMessage(String name) {
        this.name = name;
        type = EventType.ACCOMMODATION_CREATE_FAILED;
    }
}
