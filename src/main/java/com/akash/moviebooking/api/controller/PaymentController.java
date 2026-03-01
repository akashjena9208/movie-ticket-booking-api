package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.*;
import com.akash.moviebooking.api.service.PaymentService;
import com.akash.moviebooking.api.util.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Management")
public class PaymentController {

    private final PaymentService paymentService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping
    //@PreAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<PaymentResponseDto>> makePayment(
            @RequestBody PaymentRequestDto request,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.CREATED,
                "Payment processed successfully.",
                paymentService.makePayment(request),
                httpRequest
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponseDto>> getPayment(
            @PathVariable String id,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Payment fetched successfully.",
                paymentService.getPaymentById(id),
                httpRequest
        );
    }
}