////package com.akash.moviebooking.api.controller;
////
////import com.akash.moviebooking.api.dto.*;
////import com.akash.moviebooking.api.service.TheaterService;
////import com.akash.moviebooking.api.util.*;
////import io.swagger.v3.oas.annotations.Operation;
////import io.swagger.v3.oas.annotations.tags.Tag;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.validation.Valid;
////import lombok.RequiredArgsConstructor;
////import org.springframework.http.*;
////import org.springframework.security.access.prepost.PreAuthorize;
////import org.springframework.web.bind.annotation.*;
////
////@RestController
////@RequestMapping("/theaters")
////@RequiredArgsConstructor
////@Tag(name = "Theater Management")
////public class TheaterController {
////
////    private final TheaterService theaterService;
////    private final RestResponseBuilder responseBuilder;
////
////    @PostMapping
////    @PreAuthorize("hasAuthority('THEATER_OWNER')")
////    public ResponseEntity<ApiResponse<TheaterResponse>> createTheater(
////            @RequestParam String email,
////            @Valid @RequestBody TheaterRequest request,
////            HttpServletRequest httpRequest) {
////
////        return responseBuilder.success(
////                HttpStatus.CREATED,
////                "Theater created successfully.",
////                theaterService.addTheater(email, request),
////                httpRequest
////        );
////    }
////
////    @GetMapping("/{id}")
////    public ResponseEntity<ApiResponse<TheaterResponse>> getTheater(
////            @PathVariable String id,
////            HttpServletRequest httpRequest) {
////
////        return responseBuilder.success(
////                HttpStatus.OK,
////                "Theater fetched successfully.",
////                theaterService.getTheaterById(id),
////                httpRequest
////        );
////    }
////
////    @PutMapping("/{id}")
////    @PreAuthorize("hasAuthority('THEATER_OWNER')")
////    public ResponseEntity<ApiResponse<TheaterResponse>> updateTheater(
////            @PathVariable String id,
////            @Valid @RequestBody TheaterRequest request,
////            HttpServletRequest httpRequest) {
////
////        return responseBuilder.success(
////                HttpStatus.OK,
////                "Theater updated successfully.",
////                theaterService.updateTheater(id, request),
////                httpRequest
////        );
////    }
////}
//package com.akash.moviebooking.api.controller;
//
//import com.akash.moviebooking.api.dto.*;
//import com.akash.moviebooking.api.service.TheaterService;
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
//@RequestMapping("/theaters")
//@RequiredArgsConstructor
//@Tag(name = "Theater Management")
//public class TheaterController {
//
//    private final TheaterService theaterService;
//    private final RestResponseBuilder responseBuilder;
//
//    @PostMapping
////    @PreAuthorize("hasAuthority('THEATER_OWNER')")
//    @PreAuthorize("hasRole('THEATER_OWNER')")
//    @SecurityRequirement(name = "bearerAuth")
//    public ResponseEntity<ApiResponse<TheaterResponse>> createTheater(
//            @RequestParam String ownerEmail,
//            @Valid @RequestBody TheaterRequest request,
//            HttpServletRequest httpRequest) {
//
//        return responseBuilder.success(
//                HttpStatus.CREATED,
//                "Theater created successfully.",
//                theaterService.addTheater(ownerEmail, request),
//                httpRequest
//        );
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse<TheaterResponse>> getTheater(
//            @PathVariable String id,
//            HttpServletRequest httpRequest) {
//
//        return responseBuilder.success(
//                HttpStatus.OK,
//                "Theater retrieved successfully.",
//                theaterService.getTheaterById(id),
//                httpRequest
//        );
//    }
//}
package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.TheaterRequest;
import com.akash.moviebooking.api.dto.TheaterResponse;
import com.akash.moviebooking.api.service.TheaterService;
import com.akash.moviebooking.api.util.ApiResponse;
import com.akash.moviebooking.api.util.RestResponseBuilder;
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
@Tag(name = "Theater Management")
public class TheaterController {

    private final TheaterService theaterService;
    private final RestResponseBuilder responseBuilder;

    // ================= CREATE =================
    @PostMapping
    @PreAuthorize("hasRole('THEATER_OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<TheaterResponse>> createTheater(
            @Valid @RequestBody TheaterRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.CREATED,
                "Theater created successfully.",
                theaterService.addTheater(authentication.getName(), request),
                httpRequest
        );
    }

    // ================= GET =================
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TheaterResponse>> getTheater(
            @PathVariable String id,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Theater retrieved successfully.",
                theaterService.getTheaterById(id),
                httpRequest
        );
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('THEATER_OWNER') and @theaterSecurity.isOwner(#id, authentication)")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<TheaterResponse>> updateTheater(
            @PathVariable String id,
            @Valid @RequestBody TheaterRequest request,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Theater updated successfully.",
                theaterService.updateTheater(id, request),
                httpRequest
        );
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('THEATER_OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<List<TheaterResponse>>> getMyTheaters(
            Authentication authentication,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Owner theaters retrieved successfully.",
                theaterService.getMyTheaters(authentication.getName()),
                httpRequest
        );
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('THEATER_OWNER') and @theaterSecurity.isOwner(#id, authentication)")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<String>> deleteTheater(
            @PathVariable String id,
            HttpServletRequest httpRequest) {

        theaterService.deleteTheater(id);

        return responseBuilder.success(
                HttpStatus.OK,
                "Theater deleted successfully.",
                "Deleted",
                httpRequest
        );
    }
}