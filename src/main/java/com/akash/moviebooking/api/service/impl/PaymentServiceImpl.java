package com.akash.moviebooking.api.service.impl;

import com.akash.moviebooking.api.dto.PaymentRequestDto;
import com.akash.moviebooking.api.dto.PaymentResponseDto;
import com.akash.moviebooking.api.entity.Booking;
import com.akash.moviebooking.api.entity.Payment;
import com.akash.moviebooking.api.enums.BookingStatus;
import com.akash.moviebooking.api.enums.PaymentStatus;
import com.akash.moviebooking.api.exceptions.PaymentNotFoundException;
import com.akash.moviebooking.api.exceptions.ResourceNotFoundException;
import com.akash.moviebooking.api.mapper.PaymentMapper;
import com.akash.moviebooking.api.repository.BookingRepository;
import com.akash.moviebooking.api.repository.PaymentRepository;
import com.akash.moviebooking.api.service.EmailService;
import com.akash.moviebooking.api.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentMapper mapper;
    private final EmailService emailService;   // ✅ ADD THIS

    @Override
    public PaymentResponseDto makePayment(PaymentRequestDto request) {

        Booking booking = bookingRepository.findById(request.bookingId()).orElseThrow(() -> new ResourceNotFoundException("Booking not found."));

        if (booking.getBookingStatus() == BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Booking already paid.");
        }

        if (!booking.getTotalAmount().equals(request.amount())) {
            throw new IllegalArgumentException("Payment amount mismatch.");
        }

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(request.amount());
        payment.setCurrency(request.currency());
        payment.setPaymentMethod(request.paymentMethod());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId("TXN-" + System.currentTimeMillis());

        booking.setBookingStatus(BookingStatus.CONFIRMED);

        Payment saved = paymentRepository.save(payment);

        // ✅ SEND HTML EMAIL
        String htmlBody = """
                <html>
                <body style="font-family: Arial;">
                    <h2 style="color: green;">🎉 Booking Confirmed!</h2>
                    <p><b>Booking ID:</b> %s</p>
                    <p><b>Movie:</b> %s</p>
                    <p><b>Show Time:</b> %s</p>
                    <p><b>Seats:</b> %s</p>
                    <p><b>Amount Paid:</b> ₹%s</p>
                    <p><b>Transaction ID:</b> %s</p>
                    <br>
                    <p>Enjoy your movie 🍿</p>
                </body>
                </html>
                """.formatted(booking.getBookingId(), booking.getShow().getMovie().getTitle(), booking.getShow().getStartsAt(), booking.getSeats().stream().map(seat -> seat.getName()).toList(), saved.getAmount(), saved.getTransactionId());

        emailService.sendHtmlBookingConfirmation(booking.getUser().getEmail(), "Booking Confirmed - " + booking.getShow().getMovie().getTitle(), htmlBody);

        return mapper.toDto(saved);
    }


    @Override
    public PaymentResponseDto getPaymentById(String paymentId) {

        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException(paymentId));

        return mapper.toDto(payment);
    }

    @Override
    public List<PaymentResponseDto> getPaymentsByBookingId(String bookingId) {

        return paymentRepository.findByBooking_BookingId(bookingId).stream().map(mapper::toDto).toList();
    }
}