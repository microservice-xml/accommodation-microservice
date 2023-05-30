package com.example.accommodationmicroservice.model;

import com.example.accommodationmicroservice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user" , schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "username" , nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "penalties")
    private int penalties;
    @Column(columnDefinition = "ENUM('GUEST','HOST')", name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    private float avgGrade;
    private boolean isHighlighted;
}
