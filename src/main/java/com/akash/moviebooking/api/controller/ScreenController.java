//package com.akash.moviebooking.api.controller;
//
//import com.akash.moviebooking.api.dto.ScreenRequest;
//import com.akash.moviebooking.api.dto.ScreenResponse;
//import com.akash.moviebooking.api.service.ScreenService;
//import com.akash.moviebooking.api.util.ApiResponse;
//import com.akash.moviebooking.api.util.RestResponseBuilder;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/theaters/{theaterId}/screens")
//@RequiredArgsConstructor
//@Tag(name = "Screen Management")
//public class ScreenController {
//
//    private final ScreenService screenService;
//    private final RestResponseBuilder responseBuilder;
//
//    // ================= CREATE SCREEN =================
//    @PostMapping
//    @PreAuthorize("hasRole('THEATER_OWNER') and @theaterSecurity.isOwner(#theaterId, authentication)")
//    @SecurityRequirement(name = "bearerAuth")
//    public ResponseEntity<ApiResponse<ScreenResponse>> addScreen(
//            @PathVariable String theaterId,
//            @Valid @RequestBody ScreenRequest request,
//            HttpServletRequest httpRequest) {
//
//        return responseBuilder.success(
//                HttpStatus.CREATED,
//                "Screen created successfully.",
//                screenService.addScreen(request, theaterId),
//                httpRequest
//        );
//    }
//
//    // ================= GET SCREEN =================
//    @GetMapping("/{screenId}")
//    public ResponseEntity<ApiResponse<ScreenResponse>> getScreen(
//            @PathVariable String theaterId,
//            @PathVariable String screenId,
//            HttpServletRequest httpRequest) {
//
//        return responseBuilder.success(
//                HttpStatus.OK,
//                "Screen retrieved successfully.",
//                screenService.getScreen(theaterId, screenId),
//                httpRequest
//        );
//    }
//}
package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.ScreenRequest;
import com.akash.moviebooking.api.dto.ScreenResponse;
import com.akash.moviebooking.api.service.ScreenService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theaters/{theaterId}/screens")
@RequiredArgsConstructor
@Tag(
        name = "Screen Management",
        description = """
                APIs for managing theater screens.

                Access Rules:
                • Only THEATER_OWNER can create screens.
                • Public access allowed for fetching screen details.
                """
)
public class ScreenController {

    private final ScreenService screenService;
    private final RestResponseBuilder responseBuilder;

    // ================= CREATE SCREEN =================

    @PostMapping
    @PreAuthorize("hasRole('THEATER_OWNER') and @theaterSecurity.isOwner(#theaterId, authentication)")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Create a new screen",
            description = """
                    Creates a new screen inside a specific theater.

                    ACCESS:
                    • Role required: THEATER_OWNER
                    • Must be owner of the theater
                    • JWT Bearer token required

                    The request includes:
                    • Screen type (e.g., IMAX, 2D, 3D)
                    • Capacity
                    • Row configuration
                    • Seat generation details
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Screen created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Not theater owner"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Theater not found")
    })
    public ResponseEntity<ApiResponse<ScreenResponse>> addScreen(

            @Parameter(description = "Theater unique ID")
            @PathVariable String theaterId,

            @Parameter(description = "Screen creation request payload")
            @Valid @RequestBody ScreenRequest request,

            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.CREATED,
                "Screen created successfully.",
                screenService.addScreen(request, theaterId),
                httpRequest
        );
    }

    // ================= GET SCREEN =================

    @GetMapping("/{screenId}")
    @Operation(
            summary = "Get screen details",
            description = """
                    Retrieves complete details of a specific screen,
                    including seat configuration.

                    Public endpoint (no authentication required).
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Screen retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Screen not found")
    })
    public ResponseEntity<ApiResponse<ScreenResponse>> getScreen(

            @Parameter(description = "Theater unique ID")
            @PathVariable String theaterId,

            @Parameter(description = "Screen unique ID")
            @PathVariable String screenId,

            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Screen retrieved successfully.",
                screenService.getScreen(theaterId, screenId),
                httpRequest
        );
    }
}