package com.akash.moviebooking.api.dto;

import com.akash.moviebooking.api.enums.UserRole;
import lombok.Builder;

@Builder
public record UserResponse(
        String userId,
        String username,
        String email,
        String phoneNumber,
        UserRole userRole

){}