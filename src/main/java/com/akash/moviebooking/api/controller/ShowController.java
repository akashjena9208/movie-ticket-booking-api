package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.ShowResponse;
import com.akash.moviebooking.api.dto.TheaterShowProjection;
import com.akash.moviebooking.api.dto.MovieShowsRequest;
import com.akash.moviebooking.api.enums.ScreenType;
import com.akash.moviebooking.api.exceptions.CityNotFoundException;
import com.akash.moviebooking.api.service.ShowService;
import com.akash.moviebooking.api.util.ResponseStructure;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/shows")
@Tag(name = "Show Controller", description = "APIs for managing shows in theaters")
public class ShowController {

    private final ShowService showService;
    private final RestResponseBuilder responseBuilder;

    // ===================== Add Show =====================
    @PostMapping("/theaters/{theaterId}/screens/{screenId}/shows")
    //@PreAuthorize("hasAuthority('THEATER_OWNER')")
    @Operation(summary = "Add a new show", description = "Allows THEATER_OWNER to schedule a show for a movie in a specific screen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Show successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - user not logged in"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only THEATER_OWNER can create shows"),
            @ApiResponse(responseCode = "404", description = "Theater, Screen, or Movie not found")
    })
    public ResponseEntity<ResponseStructure<ShowResponse>> addShow(
            @PathVariable String theaterId,
            @PathVariable String screenId,
            @RequestParam String movieId,
            @RequestParam @NotNull Long startTime,
            @RequestParam @NotNull String zoneId) {

        ShowResponse showResponse = showService.addShow(theaterId, screenId, movieId, startTime, zoneId);
        return responseBuilder.sucess(HttpStatus.CREATED, "Show successfully created", showResponse);
    }

    // ===================== Fetch Shows =====================
    @GetMapping("/movies/{movieId}/shows")
    @Operation(summary = "Fetch available shows for a movie", description = "Get all shows for a specific movie in a city, with optional filters like date, screen type, and zone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shows successfully fetched"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Movie not found or city missing")
    })
    public ResponseEntity<ResponseStructure<Page<TheaterShowProjection>>> fetchShows(
            @PathVariable String movieId,
            @RequestParam String date, // format: yyyy-MM-dd
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) ScreenType screenType,
            @RequestParam(required = false) String zoneId,
            @RequestHeader("X-City") String city) {

        if (city == null || city.isBlank()) {
            throw new CityNotFoundException("City header 'X-City' is required");
        }

        MovieShowsRequest showsRequest = new MovieShowsRequest(
                LocalDate.parse(date),
                zoneId,
                screenType,
                size <= 0 ? 10 : size,
                page <= 0 ? 1 : page
        );

        Page<TheaterShowProjection> shows = showService.fetchShows(movieId, showsRequest, city);
        return responseBuilder.sucess(HttpStatus.OK, "Shows fetched successfully", shows);
    }
}
