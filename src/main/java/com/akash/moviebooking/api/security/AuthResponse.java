package com.akash.moviebooking.api.security;

import lombok.Builder;

@Builder
public record AuthResponse(

        String accessToken,
        String userId,
        String email,
        String role

) {}