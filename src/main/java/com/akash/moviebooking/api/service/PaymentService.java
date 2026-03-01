package com.akash.moviebooking.api.service;

import com.akash.moviebooking.api.dto.PaymentRequestDto;
import com.akash.moviebooking.api.dto.PaymentResponseDto;

import java.util.List;

public interface PaymentService {

    PaymentResponseDto makePayment(PaymentRequestDto request);

    PaymentResponseDto getPaymentById(String paymentId);

    List<PaymentResponseDto> getPaymentsByBookingId(String bookingId);
}