package com.example.accommodationmicroservice.model;

import com.example.accommodationmicroservice.enums.Facilities;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accommodation" , schema = "public")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String facilities;
    @Column(nullable = false)
    private String photo;
    @Column(nullable = false)
    private int minGuests;
    @Column(nullable = false)
    private int maxGuests;
    @Column(nullable = false)
    private int availableBeds;
    @Column
    private Long accommodationGradeId;
    @Column
    private boolean isAuto;
}
