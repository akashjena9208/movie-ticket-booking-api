package com.akash.moviebooking.api.dto;

import java.time.Instant;
import java.util.List;


// Response DTO
public record BookingResponseDto(
        String bookingId,
        String bookingStatus,
        Double totalAmount,
        String userId,
        String showId,
        List<String> seatIds,
        Instant createdAt,
        Instant updatedAt
) {}
