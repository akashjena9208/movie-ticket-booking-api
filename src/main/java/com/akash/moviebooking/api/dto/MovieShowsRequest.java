package com.akash.moviebooking.api.dto;

import com.akash.moviebooking.api.enums.ScreenType;

import java.time.LocalDate;

public record MovieShowsRequest(LocalDate date,
                                String zoneId,
                                ScreenType screenType,
                                int size,
                                int page)
{}
