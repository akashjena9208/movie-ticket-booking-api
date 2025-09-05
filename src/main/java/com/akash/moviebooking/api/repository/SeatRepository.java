package com.akash.moviebooking.api.repository;

import com.akash.moviebooking.api.entity.Screen;
import com.akash.moviebooking.api.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, String> {

    List<Seat> findByScreenAndIsDeleteFalse(Screen screen);

}