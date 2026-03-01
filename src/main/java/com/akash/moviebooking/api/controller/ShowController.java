//////////package com.akash.moviebooking.api.controller;
//////////
//////////import com.akash.moviebooking.api.dto.*;
//////////import com.akash.moviebooking.api.service.ShowService;
//////////import com.akash.moviebooking.api.util.*;
//////////import io.swagger.v3.oas.annotations.tags.Tag;
//////////import jakarta.servlet.http.HttpServletRequest;
//////////import lombok.RequiredArgsConstructor;
//////////import org.springframework.data.domain.Page;
//////////import org.springframework.http.*;
//////////import org.springframework.security.access.prepost.PreAuthorize;
//////////import org.springframework.web.bind.annotation.*;
//////////
//////////@RestController
//////////@RequestMapping("/shows")
//////////@RequiredArgsConstructor
//////////@Tag(name = "Show Management")
//////////public class ShowController {
//////////
//////////    private final ShowService showService;
//////////    private final RestResponseBuilder responseBuilder;
//////////
//////////    @PostMapping("/theaters/{theaterId}/screens/{screenId}")
//////////    @PreAuthorize("hasAuthority('THEATER_OWNER')")
//////////    public ResponseEntity<ApiResponse<ShowResponse>> addShow(
//////////            @PathVariable String theaterId,
//////////            @PathVariable String screenId,
//////////            @RequestParam String movieId,
//////////            @RequestParam Long startTime,
//////////            @RequestParam String zoneId,
//////////            HttpServletRequest httpRequest) {
//////////
//////////        return responseBuilder.success(
//////////                HttpStatus.CREATED,
//////////                "Show scheduled successfully.",
//////////                showService.addShow(theaterId, screenId, movieId, startTime, zoneId),
//////////                httpRequest
//////////        );
//////////    }
//////////}
////////package com.akash.moviebooking.api.controller;
////////
////////import com.akash.moviebooking.api.dto.ShowResponse;
////////import com.akash.moviebooking.api.service.ShowService;
////////import com.akash.moviebooking.api.util.ApiResponse;
////////import com.akash.moviebooking.api.util.RestResponseBuilder;
////////import io.swagger.v3.oas.annotations.security.SecurityRequirement;
////////import io.swagger.v3.oas.annotations.tags.Tag;
////////import jakarta.servlet.http.HttpServletRequest;
////////import lombok.RequiredArgsConstructor;
////////import org.springframework.http.HttpStatus;
////////import org.springframework.http.ResponseEntity;
////////import org.springframework.security.access.prepost.PreAuthorize;
////////import org.springframework.web.bind.annotation.*;
////////
////////@RestController
////////@RequestMapping("/shows")
////////@RequiredArgsConstructor
////////@Tag(name = "Show Management")
////////public class ShowController {
////////
////////    private final ShowService showService;
////////    private final RestResponseBuilder responseBuilder;
////////
////////    @PostMapping("/theaters/{theaterId}/screens/{screenId}")
////////    //@PreAuthorize("hasAuthority('THEATER_OWNER')")
////////    @PreAuthorize("hasRole('THEATER_OWNER')")
////////    @SecurityRequirement(name = "bearerAuth")
////////    public ResponseEntity<ApiResponse<ShowResponse>> createShow(
////////            @PathVariable String theaterId,
////////            @PathVariable String screenId,
////////            @RequestParam String movieId,
////////            @RequestParam Long startTime,
////////            @RequestParam String zoneId,
////////            HttpServletRequest httpRequest) {
////////
////////        return responseBuilder.success(
////////                HttpStatus.CREATED,
////////                "Show scheduled successfully.",
////////                showService.addShow(theaterId, screenId, movieId, startTime, zoneId),
////////                httpRequest
////////        );
////////    }
////////}
//////
//////package com.akash.moviebooking.api.controller;
//////
//////import com.akash.moviebooking.api.dto.MovieShowsRequest;
//////import com.akash.moviebooking.api.dto.ShowResponse;
//////import com.akash.moviebooking.api.dto.TheaterShowProjection;
//////import com.akash.moviebooking.api.service.ShowService;
//////import com.akash.moviebooking.api.util.ApiResponse;
//////import com.akash.moviebooking.api.util.RestResponseBuilder;
//////import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//////import io.swagger.v3.oas.annotations.tags.Tag;
//////import jakarta.servlet.http.HttpServletRequest;
//////import lombok.RequiredArgsConstructor;
//////import org.springframework.data.domain.Page;
//////import org.springframework.http.HttpStatus;
//////import org.springframework.http.ResponseEntity;
//////import org.springframework.security.access.prepost.PreAuthorize;
//////import org.springframework.web.bind.annotation.*;
//////
//////@RestController
//////@RequestMapping("/shows")
//////@RequiredArgsConstructor
//////@Tag(name = "Show Management")
//////public class ShowController {
//////
//////    private final ShowService showService;
//////    private final RestResponseBuilder responseBuilder;
//////
//////    // ================= CREATE SHOW =================
//////    @PostMapping("/theaters/{theaterId}/screens/{screenId}")
//////    @PreAuthorize("hasRole('THEATER_OWNER')")
//////    @SecurityRequirement(name = "bearerAuth")
//////    public ResponseEntity<ApiResponse<ShowResponse>> createShow(
//////            @PathVariable String theaterId,
//////            @PathVariable String screenId,
//////            @RequestParam String movieId,
//////            @RequestParam Long startTime,
//////            @RequestParam String zoneId,
//////            HttpServletRequest request) {
//////
//////        return responseBuilder.success(
//////                HttpStatus.CREATED,
//////                "Show scheduled successfully.",
//////                showService.addShow(theaterId, screenId, movieId, startTime, zoneId),
//////                request
//////        );
//////    }
//////
//////    // ================= FETCH SHOWS =================
//////    @GetMapping("/movies/{movieId}")
//////    public ResponseEntity<ApiResponse<Page<TheaterShowProjection>>> fetchShows(
//////            @PathVariable String movieId,
//////            MovieShowsRequest request,
//////            @RequestParam String city,
//////            HttpServletRequest httpRequest) {
//////
//////        return responseBuilder.success(
//////                HttpStatus.OK,
//////                "Shows fetched successfully.",
//////                showService.fetchShows(movieId, request, city),
//////                httpRequest
//////        );
//////    }
//////}
////package com.akash.moviebooking.api.controller;
////
////import com.akash.moviebooking.api.dto.MovieShowsRequest;
////import com.akash.moviebooking.api.dto.ShowResponse;
////import com.akash.moviebooking.api.dto.TheaterShowProjection;
////import com.akash.moviebooking.api.service.ShowService;
////import com.akash.moviebooking.api.util.ApiResponse;
////import com.akash.moviebooking.api.util.RestResponseBuilder;
////import io.swagger.v3.oas.annotations.security.SecurityRequirement;
////import io.swagger.v3.oas.annotations.tags.Tag;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.validation.Valid;
////import lombok.RequiredArgsConstructor;
////import org.springframework.data.domain.Page;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.security.access.prepost.PreAuthorize;
////import org.springframework.web.bind.annotation.*;
////
////@RestController
////@RequestMapping("/shows")
////@RequiredArgsConstructor
////@Tag(name = "Show Management")
////public class ShowController {
////
////    private final ShowService showService;
////    private final RestResponseBuilder responseBuilder;
////
////    // ================= CREATE =================
////    @PostMapping("/theaters/{theaterId}/screens/{screenId}")
////    @PreAuthorize("hasRole('THEATER_OWNER') and @theaterSecurity.isOwner(#theaterId, authentication)")
////    @SecurityRequirement(name = "bearerAuth")
////    public ResponseEntity<ApiResponse<ShowResponse>> createShow(
////            @PathVariable String theaterId,
////            @PathVariable String screenId,
////            @RequestParam String movieId,
////            @RequestParam Long startTime,
////            @RequestParam String zoneId,
////            HttpServletRequest httpRequest) {
////
////        return responseBuilder.success(
////                HttpStatus.CREATED,
////                "Show scheduled successfully.",
////                showService.addShow(theaterId, screenId, movieId, startTime, zoneId),
////                httpRequest
////        );
////    }
////
////    // ================= FETCH =================
////    @PostMapping("/movies/{movieId}")
////    public ResponseEntity<ApiResponse<Page<TheaterShowProjection>>> fetchShows(
////            @PathVariable String movieId,
////            @Valid @RequestBody MovieShowsRequest request,
////            @RequestParam String city,
////            HttpServletRequest httpRequest) {
////
////        return responseBuilder.success(
////                HttpStatus.OK,
////                "Shows fetched successfully.",
////                showService.fetchShows(movieId, request, city),
////                httpRequest
////        );
////    }
////}
//package com.akash.moviebooking.api.controller;
//
//import com.akash.moviebooking.api.dto.ShowResponse;
//import com.akash.moviebooking.api.service.ShowService;
//import com.akash.moviebooking.api.util.ApiResponse;
//import com.akash.moviebooking.api.util.RestResponseBuilder;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/shows")
//@RequiredArgsConstructor
//@Tag(name = "Show Management")
//public class ShowController {
//
//    private final ShowService showService;
//    private final RestResponseBuilder responseBuilder;
//
//    @PostMapping("/theaters/{theaterId}/screens/{screenId}")
//    @PreAuthorize("hasRole('THEATER_OWNER')")
//    @SecurityRequirement(name = "bearerAuth")
//    public ResponseEntity<ApiResponse<ShowResponse>> createShow(
//            @PathVariable String theaterId,
//            @PathVariable String screenId,
//            @RequestParam String movieId,
//            @RequestParam Long startTime,
//            @RequestParam String zoneId,
//            HttpServletRequest request) {
//
//        return responseBuilder.success(
//                HttpStatus.CREATED,
//                "Show scheduled successfully.",
//                showService.addShow(
//                        theaterId,
//                        screenId,
//                        movieId,
//                        startTime,
//                        zoneId),
//                request
//        );
//    }
//}
package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.MovieShowsRequest;
import com.akash.moviebooking.api.dto.ShowResponse;
import com.akash.moviebooking.api.dto.TheaterShowProjection;
import com.akash.moviebooking.api.service.ShowService;
import com.akash.moviebooking.api.util.ApiResponse;
import com.akash.moviebooking.api.util.RestResponseBuilder;
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
@Tag(name = "Show Management")
public class ShowController {

    private final ShowService showService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping("/theaters/{theaterId}/screens/{screenId}")
    @PreAuthorize("hasRole('THEATER_OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<ShowResponse>> createShow(
            @PathVariable String theaterId,
            @PathVariable String screenId,
            @RequestParam String movieId,
            @RequestParam Long startTime,
            @RequestParam String zoneId,
            HttpServletRequest request) {

        return responseBuilder.success(
                HttpStatus.CREATED,
                "Show scheduled successfully.",
                showService.addShow(theaterId, screenId, movieId, startTime, zoneId),
                request
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TheaterShowProjection>>> fetchShows(
            @RequestParam String movieId,
            @ModelAttribute MovieShowsRequest request,
            @RequestParam String city,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Shows fetched successfully.",
                showService.fetchShows(movieId, request, city),
                httpRequest
        );
    }
}