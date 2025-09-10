package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.ScreenRequest;
import com.akash.moviebooking.api.dto.ScreenResponse;
import com.akash.moviebooking.api.service.ScreenService;
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

@RestController
@AllArgsConstructor
@RequestMapping("/theaters/{theaterId}/screens")
@Tag(name = "Screen Controller", description = "APIs for managing screens inside theaters")
public class ScreenController {

    private final ScreenService screenService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping
    //@PreAuthorize("hasAuthority('THEATER_OWNER')")
    @Operation(summary = "Add a new screen", description = "Allows a THEATER_OWNER to add a new screen inside a theater")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Screen successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid screen request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - not logged in"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only THEATER_OWNER can create screens"),
            @ApiResponse(responseCode = "404", description = "Theater not found")
    })
    public ResponseEntity<ResponseStructure<ScreenResponse>> addScreen(
            @RequestBody @Valid ScreenRequest screenRequest,
            @PathVariable String theaterId) {
        ScreenResponse screenResponse = screenService.addScreen(screenRequest, theaterId);
        return responseBuilder.sucess(HttpStatus.OK, "Screen has been successfully created", screenResponse);
    }

    @GetMapping("/{screenId}")
    @Operation(summary = "Get screen by ID", description = "Fetch details of a screen inside a specific theater")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Screen successfully fetched"),
            @ApiResponse(responseCode = "404", description = "Screen or Theater not found")
    })
    public ResponseEntity<ResponseStructure<ScreenResponse>> findScreen(
            @PathVariable String theaterId,
            @PathVariable String screenId) {
        ScreenResponse screenResponse = screenService.findScreen(theaterId, screenId);
        return responseBuilder.sucess(HttpStatus.OK, "Screen has been successfully fetched", screenResponse);
    }
}
