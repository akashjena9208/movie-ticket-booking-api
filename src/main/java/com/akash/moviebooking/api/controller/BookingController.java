////package com.akash.moviebooking.api.controller;
////
////import com.akash.moviebooking.api.dto.BookingRequestDto;
////import com.akash.moviebooking.api.dto.BookingResponseDto;
////import com.akash.moviebooking.api.service.BookingService;
////import com.akash.moviebooking.api.util.ResponseStructure;
////import com.akash.moviebooking.api.util.RestResponseBuilder;
////import io.swagger.v3.oas.annotations.Operation;
////import io.swagger.v3.oas.annotations.responses.ApiResponse;
////import io.swagger.v3.oas.annotations.responses.ApiResponses;
////import io.swagger.v3.oas.annotations.tags.Tag;
////import jakarta.validation.Valid;
////import lombok.RequiredArgsConstructor;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.security.access.prepost.PreAuthorize;
////import org.springframework.web.bind.annotation.*;
////
////import java.util.List;
////
////@RestController
////@RequestMapping("/bookings")
////@RequiredArgsConstructor
////@Tag(name = "Booking Controller", description = "APIs for booking management")
////public class BookingController {
////
////    private final BookingService bookingService;
////    private final RestResponseBuilder responseBuilder;
////
////    // ================= CREATE BOOKING =================
////    @PostMapping
////    @PreAuthorize("hasAuthority('USER')")
////    @Operation(summary = "Create a booking",
////            description = "Allows a USER to create a new booking")
////    @ApiResponses({
////            @ApiResponse(responseCode = "201", description = "Booking created successfully"),
////            @ApiResponse(responseCode = "400", description = "Invalid booking request"),
////            @ApiResponse(responseCode = "403", description = "Forbidden"),
////            @ApiResponse(responseCode = "404", description = "Show or User not found")
////    })
////    public ResponseEntity<ResponseStructure<BookingResponseDto>> createBooking(
////            @Valid @RequestBody BookingRequestDto dto) {
////
////        BookingResponseDto response = bookingService.createBooking(dto);
////
////        return responseBuilder.success(
////                HttpStatus.CREATED,
////                "Booking created successfully",
////                response
////        );
////    }
////
////    // ================= GET BOOKING BY ID =================
////    @GetMapping("/{id}")
////    @PreAuthorize("hasAuthority('USER')")
////    @Operation(summary = "Get booking by ID",
////            description = "Fetch booking details using booking ID")
////    @ApiResponses({
////            @ApiResponse(responseCode = "200", description = "Booking fetched successfully"),
////            @ApiResponse(responseCode = "404", description = "Booking not found")
////    })
////    public ResponseEntity<ResponseStructure<BookingResponseDto>> getBooking(
////            @PathVariable String id) {
////
////        BookingResponseDto response = bookingService.getBookingById(id);
////
////        return responseBuilder.success(
////                HttpStatus.OK,
////                "Booking fetched successfully",
////                response
////        );
////    }
////
////    // ================= GET USER BOOKINGS =================
////    @GetMapping("/user/{userId}")
////    @PreAuthorize("hasAuthority('USER')")
////    @Operation(summary = "Get user bookings",
////            description = "Fetch all bookings made by a specific user")
////    @ApiResponses({
////            @ApiResponse(responseCode = "200", description = "Bookings fetched successfully"),
////            @ApiResponse(responseCode = "404", description = "User not found")
////    })
////    public ResponseEntity<ResponseStructure<List<BookingResponseDto>>> getUserBookings(
////            @PathVariable String userId) {
////
////        List<BookingResponseDto> response =
////                bookingService.getUserBookings(userId);
////
////        return responseBuilder.success(
////                HttpStatus.OK,
////                "User bookings fetched successfully",
////                response
////        );
////    }
////
////    // ================= CANCEL BOOKING =================
////    @PutMapping("/{id}/cancel")
////    @PreAuthorize("hasAuthority('USER')")
////    @Operation(summary = "Cancel booking",
////            description = "Allows a USER to cancel a booking by ID")
////    @ApiResponses({
////            @ApiResponse(responseCode = "200", description = "Booking cancelled successfully"),
////            @ApiResponse(responseCode = "404", description = "Booking not found")
////    })
////    public ResponseEntity<ResponseStructure<BookingResponseDto>> cancelBooking(
////            @PathVariable String id) {
////
////        BookingResponseDto response = bookingService.cancelBooking(id);
////
////        return responseBuilder.success(
////                HttpStatus.OK,
////                "Booking cancelled successfully",
////                response
////        );
////    }
////}
//package com.akash.moviebooking.api.controller;
//
//import com.akash.moviebooking.api.dto.BookingRequestDto;
//import com.akash.moviebooking.api.dto.BookingResponseDto;
//import com.akash.moviebooking.api.service.BookingService;
//import com.akash.moviebooking.api.util.ApiResponse;
//import com.akash.moviebooking.api.util.RestResponseBuilder;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/bookings")
//public class BookingController {
//
//    private final BookingService bookingService;
//    private final RestResponseBuilder responseBuilder;
//
//    @PostMapping
//    @PreAuthorize("hasAuthority('USER')")
//    public ResponseEntity<ApiResponse<BookingResponseDto>> createBooking(
//            @Valid @RequestBody BookingRequestDto request,
//            HttpServletRequest httpRequest) {
//
//        BookingResponseDto response = bookingService.createBooking(request);
//
//        return responseBuilder.success(
//                HttpStatus.CREATED,
//                "Booking created successfully.",
//                response,
//                httpRequest
//        );
//    }
//
//    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('USER')")
//    public ResponseEntity<ApiResponse<BookingResponseDto>> getBooking(
//            @PathVariable String id,
//            HttpServletRequest httpRequest) {
//
//        BookingResponseDto response = bookingService.getBookingById(id);
//
//        return responseBuilder.success(
//                HttpStatus.OK,
//                "Booking fetched successfully.",
//                response,
//                httpRequest
//        );
//    }
//
//    @PutMapping("/{id}/cancel")
//    @PreAuthorize("hasAuthority('USER')")
//    public ResponseEntity<ApiResponse<BookingResponseDto>> cancelBooking(
//            @PathVariable String id,
//            HttpServletRequest httpRequest) {
//
//        BookingResponseDto response = bookingService.cancelBooking(id);
//
//        return responseBuilder.success(
//                HttpStatus.OK,
//                "Booking cancelled successfully.",
//                response,
//                httpRequest
//        );
//    }
//}
package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.*;
import com.akash.moviebooking.api.service.BookingService;
import com.akash.moviebooking.api.util.ApiResponse;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking Management")
public class BookingController {

    private final BookingService bookingService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<BookingResponseDto>> createBooking(
            @Valid @RequestBody BookingRequestDto request,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.CREATED,
                "Booking created successfully.",
                bookingService.createBooking(request),
                httpRequest
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<BookingResponseDto>> getBooking(
            @PathVariable String id,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Booking retrieved successfully.",
                bookingService.getBookingById(id),
                httpRequest
        );
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<BookingResponseDto>> cancelBooking(
            @PathVariable String id,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Booking cancelled successfully.",
                bookingService.cancelBooking(id),
                httpRequest
        );
    }
}