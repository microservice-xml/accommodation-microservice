package com.example.accommodationmicroservice.dto.messages;

import com.example.accommodationmicroservice.event.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class NewAccommodationMessage {

    private long accommodationId;

    private String name;

    private EventType type;

    public NewAccommodationMessage() {
        type = EventType.ACCOMMODATION_CREATED;
    }

    public NewAccommodationMessage(long accommodationId, String name) {
        this.accommodationId = accommodationId;
        this.name = name;
        type = EventType.ACCOMMODATION_CREATED;
    }
}
