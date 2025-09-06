package com.akash.moviebooking.api.dto;


import com.akash.moviebooking.api.enums.PaymentStatus;

import java.time.Instant;

public record PaymentResponseDto(
        String paymentId,
        Double amount,
        String currency,
        String paymentMethod,
        PaymentStatus status,
        String bookingId,
        Instant createdAt,
        Instant updatedAt
) {}