package com.example.accommodationmicroservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class AccommodationDeleteFailed extends BaseEvent{

    private Long userId;

    public AccommodationDeleteFailed(LocalDateTime timestamp, EventType type, Long userId) {
        super(timestamp, type);
        this.userId = userId;
    }

    public AccommodationDeleteFailed() {
    }
}
