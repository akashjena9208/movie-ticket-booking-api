package com.akash.moviebooking.api.security;

public record AuthRequest(
        String email,
        String password
) {}
