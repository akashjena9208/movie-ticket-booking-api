package com.akash.moviebooking.api.security.dto;

public record LoginRequest (
        String email,
        String password
){}