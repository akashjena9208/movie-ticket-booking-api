package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.MovieRequest;
import com.akash.moviebooking.api.dto.MovieResponse;
import com.akash.moviebooking.api.service.MovieService;
import com.akash.moviebooking.api.util.ResponseStructure;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody MovieRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.addMovie(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable String id, @Valid @RequestBody MovieRequest request) {
        return ResponseEntity.ok(movieService.updateMovie(id, request));
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<ResponseStructure<MovieResponse>> getMovie(@PathVariable String movieId){
        MovieResponse movieResponse = movieService.getMovie(movieId);
        return responseBuilder.sucess(HttpStatus.OK, "Movie has been successfully fetched", movieResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseStructure<Set<MovieResponse>>> searchMovies( @RequestParam String search){
        Set<MovieResponse> movieResponses = movieService.searchMovies(search);
        return responseBuilder.sucess(HttpStatus.OK, "Movies fetched Successfully", movieResponses);
    }

}






















//    @PostMapping("/movies")
//    //@PreAuthorize("hasAuthority('ADMIN')") // or THEATER_OWNER depending on your business rules
//    public ResponseEntity<ResponseStructure<MovieResponse>> addMovie(
//            @RequestBody @Valid MovieRequest movieRequest) {
//        MovieResponse movieResponse = movieService.addMovie(movieRequest);
//        return responseBuilder.sucess(HttpStatus.CREATED, "Movie successfully added", movieResponse);
//    }