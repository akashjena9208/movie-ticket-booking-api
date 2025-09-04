package com.akash.moviebooking.api.repository;

import com.akash.moviebooking.api.entity.TheaterOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterOwnerRepository extends JpaRepository<TheaterOwner, String> {
}