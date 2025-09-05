package com.akash.moviebooking.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Set;

@Entity
public class User extends UserDetails{

    @OneToMany(mappedBy = "user")
    private List<Feedback> feedbacks;

//    @OneToMany(mappedBy = "user")
//    private Set<Booking> bookings;

}
