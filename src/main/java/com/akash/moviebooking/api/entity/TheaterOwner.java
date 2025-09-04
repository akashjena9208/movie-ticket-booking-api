package com.akash.moviebooking.api.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class TheaterOwner extends UserDetails{

    @OneToMany(mappedBy = "theaterOwner")
    private List<Theater> theater;

}
