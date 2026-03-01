package com.akash.moviebooking.api.security;

import com.akash.moviebooking.api.entity.UserDetails;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserDetails user;

    @Column(nullable = false)
    private String tokenHash;

    @Column(nullable = false)
    private Instant expiryDate;
}