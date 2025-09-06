package com.akash.moviebooking.api.service;
import com.akash.moviebooking.api.dto.BookingRequestDto;
import com.akash.moviebooking.api.dto.BookingResponseDto;

import java.util.List;

public interface BookingService {
    BookingResponseDto createBooking(BookingRequestDto dto);
    BookingResponseDto getBookingById(String id);
    List<BookingResponseDto> getUserBookings(String userId);
    BookingResponseDto cancelBooking(String id);
}

