package com.akash.moviebooking.api.dto;

import com.akash.moviebooking.api.enums.ScreenType;

import java.time.LocalDate;

public record MovieShowsRequest(LocalDate date, ScreenType screenType, Integer page, Integer size, String zoneId) {
}