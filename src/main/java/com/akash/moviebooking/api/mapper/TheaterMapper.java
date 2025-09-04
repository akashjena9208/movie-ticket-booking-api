package com.akash.moviebooking.api.mapper;

import com.akash.moviebooking.api.dto.TheaterResponse;
import com.akash.moviebooking.api.entity.Theater;
import org.springframework.stereotype.Component;

@Component
public class TheaterMapper {

    public TheaterResponse theaterResponseMapper(Theater theater) {
        if (theater == null)
            return null;

        return TheaterResponse.builder()
                .theaterId(theater.getTheaterId())
                .name(theater.getName())
                .address(theater.getAddress())
                .city(theater.getCity())
                .landmark(theater.getLandmark())
                .build();
    }

}