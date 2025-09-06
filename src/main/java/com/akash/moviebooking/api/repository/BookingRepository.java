package com.akash.moviebooking.api.repository;

import com.akash.moviebooking.api.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByUser_UserId(String userId);
}
