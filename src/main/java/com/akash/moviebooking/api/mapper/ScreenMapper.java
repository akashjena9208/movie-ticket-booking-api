package com.akash.moviebooking.api.mapper;

import com.akash.moviebooking.api.dto.ScreenResponse;
import com.akash.moviebooking.api.dto.SeatRespose;
import com.akash.moviebooking.api.entity.Screen;
import com.akash.moviebooking.api.entity.Seat;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ScreenMapper {

    public ScreenResponse screenResponseMapper(Screen screen) {
        if (screen == null)
            return null;

        return ScreenResponse.builder()
                .screenId(screen.getScreenId())
                .screenType(screen.getScreenType())
                .capacity(screen.getCapacity())
                .noOfRows(screen.getNoOfRows())
                .seats(seatResponseMapper(screen.getSeats()))
                .build();
    }

    private List<SeatRespose> seatResponseMapper(List<Seat> seats) {
        List<SeatRespose> seatList = new LinkedList<>();
        for (Seat seat : seats) {

            seatList.add(SeatRespose.builder()
                    .seatId(seat.getSeatId())
                    .name(seat.getName())
                    .build());
        }
        return seatList;
    }
}
