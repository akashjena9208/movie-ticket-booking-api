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
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/movies")
@Tag(name = "Movie Controller", description = "APIs for managing movies")
public class MovieController {

    private final MovieService movieService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping
    @Operation(summary = "Add a new movie", description = "Create a new movie entry in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody MovieRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.addMovie(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update movie", description = "Update details of an existing movie by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie successfully updated"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<MovieResponse> updateMovie(
            @PathVariable String id,
            @Valid @RequestBody MovieRequest request) {
        return ResponseEntity.ok(movieService.updateMovie(id, request));
    }

    @GetMapping("/{movieId}")
    @Operation(summary = "Get movie by ID", description = "Fetch movie details by movie ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie successfully fetched"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<ResponseStructure<MovieResponse>> getMovie(@PathVariable String movieId) {
        MovieResponse movieResponse = movieService.getMovie(movieId);
        return responseBuilder.sucess(HttpStatus.OK, "Movie has been successfully fetched", movieResponse);
    }

    @GetMapping("/search")
    @Operation(summary = "Search movies", description = "Search movies by title, genre, or other criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies successfully fetched")
    })
    public ResponseEntity<ResponseStructure<Set<MovieResponse>>> searchMovies(@RequestParam String search) {
        Set<MovieResponse> movieResponses = movieService.searchMovies(search);
        return responseBuilder.sucess(HttpStatus.OK, "Movies fetched successfully", movieResponses);
    }
    @DeleteMapping("/{movieId}")
    @Operation(summary = "Delete a movie", description = "Delete a movie by its ID and evict cache")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<ResponseStructure<String>> deleteMovie(@PathVariable String movieId) {
        movieService.deleteMovie(movieId);
        return responseBuilder.sucess(HttpStatus.OK, "Movie deleted successfully", "Deleted Movie ID: " + movieId);
    }


}
