package com.akash.moviebooking.api.service.impl;

import com.akash.moviebooking.api.dto.BookingRequestDto;
import com.akash.moviebooking.api.dto.BookingResponseDto;
import com.akash.moviebooking.api.entity.Booking;
import com.akash.moviebooking.api.enums.BookingStatus;
import com.akash.moviebooking.api.exceptions.BookingNotFoundException;
import com.akash.moviebooking.api.mapper.BookingMapper;
import com.akash.moviebooking.api.repository.BookingRepository;
import com.akash.moviebooking.api.repository.SeatRepository;
import com.akash.moviebooking.api.repository.ShowRepository;
import com.akash.moviebooking.api.repository.UserRepository;
import com.akash.moviebooking.api.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingResponseDto createBooking(BookingRequestDto dto) {
        var user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var show = showRepository.findById(dto.showId())
                .orElseThrow(() -> new RuntimeException("Show not found"));
        var seats = seatRepository.findAllById(dto.seatIds());

        if (seats.size() != dto.seatIds().size()) {
            throw new RuntimeException("One or more seats not found");
        }

        //double totalAmount = seats.size() * 250.0; // Example pricing logic

        // ✅ Better pricing logic: sum of seat prices
        double totalAmount = seats.stream()
                .mapToDouble(seat -> seat.getPrice() != null ? seat.getPrice() : 250.0)
                .sum();



        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setSeats(seats);
        booking.setTotalAmount(totalAmount);
        booking.setBookingStatus(BookingStatus.PENDING);

        Booking saved = bookingRepository.save(booking);
        return bookingMapper.toDto(saved);
    }

    @Override
    public BookingResponseDto getBookingById(String id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::toDto)
                .orElseThrow(() -> new BookingNotFoundException(id));
    }

    @Override
    public List<BookingResponseDto> getUserBookings(String userId) {
        return bookingRepository.findByUser_UserId(userId)
                .stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    public BookingResponseDto cancelBooking(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));

        booking.setBookingStatus(BookingStatus.CANCELLED);
        return bookingMapper.toDto(bookingRepository.save(booking));
    }
}
