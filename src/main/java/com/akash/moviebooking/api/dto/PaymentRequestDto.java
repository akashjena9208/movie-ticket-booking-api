package com.akash.moviebooking.api.dto;
import com.akash.moviebooking.api.enums.PaymentMethod;

public record PaymentRequestDto(
        String bookingId,
        Double amount,
        String currency,
        PaymentMethod paymentMethod
) {}