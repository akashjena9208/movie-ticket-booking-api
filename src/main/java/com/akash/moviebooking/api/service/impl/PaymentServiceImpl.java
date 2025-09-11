//package com.akash.moviebooking.api.service.impl;
//import com.akash.moviebooking.api.dto.PaymentRequestDto;
//import com.akash.moviebooking.api.dto.PaymentResponseDto;
//import com.akash.moviebooking.api.entity.Booking;
//import com.akash.moviebooking.api.entity.Payment;
//import com.akash.moviebooking.api.enums.BookingStatus;
//import com.akash.moviebooking.api.enums.PaymentStatus;
//import com.akash.moviebooking.api.exceptions.BookingNotFoundException;
//import com.akash.moviebooking.api.exceptions.PaymentNotFoundException;
//import com.akash.moviebooking.api.mapper.PaymentMapper;
//import com.akash.moviebooking.api.repository.BookingRepository;
//import com.akash.moviebooking.api.repository.PaymentRepository;
//import com.akash.moviebooking.api.service.PaymentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class PaymentServiceImpl implements PaymentService {
//
//    private final PaymentRepository paymentRepository;
//    private final BookingRepository bookingRepository;
//    private final PaymentMapper paymentMapper;
//
//    @Override
//    public PaymentResponseDto makePayment(PaymentRequestDto dto) {
//        Booking booking = bookingRepository.findById(dto.bookingId())
//                .orElseThrow(() -> new BookingNotFoundException(dto.bookingId()));
//
//        Payment payment = new Payment();
//        payment.setBooking(booking);
//        payment.setAmount(dto.amount());
//        payment.setCurrency(dto.currency());
//        payment.setPaymentMethod(dto.paymentMethod());
//        payment.setStatus(PaymentStatus.PENDING);
//
//        // Simulate payment gateway
//        payment.setTransactionId(UUID.randomUUID().toString());
//        payment.setStatus(PaymentStatus.SUCCESS);
//
//        Payment saved = paymentRepository.save(payment);
//
//        // Update booking status if payment successful
//        if (saved.getStatus() == PaymentStatus.SUCCESS) {
//            booking.setBookingStatus(BookingStatus.CONFIRMED);
//            bookingRepository.save(booking);
//        }
//
//        return paymentMapper.toDto(saved);
//    }
//
//    @Override
//    public PaymentResponseDto getPaymentById(String id) {
//        return paymentRepository.findById(id)
//                .map(paymentMapper::toDto)
//                .orElseThrow(() -> new PaymentNotFoundException(id));
//    }
//
//    @Override
//    public List<PaymentResponseDto> getPaymentsForBooking(String bookingId) {
//        return paymentRepository.findByBooking_BookingId(bookingId)
//                .stream()
//                .map(paymentMapper::toDto)
//                .toList();
//    }
//}



























//
//package com.akash.moviebooking.api.service.impl;
//
//import com.akash.moviebooking.api.dto.PaymentRequestDto;
//import com.akash.moviebooking.api.dto.PaymentResponseDto;
//import com.akash.moviebooking.api.entity.Booking;
//import com.akash.moviebooking.api.entity.Payment;
//import com.akash.moviebooking.api.enums.BookingStatus;
//import com.akash.moviebooking.api.enums.PaymentStatus;
//import com.akash.moviebooking.api.exceptions.BookingNotFoundException;
//import com.akash.moviebooking.api.exceptions.PaymentNotFoundException;
//import com.akash.moviebooking.api.mapper.PaymentMapper;
//import com.akash.moviebooking.api.repository.BookingRepository;
//import com.akash.moviebooking.api.repository.PaymentRepository;
//import com.akash.moviebooking.api.service.EmailService;
//import com.akash.moviebooking.api.service.PaymentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class PaymentServiceImpl implements PaymentService {
//
//    private final PaymentRepository paymentRepository;
//    private final BookingRepository bookingRepository;
//    private final PaymentMapper paymentMapper;
//    private final EmailService emailService;
//
//    @Override
//    public PaymentResponseDto makePayment(PaymentRequestDto dto) {
//        Booking booking = bookingRepository.findById(dto.bookingId())
//                .orElseThrow(() -> new BookingNotFoundException(dto.bookingId()));
//
//        Payment payment = new Payment();
//        payment.setBooking(booking);
//        payment.setAmount(dto.amount());
//        payment.setCurrency(dto.currency());
//        payment.setPaymentMethod(dto.paymentMethod());
//        payment.setStatus(PaymentStatus.PENDING);
//
//        // Simulate payment gateway success
//        payment.setTransactionId(UUID.randomUUID().toString());
//        payment.setStatus(PaymentStatus.SUCCESS);
//
//        Payment saved = paymentRepository.save(payment);
//
//        // ✅ Update booking and send email
//        if (saved.getStatus() == PaymentStatus.SUCCESS) {
//            booking.setBookingStatus(BookingStatus.CONFIRMED);
//            bookingRepository.save(booking);
//
//            // Safe getters
//            String userName = booking.getUser() != null ? booking.getUser().getUsername() : "Customer";
//            String userEmail = booking.getUser() != null ? booking.getUser().getEmail() : null;
//            String movieTitle = booking.getShow() != null && booking.getShow().getMovie() != null
//                    ? booking.getShow().getMovie().getTitle()
//                    : "Movie";
//            String showTime = booking.getShow() != null && booking.getShow().getStartsAt() != null
//                    ? booking.getShow().getStartsAt().toString()
//                    : "N/A";
//
//            // ✅ use seat.getName()
//            String seatNumbers = booking.getSeats().stream()
//                    .map(seat -> seat.getName())
//                    .collect(Collectors.joining(", "));
//
//            // Build email body
//            String body = "Hello " + userName + ",\n\n" +
//                    "Your booking is confirmed 🎉\n\n" +
//                    "Booking ID: " + booking.getBookingId() + "\n" +
//                    "Movie: " + movieTitle + "\n" +
//                    "Show Time: " + showTime + "\n" +
//                    "Seats: " + seatNumbers + "\n" +
//                    "Payment Amount: ₹" + saved.getAmount() + "\n" +
//                    "Transaction ID: " + saved.getTransactionId() + "\n\n" +
//                    "Enjoy your movie! 🍿";
//
//            // Send email only if user has an email
//            if (userEmail != null) {
//                emailService.sendBookingConfirmation(
//                        userEmail,
//                        "Booking Confirmed - " + movieTitle,
//                        body
//                );
//            }
//        }
//
//        return paymentMapper.toDto(saved);
//
//
//
//    }
//
//    @Override
//    public PaymentResponseDto getPaymentById(String id) {
//        return paymentRepository.findById(id)
//                .map(paymentMapper::toDto)
//                .orElseThrow(() -> new PaymentNotFoundException(id));
//    }
//
//    @Override
//    public List<PaymentResponseDto> getPaymentsForBooking(String bookingId) {
//        return paymentRepository.findByBooking_BookingId(bookingId)
//                .stream()
//                .map(paymentMapper::toDto)
//                .collect(Collectors.toList());
//    }
//}










































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
import com.akash.moviebooking.api.service.EmailService;
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
    private final EmailService emailService;

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

            // ✅ Build HTML email
            String htmlBody = """
                    <html>
                    <body style="font-family: Arial, sans-serif;">
                        <h2 style="color: green;">🎉 Booking Confirmed!</h2>
                        <p>Hello <b>%s</b>,</p>
                        <p>Your booking is confirmed. Here are your details:</p>
                        <ul>
                            <li><b>Booking ID:</b> %s</li>
                            <li><b>Movie:</b> %s</li>
                            <li><b>Show Time:</b> %s</li>
                            <li><b>Seats:</b> %s</li>
                            <li><b>Amount Paid:</b> ₹%s</li>
                            <li><b>Transaction ID:</b> %s</li>
                        </ul>
                        <p>Enjoy your movie 🍿!</p>
                    </body>
                    </html>
                    """.formatted(
                    booking.getUser().getUsername(),   // ✅ fixed (uses username from UserDetails)
                    booking.getBookingId(),
                    booking.getShow().getMovie().getTitle(),
                    booking.getShow().getStartsAt(),
                    booking.getSeats().stream().map(seat -> seat.getName()).toList(),
                    saved.getAmount(),
                    saved.getTransactionId()
            );

            // ✅ Send HTML email
            emailService.sendHtmlBookingConfirmation(
                    booking.getUser().getEmail(),
                    "Booking Confirmed - " + booking.getShow().getMovie().getTitle(),
                    htmlBody
            );
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
