package com.akash.moviebooking.api.dto;

import java.util.List;

public record TheaterShowProjection (
        String theaterId,
        String theaterName,
        String address
       // , List<ShowResponse> shows
) {}
