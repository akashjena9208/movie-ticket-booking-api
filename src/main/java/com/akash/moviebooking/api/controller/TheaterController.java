package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.TheaterRequest;
import com.akash.moviebooking.api.dto.TheaterResponse;
import com.akash.moviebooking.api.service.TheaterService;
import com.akash.moviebooking.api.util.ResponseStructure;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/theaters")
@Tag(name = "Theater Controller", description = "APIs for managing theaters")
public class TheaterController {

    private final TheaterService theaterService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping
    //@PreAuthorize("hasAuthority('THEATER_OWNER')")
    @Operation(summary = "Add a theater", description = "Allows THEATER_OWNER to register a new theater")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Theater successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - user not logged in"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only THEATER_OWNER can add theaters")
    })
    public ResponseEntity<ResponseStructure<TheaterResponse>> addTheater(
            @RequestParam String email,
            @Valid @RequestBody TheaterRequest theaterRequest) {
        TheaterResponse theaterResponse = theaterService.addTheater(email, theaterRequest);
        return responseBuilder.sucess(HttpStatus.OK, "Theater has been successfully created", theaterResponse);
    }

    @GetMapping("/{theaterId}")
    @Operation(summary = "Get theater by ID", description = "Fetch details of a specific theater by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Theater successfully fetched"),
            @ApiResponse(responseCode = "404", description = "Theater not found")
    })
    public ResponseEntity<ResponseStructure<TheaterResponse>> findTheater(@PathVariable String theaterId) {
        TheaterResponse theaterResponse = theaterService.findTheater(theaterId);
        return responseBuilder.sucess(HttpStatus.OK, "Theater has been successfully fetched", theaterResponse);
    }

    @PutMapping("/{theaterId}")
    //@PreAuthorize("hasAuthority('THEATER_OWNER')")
    @Operation(summary = "Update theater", description = "Allows THEATER_OWNER to update details of a specific theater")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Theater successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - user not logged in"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only THEATER_OWNER can update theaters"),
            @ApiResponse(responseCode = "404", description = "Theater not found")
    })
    public ResponseEntity<ResponseStructure<TheaterResponse>> updateTheater(
            @PathVariable String theaterId,
            @Valid @RequestBody TheaterRequest registerationRequest) {
        TheaterResponse theaterResponse = theaterService.updateTheater(theaterId, registerationRequest);
        return responseBuilder.sucess(HttpStatus.OK, "Theater has been successfully updated", theaterResponse);
    }
}
