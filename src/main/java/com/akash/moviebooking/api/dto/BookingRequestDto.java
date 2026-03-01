package com.akash.moviebooking.api.dto;

import java.util.List;

public record BookingRequestDto(String showId, List<String> seatIds) {
}



