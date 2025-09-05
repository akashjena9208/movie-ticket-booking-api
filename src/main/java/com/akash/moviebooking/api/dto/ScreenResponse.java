package com.akash.moviebooking.api.dto;

import com.akash.moviebooking.api.enums.ScreenType;
import lombok.Builder;

import java.util.List;

@Builder
public record ScreenResponse(

        String screenId,
        ScreenType screenType,
        Integer capacity,
        Integer noOfRows,
        List<SeatRespose> seats

)
{}
