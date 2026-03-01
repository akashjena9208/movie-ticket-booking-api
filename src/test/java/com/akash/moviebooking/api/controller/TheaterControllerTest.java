package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.TheaterRequest;
import com.akash.moviebooking.api.dto.TheaterResponse;
import com.akash.moviebooking.api.service.TheaterService;
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

class TheaterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TheaterService theaterService;

    @Mock
    private RestResponseBuilder responseBuilder;

    @InjectMocks
    private TheaterController theaterController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(theaterController)
                .build();
    }

    // ================= CREATE =================

    @Test
    void createTheater_shouldReturn201() throws Exception {

        String ownerEmail = "owner@gmail.com";

        TheaterRequest request = TheaterRequest.builder()
                .name("INOX Bhubaneswar")
                .address("Patia")
                .city("Bhubaneswar")
                .landmark("Near KIIT")
                .build();

        TheaterResponse response = TheaterResponse.builder()
                .theaterId("theater-1")
                .name("INOX Bhubaneswar")
                .address("Patia")
                .city("Bhubaneswar")
                .landmark("Near KIIT")
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(ownerEmail, null);

        when(theaterService.addTheater(eq(ownerEmail), any()))
                .thenReturn(response);

        when(responseBuilder.success(
                eq(HttpStatus.CREATED),
                anyString(),
                eq(response),
                any(HttpServletRequest.class)
        )).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        mockMvc.perform(post("/theaters")
                        .param("ownerEmail", ownerEmail)
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(theaterService, times(1))
                .addTheater(eq(ownerEmail), any());
    }

    // ================= GET BY ID =================

    @Test
    void getTheater_shouldReturn200() throws Exception {

        TheaterResponse response = TheaterResponse.builder()
                .theaterId("theater-1")
                .name("INOX")
                .address("Patia")
                .city("Bhubaneswar")
                .landmark("KIIT")
                .build();

        when(theaterService.getTheaterById("theater-1"))
                .thenReturn(response);

        when(responseBuilder.success(
                eq(HttpStatus.OK),
                anyString(),
                eq(response),
                any(HttpServletRequest.class)
        )).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/theaters/{id}", "theater-1"))
                .andExpect(status().isOk());

        verify(theaterService, times(1))
                .getTheaterById("theater-1");
    }

    // ================= DELETE =================

    @Test
    void deleteTheater_shouldReturn200() throws Exception {

        String ownerEmail = "owner@gmail.com";
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(ownerEmail, null);

        doNothing().when(theaterService).deleteTheater("theater-1");

        when(responseBuilder.success(
                eq(HttpStatus.OK),
                anyString(),
                any(),
                any(HttpServletRequest.class)
        )).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/theaters/{id}", "theater-1")
                        .principal(authentication))
                .andExpect(status().isOk());

        verify(theaterService, times(1))
                .deleteTheater("theater-1");
    }
}