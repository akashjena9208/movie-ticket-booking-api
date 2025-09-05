package com.akash.moviebooking.api.service;

import com.akash.moviebooking.api.dto.MovieRequest;
import com.akash.moviebooking.api.dto.MovieResponse;
import jakarta.validation.Valid;

import java.util.Set;

public interface MovieService {
    MovieResponse getMovie(String movieId);

    Set<MovieResponse> searchMovies(String search);


    MovieResponse addMovie(MovieRequest request);
    MovieResponse updateMovie(String movieId, MovieRequest request);
}
