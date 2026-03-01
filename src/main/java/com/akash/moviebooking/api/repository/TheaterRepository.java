package com.akash.moviebooking.api.repository;

import com.akash.moviebooking.api.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, String> {
    List<Theater> findByTheaterOwner_Email(String email);
}