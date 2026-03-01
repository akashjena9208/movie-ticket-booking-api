package com.akash.moviebooking.api.repository;

import com.akash.moviebooking.api.entity.Booking;
import com.akash.moviebooking.api.entity.Seat;
import com.akash.moviebooking.api.entity.Show;
import com.akash.moviebooking.api.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {

    List<Booking> findByUser_UserId(String userId);

    boolean existsByShowAndSeatsInAndBookingStatus(Show show, List<Seat> seats, BookingStatus status);
}