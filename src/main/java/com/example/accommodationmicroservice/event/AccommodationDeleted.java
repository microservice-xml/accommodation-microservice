package com.example.accommodationmicroservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class AccommodationDeleted extends BaseEvent{

    public AccommodationDeleted(LocalDateTime timestamp, EventType type) {
        super(timestamp, type);
    }

    public AccommodationDeleted() {
    }
}
