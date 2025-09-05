
package com.akash.moviebooking.api.controller;
import com.akash.moviebooking.api.dto.ShowResponse;
import com.akash.moviebooking.api.dto.TheaterShowProjection;
import com.akash.moviebooking.api.dto.MovieShowsRequest;
import com.akash.moviebooking.api.enums.ScreenType;
import com.akash.moviebooking.api.exceptions.CityNotFoundException;
import com.akash.moviebooking.api.service.ShowService;
import com.akash.moviebooking.api.util.ResponseStructure;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api") // optional base path
public class ShowController {

    private final ShowService showService;
    private final RestResponseBuilder responseBuilder;

    // ===================== Add Show =====================
    @PostMapping("/theaters/{theaterId}/screens/{screenId}/shows")
    //@PreAuthorize("hasAuthority('THEATER_OWNER')")
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

        // Build MovieShowsRequest
        MovieShowsRequest showsRequest = new MovieShowsRequest(
                LocalDate.parse(date),
                zoneId,       // optional, defaults to UTC in service
                screenType,   // optional
                size <= 0 ? 10 : size,   // default size
                page <= 0 ? 1 : page     // default page
        );

        Page<TheaterShowProjection> shows = showService.fetchShows(movieId, showsRequest, city);
        return responseBuilder.sucess(HttpStatus.OK, "Shows fetched successfully", shows);
    }

}



























//package com.akash.moviebooking.api.controller;
//import com.akash.moviebooking.api.dto.MovieShowsRequest;
//import com.akash.moviebooking.api.dto.ShowResponse;
//import com.akash.moviebooking.api.dto.TheaterShowProjection;
//import com.akash.moviebooking.api.exceptions.CityNotFoundException;
//import com.akash.moviebooking.api.service.ShowService;
//import com.akash.moviebooking.api.util.ResponseStructure;
//import com.akash.moviebooking.api.util.RestResponseBuilder;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//
//@RestController
//@AllArgsConstructor
//@Validated
//public class ShowController {
//
//    private final ShowService showService;
//    private final RestResponseBuilder responseBuilder;
//
////    @PostMapping("theaters/{theaterId}/screens/{screenId}/shows")
////    //@PreAuthorize("hasAuthority('THEATER_OWNER')")
////    public ResponseEntity<ResponseStructure<ShowResponse>> addShow(@PathVariable String theaterId, @PathVariable String screenId, String movieId , @NotNull Long startTime, @NotNull String zoneId ){
////        ShowResponse showResponse = showService.addShow(theaterId, screenId, movieId, startTime, zoneId);
////        return responseBuilder.sucess(HttpStatus.OK, "Show sucessfully created", showResponse);
////    }
//
//    @PostMapping("theaters/{theaterId}/screens/{screenId}/shows")
////@PreAuthorize("hasAuthority('THEATER_OWNER')")
//    public ResponseEntity<ResponseStructure<ShowResponse>> addShow(
//            @PathVariable String theaterId,
//            @PathVariable String screenId,
//            @RequestParam String movieId,
//            @RequestParam @NotNull Long startTime,
//            @RequestParam @NotNull String zoneId) {
//
//        ShowResponse showResponse = showService.addShow(theaterId, screenId, movieId, startTime, zoneId);
//        return responseBuilder.sucess(HttpStatus.OK, "Show sucessfully created", showResponse);
//    }
//
//
////    @GetMapping("movies/{movieId}/shows")
////    public ResponseEntity<ResponseStructure<Page<TheaterShowProjection>>> fetchShows(
////            @PathVariable String movieId,
////            @RequestBody MovieShowsRequest showsRequest,
////            @RequestHeader(value = "X-City", required = false) String city // Header for city
////    ) {
////        Page<TheaterShowProjection> shows = showService.fetchShows(movieId, showsRequest, city);
////        return responseBuilder.sucess(HttpStatus.OK, "Fetched Succesfully", shows);
////    }
//
////    @GetMapping("/{movieId}/shows")
////    public ResponseEntity<ResponseStructure<Page<TheaterShowProjection>>> fetchShows(
////            @PathVariable String movieId,
////            @RequestBody @Valid MovieShowsRequest showsRequest,
////            @RequestHeader(value = "X-City", required = true) String city) {
////
////        if (city == null || city.isBlank()) {
////            // Optional: You can throw a custom exception or return bad request if city is missing
////            throw new CityNotFoundException("City header 'X-City' is required");
////        }
////
////        Page<TheaterShowProjection> shows = showService.fetchShows(movieId, showsRequest, city);
////
////        return responseBuilder.sucess(HttpStatus.OK, "Shows fetched successfully", shows);
////    }
//
//
//
//
//
//
//


















































