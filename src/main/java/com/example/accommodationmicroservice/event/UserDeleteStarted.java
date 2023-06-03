package com.example.accommodationmicroservice.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDeleteStarted extends BaseEvent {

    private Long userId;

    public UserDeleteStarted(LocalDateTime timestamp,
                             EventType type,
                             Long userId) {
        super(timestamp, type);
        this.userId = userId;
    }

    public UserDeleteStarted() {}
}
