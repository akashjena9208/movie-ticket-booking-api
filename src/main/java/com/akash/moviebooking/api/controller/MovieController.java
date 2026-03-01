//package com.akash.moviebooking.api.controller;
//
//import com.akash.moviebooking.api.dto.*;
//import com.akash.moviebooking.api.service.MovieService;
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
//import java.util.Set;
//
//@RestController
//@RequestMapping("/movies")
//@RequiredArgsConstructor
//@Tag(name = "Movie Management")
//public class MovieController {
//
//    private final MovieService movieService;
//    private final RestResponseBuilder responseBuilder;
//
//    @PostMapping
//    //@PreAuthorize("hasAuthority('THEATER_OWNER')")
//    @PreAuthorize("hasRole('THEATER_OWNER')")
//    @SecurityRequirement(name = "bearerAuth")
//    public ResponseEntity<ApiResponse<MovieResponse>> createMovie(
//            @Valid @RequestBody MovieRequest request,
//            HttpServletRequest httpRequest) {
//
//        return responseBuilder.success(
//                HttpStatus.CREATED,
//                "Movie created successfully.",
//                movieService.addMovie(request),
//                httpRequest
//        );
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse<MovieResponse>> getMovie(
//            @PathVariable String id,
//            HttpServletRequest httpRequest) {
//
//        return responseBuilder.success(
//                HttpStatus.OK,
//                "Movie retrieved successfully.",
//                movieService.getMovieById(id),
//                httpRequest
//        );
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<ApiResponse<Set<MovieResponse>>> searchMovies(
//            @RequestParam String search,
//            HttpServletRequest httpRequest) {
//
//        return responseBuilder.success(
//                HttpStatus.OK,
//                "Movies retrieved successfully.",
//                movieService.searchMovies(search),
//                httpRequest
//        );
//    }
//}
package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.*;
import com.akash.moviebooking.api.service.MovieService;
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

import java.util.Set;
@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Tag(name = "Movie Management")
public class MovieController {

    private final MovieService movieService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping
    @PreAuthorize("hasRole('THEATER_OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<MovieResponse>> createMovie(
            @Valid @RequestBody MovieRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.CREATED,
                "Movie created successfully.",
                movieService.addMovie(request, authentication.getName()),
                httpRequest
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('THEATER_OWNER') and @movieSecurity.isOwner(#id, authentication)")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<MovieResponse>> updateMovie(
            @PathVariable String id,
            @Valid @RequestBody MovieRequest request,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Movie updated successfully.",
                movieService.updateMovie(id, request),
                httpRequest
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('THEATER_OWNER') and @movieSecurity.isOwner(#id, authentication)")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<String>> deleteMovie(
            @PathVariable String id,
            HttpServletRequest httpRequest) {

        movieService.deleteMovie(id);

        return responseBuilder.success(
                HttpStatus.OK,
                "Movie deleted successfully.",
                "Deleted",
                httpRequest
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponse>> getMovie(
            @PathVariable String id,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Movie retrieved successfully.",
                movieService.getMovieById(id),
                httpRequest
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Set<MovieResponse>>> searchMovies(
            @RequestParam String search,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Movies retrieved successfully.",
                movieService.searchMovies(search),
                httpRequest
        );
    }
}