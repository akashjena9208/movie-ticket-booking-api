////package com.akash.moviebooking.api.controller;
////
////import com.akash.moviebooking.api.dto.*;
////import com.akash.moviebooking.api.service.ScreenService;
////import com.akash.moviebooking.api.util.*;
////import io.swagger.v3.oas.annotations.tags.Tag;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.validation.Valid;
////import lombok.RequiredArgsConstructor;
////import org.springframework.http.*;
////import org.springframework.security.access.prepost.PreAuthorize;
////import org.springframework.web.bind.annotation.*;
////
////@RestController
////@RequestMapping("/theaters/{theaterId}/screens")
////@RequiredArgsConstructor
////@Tag(name = "Screen Management")
////public class ScreenController {
////
////    private final ScreenService screenService;
////    private final RestResponseBuilder responseBuilder;
////
////    @PostMapping
////    @PreAuthorize("hasAuthority('THEATER_OWNER')")
////    public ResponseEntity<ApiResponse<ScreenResponse>> addScreen(
////            @PathVariable String theaterId,
////            @Valid @RequestBody ScreenRequest request,
////            HttpServletRequest httpRequest) {
////
////        return responseBuilder.success(
////                HttpStatus.CREATED,
////                "Screen created successfully.",
////                screenService.addScreen(request, theaterId),
////                httpRequest
////        );
////    }
////
////    @GetMapping("/{screenId}")
////    public ResponseEntity<ApiResponse<ScreenResponse>> getScreen(
////            @PathVariable String theaterId,
////            @PathVariable String screenId,
////            HttpServletRequest httpRequest) {
////
////        return responseBuilder.success(
////                HttpStatus.OK,
////                "Screen fetched successfully.",
////                screenService.getScreen(theaterId, screenId),
////                httpRequest
////        );
////    }
////}
//package com.akash.moviebooking.api.controller;
//
//import com.akash.moviebooking.api.dto.*;
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
//    @PostMapping
////    @PreAuthorize("hasAuthority('THEATER_OWNER')")
//    @PreAuthorize("hasRole('THEATER_OWNER')")
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
@Tag(name = "Screen Management")
public class ScreenController {

    private final ScreenService screenService;
    private final RestResponseBuilder responseBuilder;

    // ================= CREATE SCREEN =================
    @PostMapping
    @PreAuthorize("hasRole('THEATER_OWNER') and @theaterSecurity.isOwner(#theaterId, authentication)")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<ScreenResponse>> addScreen(
            @PathVariable String theaterId,
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
    public ResponseEntity<ApiResponse<ScreenResponse>> getScreen(
            @PathVariable String theaterId,
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