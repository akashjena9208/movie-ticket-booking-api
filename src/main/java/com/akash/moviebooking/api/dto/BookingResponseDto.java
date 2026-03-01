package com.akash.moviebooking.api.dto;

import com.akash.moviebooking.api.enums.BookingStatus;

import java.time.Instant;
import java.util.List;


public record BookingResponseDto(String bookingId, BookingStatus bookingStatus, Double totalAmount, String userId,
                                 String showId, List<String> seatIds, Instant createdAt, Instant updatedAt) {
}