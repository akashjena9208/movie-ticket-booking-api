package com.akash.moviebooking.api.service;

import com.akash.moviebooking.api.dto.MovieRequest;
import com.akash.moviebooking.api.dto.MovieResponse;

import java.util.Set;

//
//package com.akash.moviebooking.api.service;
//
//import com.akash.moviebooking.api.dto.MovieRequest;
//import com.akash.moviebooking.api.dto.MovieResponse;
//
//import java.util.Set;
//
//public interface MovieService {
//
//    MovieResponse getMovieById(String movieId);
//
//    Set<MovieResponse> searchMovies(String search);
//
//    MovieResponse addMovie(MovieRequest request);
//
//    MovieResponse updateMovie(String movieId, MovieRequest request);
//
//    void deleteMovie(String movieId);
//}
public interface MovieService {

    MovieResponse addMovie(MovieRequest request, String ownerEmail);

    MovieResponse updateMovie(String movieId, MovieRequest request);

    void deleteMovie(String movieId);

    MovieResponse getMovieById(String movieId);

    Set<MovieResponse> searchMovies(String search);
}