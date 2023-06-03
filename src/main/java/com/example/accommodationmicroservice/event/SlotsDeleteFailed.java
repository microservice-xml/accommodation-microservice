package com.example.accommodationmicroservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class SlotsDeleteFailed extends BaseEvent{

    List<Long> accommodationIds;
}
