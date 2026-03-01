package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.MovieShowsRequest;
import com.akash.moviebooking.api.dto.ShowResponse;
import com.akash.moviebooking.api.dto.TheaterShowProjection;
import com.akash.moviebooking.api.service.ShowService;
import com.akash.moviebooking.api.util.ApiResponse;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shows")
@RequiredArgsConstructor
@Tag(name = "Show Management", description = """
        APIs for scheduling and fetching movie shows.
        
        Access Rules:
        • Only THEATER_OWNER can schedule shows.
        • All users can fetch available shows.
        """)
public class ShowController {

    private final ShowService showService;
    private final RestResponseBuilder responseBuilder;


    @PostMapping("/theaters/{theaterId}/screens/{screenId}")
    @PreAuthorize("hasRole('THEATER_OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Schedule a new show", description = """
            Creates and schedules a new movie show for a specific screen.
            
            ACCESS:
            • Role required: THEATER_OWNER
            • JWT Bearer token required
            
            PARAMETERS:
            • theaterId – Theater ID
            • screenId – Screen ID
            • movieId – Movie ID
            • startTime – Epoch timestamp (milliseconds)
            • zoneId – Time zone ID (e.g., Asia/Kolkata)
            """)
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Show scheduled successfully"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Not a theater owner"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Theater/Screen/Movie not found")})
    public ResponseEntity<ApiResponse<ShowResponse>> createShow(

            @Parameter(description = "Theater unique ID") @PathVariable String theaterId,

            @Parameter(description = "Screen unique ID") @PathVariable String screenId,

            @Parameter(description = "Movie unique ID") @RequestParam String movieId,

            @Parameter(description = "Show start time in epoch milliseconds") @RequestParam Long startTime,

            @Parameter(description = "Time zone ID (Example: Asia/Kolkata)") @RequestParam String zoneId,

            HttpServletRequest request) {

        return responseBuilder.success(HttpStatus.CREATED, "Show scheduled successfully.", showService.addShow(theaterId, screenId, movieId, startTime, zoneId), request);
    }

    // ================= FETCH SHOWS =================

    @GetMapping
    @Operation(summary = "Fetch available shows", description = """
            Fetches available shows for a specific movie in a given city.
            
            Supports:
            • Pagination
            • Date filtering
            • City filtering
            
            Public endpoint (No authentication required).
            """)
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Shows fetched successfully"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request parameters")})
    public ResponseEntity<ApiResponse<Page<TheaterShowProjection>>> fetchShows(

            @Parameter(description = "Movie unique ID") @RequestParam String movieId,

            @Parameter(description = "Show filtering request (date, pagination, etc.)") @ModelAttribute MovieShowsRequest request,

            @Parameter(description = "City name (Example: Bhubaneswar)") @RequestParam String city,

            HttpServletRequest httpRequest) {

        return responseBuilder.success(HttpStatus.OK, "Shows fetched successfully.", showService.fetchShows(movieId, request, city), httpRequest);
    }
}