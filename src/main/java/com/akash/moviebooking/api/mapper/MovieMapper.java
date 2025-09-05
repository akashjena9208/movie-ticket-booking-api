package com.akash.moviebooking.api.mapper;


import com.akash.moviebooking.api.dto.MovieResponse;
import com.akash.moviebooking.api.entity.Movie;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    public MovieResponse movieResponseMapper(Movie movie, double avgRatings) {
        if (movie == null)
            return null;

        DecimalFormat df = new DecimalFormat("#.##");
        String formattedRatings = df.format(avgRatings);

        return MovieResponse.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .ratings(formattedRatings)
                .runtime(movie.getRuntime())
                .certificate(movie.getCertificate())
                .genre(movie.getGenre())
                .castList(movie.getCastList())
                .build();
    }

    public Set<MovieResponse> movieResponseMapper(Collection<Movie> movies) {
        if (movies == null)
            return null;

        return movies.stream()
                .map(movie -> MovieResponse.builder()
                        .movieId(movie.getMovieId())
                        .title(movie.getTitle())
                        .description(movie.getDescription())
                        .runtime(movie.getRuntime())
                        .certificate(movie.getCertificate())
                        .genre(movie.getGenre())
                        .castList(movie.getCastList())
                        .build())
                .collect(Collectors.toSet());
    }

}
