package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.ShowResponse;
import com.akash.moviebooking.api.service.ShowService;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShowControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ShowService showService;

    @Mock
    private RestResponseBuilder responseBuilder;

    @InjectMocks
    private ShowController showController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(showController)
                .build();
    }

    // ================= CREATE SHOW =================
    @Test
    void createShow_shouldReturn201() throws Exception {

        String theaterId = "theater-1";
        String screenId = "screen-1";
        String movieId = "movie-1";
        Long startTime = Instant.now().toEpochMilli();
        String zoneId = "UTC";

        ShowResponse response = new ShowResponse(
                "show-1",
                Instant.now(),
                Instant.now().plusSeconds(7200),
                screenId,
                null
        );

        Authentication authentication =
                new UsernamePasswordAuthenticationToken("owner@gmail.com", null);

        when(showService.addShow(theaterId, screenId, movieId, startTime, zoneId))
                .thenReturn(response);

        when(responseBuilder.success(
                eq(HttpStatus.CREATED),
                anyString(),
                eq(response),
                any(HttpServletRequest.class)
        )).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        mockMvc.perform(post("/shows/theaters/{theaterId}/screens/{screenId}",
                        theaterId, screenId)
                        .principal(authentication)
                        .param("movieId", movieId)
                        .param("startTime", startTime.toString())
                        .param("zoneId", zoneId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(showService, times(1))
                .addShow(theaterId, screenId, movieId, startTime, zoneId);
    }


}