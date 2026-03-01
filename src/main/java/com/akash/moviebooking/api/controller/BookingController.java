package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.*;
import com.akash.moviebooking.api.service.BookingService;
import com.akash.moviebooking.api.util.ApiResponse;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking Management", description = """
        APIs responsible for ticket booking lifecycle management.
        
        Booking Flow:
        1. USER selects show & seats
        2. Booking is created (Status: PENDING)
        3. USER completes payment
        4. Booking becomes CONFIRMED
        5. USER can cancel before show time (if allowed)
        
        Role Access:
        • Only USER can create, view, or cancel bookings
        """)
public class BookingController {

    private final BookingService bookingService;
    private final RestResponseBuilder responseBuilder;


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create new booking", description = """
            Creates a booking for selected seats in a specific show.
            
            Initial Status:
            • PENDING
            
            After successful payment:
            • CONFIRMED
            
            Requirements:
            • JWT Token
            • Role: USER
            """)
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Booking created successfully"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid seat or show data"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Seat already booked")})
    public ResponseEntity<ApiResponse<BookingResponseDto>> createBooking(@Valid @RequestBody BookingRequestDto request, HttpServletRequest httpRequest) {

        return responseBuilder.success(HttpStatus.CREATED, "Booking created successfully.", bookingService.createBooking(request), httpRequest);
    }

    // ================= GET BOOKING =================

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get booking details", description = """
            Fetch booking details by booking ID.
            
            Includes:
            • Booking status
            • Seats
            • Show details reference
            • Total amount
            """)
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Booking retrieved successfully"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Booking not found")})
    public ResponseEntity<ApiResponse<BookingResponseDto>> getBooking(@Parameter(description = "Unique Booking ID") @PathVariable String id, HttpServletRequest httpRequest) {

        return responseBuilder.success(HttpStatus.OK, "Booking retrieved successfully.", bookingService.getBookingById(id), httpRequest);
    }


    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Cancel booking", description = """
            Cancels an existing booking.
            
            Conditions:
            • Booking must belong to authenticated user
            • Cannot cancel after show time (if business rule applied)
            • Status changes to CANCELLED
            
            Refund handling (if implemented) happens in service layer.
            """)
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Booking cancelled successfully"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid booking state"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Booking not found")})
    public ResponseEntity<ApiResponse<BookingResponseDto>> cancelBooking(@Parameter(description = "Unique Booking ID") @PathVariable String id, HttpServletRequest httpRequest) {

        return responseBuilder.success(HttpStatus.OK, "Booking cancelled successfully.", bookingService.cancelBooking(id), httpRequest);
    }
}