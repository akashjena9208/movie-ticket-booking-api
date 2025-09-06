package com.akash.moviebooking.api.service;

import com.akash.moviebooking.api.dto.PaymentRequestDto;
import com.akash.moviebooking.api.dto.PaymentResponseDto;

import java.util.List;

public interface PaymentService {
    PaymentResponseDto makePayment(PaymentRequestDto dto);
    PaymentResponseDto getPaymentById(String id);
    List<PaymentResponseDto> getPaymentsForBooking(String bookingId);
}

