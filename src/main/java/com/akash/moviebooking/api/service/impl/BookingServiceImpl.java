package com.akash.moviebooking.api.service.impl;

import com.akash.moviebooking.api.dto.BookingRequestDto;
import com.akash.moviebooking.api.dto.BookingResponseDto;
import com.akash.moviebooking.api.entity.Booking;
import com.akash.moviebooking.api.entity.Seat;
import com.akash.moviebooking.api.entity.Show;
import com.akash.moviebooking.api.entity.UserDetails;
import com.akash.moviebooking.api.enums.BookingStatus;
import com.akash.moviebooking.api.exceptions.BookingNotFoundException;
import com.akash.moviebooking.api.exceptions.ResourceNotFoundException;
import com.akash.moviebooking.api.mapper.BookingMapper;
import com.akash.moviebooking.api.repository.BookingRepository;
import com.akash.moviebooking.api.repository.SeatRepository;
import com.akash.moviebooking.api.repository.ShowRepository;
import com.akash.moviebooking.api.repository.UserRepository;
import com.akash.moviebooking.api.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final BookingMapper mapper;

    @Override
    public BookingResponseDto createBooking(BookingRequestDto request) {

        if (request.showId() == null || request.seatIds() == null || request.seatIds().isEmpty()) {
            throw new IllegalArgumentException("Invalid booking request.");
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserDetails user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found."));

        Show show = showRepository.findById(request.showId()).orElseThrow(() -> new ResourceNotFoundException("Show not found."));

        List<Seat> seats = seatRepository.findAllById(request.seatIds());

        if (seats.size() != request.seatIds().size()) {
            throw new ResourceNotFoundException("One or more selected seats are invalid.");
        }

        // 🔥 Prevent double booking
        boolean alreadyBooked = bookingRepository.existsByShowAndSeatsInAndBookingStatus(show, seats, BookingStatus.CONFIRMED);

        if (alreadyBooked) {
            throw new IllegalStateException("One or more seats are already booked.");
        }

        double totalAmount = seats.stream().mapToDouble(seat -> seat.getPrice() != null ? seat.getPrice() : 250.0).sum();

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setSeats(seats);
        booking.setTotalAmount(totalAmount);
        booking.setBookingStatus(BookingStatus.PENDING);

        return mapper.toDto(bookingRepository.save(booking));
    }

    @Override
    public BookingResponseDto getBookingById(String bookingId) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId));

        return mapper.toDto(booking);
    }

    @Override
    public List<BookingResponseDto> getUserBookings(String userId) {
        return bookingRepository.findByUser_UserId(userId).stream().map(mapper::toDto).toList();
    }

    @Override
    public BookingResponseDto cancelBooking(String bookingId) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId));

        if (booking.getBookingStatus() == BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Cannot cancel confirmed booking.");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);

        return mapper.toDto(bookingRepository.save(booking));
    }
}