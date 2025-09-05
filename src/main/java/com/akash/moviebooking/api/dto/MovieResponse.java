package com.akash.moviebooking.api.dto;

import com.akash.moviebooking.api.enums.Certificate;
import com.akash.moviebooking.api.enums.Genre;
import lombok.Builder;

import java.time.Duration;
import java.util.Set;

@Builder
public record MovieResponse(
        String movieId,
        String title,
        String description,
        String ratings,
        Duration runtime,
        Certificate certificate,
        Genre genre,
        Set<String> castList
) {
}
