package com.akash.moviebooking.api.mapper;

import com.akash.moviebooking.api.dto.MovieResponse;
import com.akash.moviebooking.api.entity.Movie;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    public MovieResponse toDto(Movie movie) {
        return MovieResponse.builder().movieId(movie.getMovieId()).title(movie.getTitle()).description(movie.getDescription()).runtime(movie.getRuntime()).certificate(movie.getCertificate()).genre(movie.getGenre()).castList(movie.getCastList()).build();
    }

    public Set<MovieResponse> toDto(List<Movie> movies) {
        return movies.stream().map(this::toDto).collect(Collectors.toSet());
    }
}