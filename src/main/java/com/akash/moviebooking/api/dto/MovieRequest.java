package com.akash.moviebooking.api.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.akash.moviebooking.api.enums.Certificate;
import com.akash.moviebooking.api.enums.Genre;


import java.time.Duration;
import java.util.Set;

public record MovieRequest(
        @NotBlank(message = "Title is required")
        String title,

        String description,

        Set<String> castList,

        @NotNull(message = "Runtime is required")
        Duration runtime,   // Example: PT2H30M for 2 hours 30 minutes

        @NotNull(message = "Certificate is required")
        Certificate certificate,

        @NotNull(message = "Genre is required")
        Genre genre
) {}


