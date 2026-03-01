package com.akash.moviebooking.api.dto;

import lombok.Builder;

@Builder
public record SeatResponse(String seatId, String name) {
}
