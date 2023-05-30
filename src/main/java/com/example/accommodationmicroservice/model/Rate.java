package com.example.accommodationmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(nullable = false)
    private Long accommodationId;
    @Column(nullable = false)
    private Long hostId;
    @Column(nullable = false)
    private Long guestId;
    @Column(nullable = false)
    private int rateValue;
    @Column(nullable = false)
    private LocalDate rateDate;
}
