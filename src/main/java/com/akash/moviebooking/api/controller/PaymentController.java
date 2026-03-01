package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.PaymentRequestDto;
import com.akash.moviebooking.api.dto.PaymentResponseDto;
import com.akash.moviebooking.api.service.PaymentService;
import com.akash.moviebooking.api.util.ApiResponse;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Management", description = """
        APIs for handling booking payments.
        
        Flow:
        1. User creates a booking (status = PENDING)
        2. User makes payment
        3. On SUCCESS → booking becomes CONFIRMED
        
        Only USER role can perform payments.
        """)
public class PaymentController {

    private final PaymentService paymentService;
    private final RestResponseBuilder responseBuilder;


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Process payment for a booking", description = """
            Processes payment for an existing booking.
            
            ACCESS:
            • Role required: USER
            • JWT Bearer token required
            
            Business Logic:
            • Booking must exist
            • Payment is recorded
            • If payment SUCCESS → Booking status becomes CONFIRMED
            
            Request must include:
            • bookingId
            • amount
            • currency
            • paymentMethod
            """)
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Payment processed successfully"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request data"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Booking not found"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Payment conflict / already processed")})
    public ResponseEntity<ApiResponse<PaymentResponseDto>> makePayment(

            @Parameter(description = "Payment request payload containing bookingId and payment details") @RequestBody PaymentRequestDto request,

            HttpServletRequest httpRequest) {

        return responseBuilder.success(HttpStatus.CREATED, "Payment processed successfully.", paymentService.makePayment(request), httpRequest);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get payment details by ID", description = """
            Retrieves payment details using paymentId.
            
            Useful for:
            • Checking payment status
            • Viewing transaction ID
            • Verifying booking payment
            
            Public endpoint (authentication required by global security config).
            """)
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Payment retrieved successfully"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Payment not found")})
    public ResponseEntity<ApiResponse<PaymentResponseDto>> getPayment(

            @Parameter(description = "Unique Payment ID") @PathVariable String id,

            HttpServletRequest httpRequest) {

        return responseBuilder.success(HttpStatus.OK, "Payment fetched successfully.", paymentService.getPaymentById(id), httpRequest);
    }
}