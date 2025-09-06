package com.akash.moviebooking.api.service.impl;
import com.akash.moviebooking.api.dto.PaymentRequestDto;
import com.akash.moviebooking.api.dto.PaymentResponseDto;
import com.akash.moviebooking.api.entity.Booking;
import com.akash.moviebooking.api.entity.Payment;
import com.akash.moviebooking.api.enums.BookingStatus;
import com.akash.moviebooking.api.enums.PaymentStatus;
import com.akash.moviebooking.api.exceptions.BookingNotFoundException;
import com.akash.moviebooking.api.exceptions.PaymentNotFoundException;
import com.akash.moviebooking.api.mapper.PaymentMapper;
import com.akash.moviebooking.api.repository.BookingRepository;
import com.akash.moviebooking.api.repository.PaymentRepository;
import com.akash.moviebooking.api.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponseDto makePayment(PaymentRequestDto dto) {
        Booking booking = bookingRepository.findById(dto.bookingId())
                .orElseThrow(() -> new BookingNotFoundException(dto.bookingId()));

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(dto.amount());
        payment.setCurrency(dto.currency());
        payment.setPaymentMethod(dto.paymentMethod());
        payment.setStatus(PaymentStatus.PENDING);

        // Simulate payment gateway
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setStatus(PaymentStatus.SUCCESS);

        Payment saved = paymentRepository.save(payment);

        // Update booking status if payment successful
        if (saved.getStatus() == PaymentStatus.SUCCESS) {
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
        }

        return paymentMapper.toDto(saved);
    }

    @Override
    public PaymentResponseDto getPaymentById(String id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toDto)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    @Override
    public List<PaymentResponseDto> getPaymentsForBooking(String bookingId) {
        return paymentRepository.findByBooking_BookingId(bookingId)
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }
}
