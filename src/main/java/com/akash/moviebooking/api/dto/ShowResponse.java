package com.akash.moviebooking.api.dto;

import com.akash.moviebooking.api.enums.ScreenType;
import lombok.Builder;

import java.time.Instant;

@Builder
public record ShowResponse(

        String showId,
        Instant startsAt,
        Instant endsAt,

        String screenId, ScreenType screenType) {
}