package com.akash.moviebooking.api.mapper;

import com.akash.moviebooking.api.dto.PaymentResponseDto;
import com.akash.moviebooking.api.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentResponseDto toDto(Payment payment) {
        return new PaymentResponseDto(
                payment.getPaymentId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getPaymentMethod().name(),
                payment.getStatus(),
                payment.getBooking().getBookingId(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}
