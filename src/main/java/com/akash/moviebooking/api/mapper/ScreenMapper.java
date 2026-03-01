package com.akash.moviebooking.api.mapper;

import com.akash.moviebooking.api.dto.ScreenResponse;
import com.akash.moviebooking.api.dto.SeatResponse;
import com.akash.moviebooking.api.entity.Screen;
import com.akash.moviebooking.api.entity.Seat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScreenMapper {

    public ScreenResponse toDto(Screen screen) {

        if (screen == null) return null;

        return ScreenResponse.builder().screenId(screen.getScreenId()).screenType(screen.getScreenType()).capacity(screen.getCapacity()).noOfRows(screen.getNoOfRows()).seats(mapSeats(screen.getSeats())).build();
    }

    private List<SeatResponse> mapSeats(List<Seat> seats) {

        if (seats == null) return null;

        return seats.stream().map(seat -> SeatResponse.builder().seatId(seat.getSeatId()).name(seat.getName()).build()).toList();
    }
}