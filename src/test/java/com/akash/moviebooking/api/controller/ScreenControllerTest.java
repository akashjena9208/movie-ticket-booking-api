package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.ScreenRequest;
import com.akash.moviebooking.api.dto.ScreenResponse;
import com.akash.moviebooking.api.enums.ScreenType;
import com.akash.moviebooking.api.service.ScreenService;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ScreenControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ScreenService screenService;

    @Mock
    private RestResponseBuilder responseBuilder;

    @InjectMocks
    private ScreenController screenController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(screenController)
                .build();
    }

    // ================= ADD SCREEN =================

    @Test
    void addScreen_shouldReturn201() throws Exception {

        String theaterId = "theater-1";
        String ownerEmail = "owner@gmail.com";

        ScreenRequest request = new ScreenRequest(
                ScreenType.IMAX,
                100,
                10
        );

        ScreenResponse response = ScreenResponse.builder()
                .screenId("screen-1")
                .screenType(ScreenType.IMAX)
                .capacity(100)
                .noOfRows(10)
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(ownerEmail, null);

        when(screenService.addScreen(any(), eq(theaterId)))
                .thenReturn(response);

        when(responseBuilder.success(
                eq(HttpStatus.CREATED),
                anyString(),
                eq(response),
                any(HttpServletRequest.class)
        )).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        mockMvc.perform(post("/theaters/{theaterId}/screens", theaterId)
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(screenService, times(1))
                .addScreen(any(), eq(theaterId));
    }

    // ================= GET SCREEN =================

    @Test
    void getScreen_shouldReturn200() throws Exception {

        String theaterId = "theater-1";
        String screenId = "screen-1";

        ScreenResponse response = ScreenResponse.builder()
                .screenId(screenId)
                .screenType(ScreenType.IMAX)
                .capacity(100)
                .noOfRows(10)
                .build();

        when(screenService.getScreen(theaterId, screenId))
                .thenReturn(response);

        when(responseBuilder.success(
                eq(HttpStatus.OK),
                anyString(),
                eq(response),
                any(HttpServletRequest.class)
        )).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/theaters/{theaterId}/screens/{screenId}",
                        theaterId, screenId))
                .andExpect(status().isOk());

        verify(screenService, times(1))
                .getScreen(theaterId, screenId);
    }
}