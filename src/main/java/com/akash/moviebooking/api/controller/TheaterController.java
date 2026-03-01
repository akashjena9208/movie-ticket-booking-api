package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.TheaterRequest;
import com.akash.moviebooking.api.dto.TheaterResponse;
import com.akash.moviebooking.api.service.TheaterService;
import com.akash.moviebooking.api.util.ApiResponse;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theaters")
@RequiredArgsConstructor
@Tag(name = "Theater Management", description = """
        APIs for managing theaters.
        
        Access Rules:
        • Only THEATER_OWNER can create, update, delete theaters.
        • Public users can view theater details.
        • Owners can only modify their own theaters.
        """)
public class TheaterController {

    private final TheaterService theaterService;
    private final RestResponseBuilder responseBuilder;


    @PostMapping
    @PreAuthorize("hasRole('THEATER_OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create new theater", description = """
            Creates a new theater.
            
            ACCESS:
            • Role required: THEATER_OWNER
            • JWT Bearer token required
            """)
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Theater created successfully"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Not a theater owner")})
    public ResponseEntity<ApiResponse<TheaterResponse>> createTheater(@Valid @RequestBody TheaterRequest request, Authentication authentication, HttpServletRequest httpRequest) {

        return responseBuilder.success(HttpStatus.CREATED, "Theater created successfully.", theaterService.addTheater(authentication.getName(), request), httpRequest);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get theater by ID", description = "Fetches detailed information of a specific theater.")
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Theater retrieved successfully"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Theater not found")})
    public ResponseEntity<ApiResponse<TheaterResponse>> getTheater(@Parameter(description = "Theater unique ID") @PathVariable String id, HttpServletRequest httpRequest) {

        return responseBuilder.success(HttpStatus.OK, "Theater retrieved successfully.", theaterService.getTheaterById(id), httpRequest);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('THEATER_OWNER') and @theaterSecurity.isOwner(#id, authentication)")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update theater", description = """
            Updates theater details.
            
            ACCESS:
            • Role required: THEATER_OWNER
            • Must be owner of the theater
            • JWT required
            """)
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Theater updated successfully"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Not owner"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Theater not found")})
    public ResponseEntity<ApiResponse<TheaterResponse>> updateTheater(@Parameter(description = "Theater unique ID") @PathVariable String id, @Valid @RequestBody TheaterRequest request, HttpServletRequest httpRequest) {

        return responseBuilder.success(HttpStatus.OK, "Theater updated successfully.", theaterService.updateTheater(id, request), httpRequest);
    }


    @GetMapping("/my")
    @PreAuthorize("hasRole('THEATER_OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get my theaters", description = """
            Returns all theaters owned by the authenticated THEATER_OWNER.
            JWT required.
            """)
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Owner theaters retrieved successfully"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden")})
    public ResponseEntity<ApiResponse<List<TheaterResponse>>> getMyTheaters(Authentication authentication, HttpServletRequest httpRequest) {

        return responseBuilder.success(HttpStatus.OK, "Owner theaters retrieved successfully.", theaterService.getMyTheaters(authentication.getName()), httpRequest);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('THEATER_OWNER') and @theaterSecurity.isOwner(#id, authentication)")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete theater", description = """
            Deletes a theater.
            
            ACCESS:
            • Role required: THEATER_OWNER
            • Must be owner of the theater
            • JWT required
            """)
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Theater deleted successfully"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Not owner"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Theater not found")})
    public ResponseEntity<ApiResponse<String>> deleteTheater(@Parameter(description = "Theater unique ID") @PathVariable String id, HttpServletRequest httpRequest) {

        theaterService.deleteTheater(id);

        return responseBuilder.success(HttpStatus.OK, "Theater deleted successfully.", "Deleted", httpRequest);
    }
}