package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.PaymentRequestDto;
import com.akash.moviebooking.api.dto.PaymentResponseDto;
import com.akash.moviebooking.api.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Controller", description = "APIs for handling payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
   // @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Make a payment", description = "Allows a USER to make a payment for a booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payment request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - user not logged in"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<PaymentResponseDto> makePayment(@RequestBody PaymentRequestDto dto) {
        return ResponseEntity.ok(paymentService.makePayment(dto));
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get payment by ID", description = "Fetch details of a payment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment details fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<PaymentResponseDto> getPayment(@PathVariable String id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/booking/{bookingId}")
    //@PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get payments for a booking", description = "Fetch all payments associated with a specific booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsForBooking(@PathVariable String bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentsForBooking(bookingId));
    }
}
