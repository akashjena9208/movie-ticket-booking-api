package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.PaymentRequestDto;
import com.akash.moviebooking.api.entity.UserDetails;
import com.akash.moviebooking.api.enums.PaymentMethod;
import com.akash.moviebooking.api.enums.UserRole;
import com.akash.moviebooking.api.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookingFlowIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserRepository userRepository;

    // ✅ Use your real existing IDs
    private final String EXISTING_SHOW_ID = "8139c97e-96e9-4483-b152-a8e270c02981";
    private final String EXISTING_SEAT_ID = "37da3987-342d-49f0-9d64-8f9b1f153478";

    private String testEmail;

    @BeforeEach
    void setupUser() {

        // unique email every time (no duplicate error)
        testEmail = "integration" + System.currentTimeMillis() + "@test.com";

        UserDetails user = new UserDetails();
        user.setEmail(testEmail);
        user.setPassword("password");
        user.setUserRole(UserRole.USER);
        user.setDelete(false);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        userRepository.save(user);
    }

    @Test
    void fullBookingFlow_shouldCreateAndConfirmBooking() throws Exception {

        String bookingRequest = """
                {
                  "showId": "%s",
                  "seatIds": ["%s"]
                }
                """.formatted(EXISTING_SHOW_ID, EXISTING_SEAT_ID);

        // ================= CREATE BOOKING =================
        String bookingResponse = mockMvc.perform(post("/bookings")
                        .with(request -> {
                            request.setRemoteUser(testEmail);
                            return request;
                        })
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.bookingStatus").value("PENDING"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String bookingId = objectMapper
                .readTree(bookingResponse)
                .path("data")
                .path("bookingId")
                .asText();

        // ================= MAKE PAYMENT =================
        PaymentRequestDto paymentRequest = new PaymentRequestDto(
                bookingId,
                250.0,
                "INR",
                PaymentMethod.CARD
        );

        mockMvc.perform(post("/payments")
                        .with(request -> {
                            request.setRemoteUser(testEmail);
                            return request;
                        })
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.status").value("SUCCESS"));

        // ================= VERIFY CONFIRMED =================
        mockMvc.perform(get("/bookings/" + bookingId)
                        .with(request -> {
                            request.setRemoteUser(testEmail);
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.bookingStatus").value("CONFIRMED"));
    }
}