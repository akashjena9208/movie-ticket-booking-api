package com.akash.moviebooking.api.repository;

import com.akash.moviebooking.api.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen, String> {
}