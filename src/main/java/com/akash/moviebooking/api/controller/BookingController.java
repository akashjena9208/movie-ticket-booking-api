package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.BookingRequestDto;
import com.akash.moviebooking.api.dto.BookingResponseDto;
import com.akash.moviebooking.api.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking Controller", description = "APIs for booking management")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    //@PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Create a booking", description = "Allows a USER to create a new booking")
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto dto) {
        return ResponseEntity.ok(bookingService.createBooking(dto));
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get booking by ID", description = "Fetch booking details by booking ID")
    public ResponseEntity<BookingResponseDto> getBooking(@PathVariable String id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/user/{userId}")
    //@PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get user bookings", description = "Fetch all bookings made by a specific user")
    public ResponseEntity<List<BookingResponseDto>> getUserBookings(@PathVariable String userId) {
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }

    @PutMapping("/{id}/cancel")
    //@PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Cancel booking", description = "Allows a USER to cancel a booking by ID")
    public ResponseEntity<BookingResponseDto> cancelBooking(@PathVariable String id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }
}
