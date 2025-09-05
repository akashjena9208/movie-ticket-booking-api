package com.akash.moviebooking.api.dto;

import lombok.Builder;

@Builder
public record SeatRespose (
        String seatId,
        String name
)
{}
