package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accommodation" , schema = "public")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "facilities", nullable = false)
    private String facilities;
    @Column(name = "photo", nullable = false)
    private String photo;
    @Column(name = "minGuests" , nullable = false)
    private int minGuests;
    @Column(name = "maxGuests" , nullable = false)
    private int maxGuests;
}