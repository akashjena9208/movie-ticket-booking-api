package com.akash.moviebooking.api.controller;
import com.akash.moviebooking.api.dto.MovieRequest;
import com.akash.moviebooking.api.dto.MovieResponse;
import com.akash.moviebooking.api.service.MovieService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/movies")
@Tag(name = "Movie Controller", description = "APIs for managing movies")
public class MovieController {

    private final MovieService movieService;
    private final RestResponseBuilder responseBuilder;

    // ======================================================
    // CREATE MOVIE (THEATER_OWNER ONLY)
    // ======================================================
    @PostMapping
    @PreAuthorize("hasAuthority('THEATER_OWNER')")
    @Operation(summary = "Add a new movie",
            description = "Allows THEATER_OWNER to create a new movie entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie successfully created"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Only THEATER_OWNER allowed")
    })
    public ResponseEntity<ResponseStructure<MovieResponse>> createMovie(
            @Valid @RequestBody MovieRequest request) {

        MovieResponse response = movieService.addMovie(request);

        return responseBuilder.sucess(
                HttpStatus.CREATED,
                "Movie created successfully",
                response
        );
    }

    // ======================================================
    // UPDATE MOVIE (THEATER_OWNER ONLY)
    // ======================================================
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('THEATER_OWNER')")
    @Operation(summary = "Update movie",
            description = "Allows THEATER_OWNER to update an existing movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie successfully updated"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<ResponseStructure<MovieResponse>> updateMovie(
            @PathVariable String id,
            @Valid @RequestBody MovieRequest request) {

        MovieResponse response = movieService.updateMovie(id, request);

        return responseBuilder.sucess(
                HttpStatus.OK,
                "Movie updated successfully",
                response
        );
    }

    // ======================================================
    // GET MOVIE BY ID (PUBLIC)
    // ======================================================
    @GetMapping("/{movieId}")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get movie by ID",
            description = "Fetch movie details by movie ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie successfully fetched"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<ResponseStructure<MovieResponse>> getMovie(
            @PathVariable String movieId) {

        MovieResponse movieResponse = movieService.getMovie(movieId);

        return responseBuilder.sucess(
                HttpStatus.OK,
                "Movie fetched successfully",
                movieResponse
        );
    }

    // ======================================================
    // SEARCH MOVIES (PUBLIC)
    // ======================================================
    @GetMapping("/search")

    @Operation(summary = "Search movies",
            description = "Search movies by title or genre")
    public ResponseEntity<ResponseStructure<Set<MovieResponse>>> searchMovies(
            @RequestParam String search) {

        Set<MovieResponse> movieResponses =
                movieService.searchMovies(search);

        return responseBuilder.sucess(
                HttpStatus.OK,
                "Movies fetched successfully",
                movieResponses
        );
    }

    // ======================================================
    // DELETE MOVIE (THEATER_OWNER ONLY)
    // ======================================================
    @DeleteMapping("/{movieId}")
    @PreAuthorize("hasAuthority('THEATER_OWNER')")
    @Operation(summary = "Delete movie",
            description = "Allows THEATER_OWNER to delete a movie")
    public ResponseEntity<ResponseStructure<String>> deleteMovie(
            @PathVariable String movieId) {

        movieService.deleteMovie(movieId);

        return responseBuilder.sucess(
                HttpStatus.OK,
                "Movie deleted successfully",
                "Deleted Movie ID: " + movieId
        );
    }
}
