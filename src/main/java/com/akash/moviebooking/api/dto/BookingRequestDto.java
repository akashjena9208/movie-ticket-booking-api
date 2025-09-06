package com.akash.moviebooking.api.dto;

import java.util.List;
// Request DTO
public record BookingRequestDto(
        String userId,
        String showId,
        List<String> seatIds
) {}



