package com.akash.moviebooking.api.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class TheaterOwner extends UserDetails {

    @OneToMany(mappedBy = "theaterOwner", fetch = FetchType.LAZY)
    private List<Theater> theater;
}