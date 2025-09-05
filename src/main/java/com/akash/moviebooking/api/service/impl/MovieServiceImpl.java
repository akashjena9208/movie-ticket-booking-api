package com.akash.moviebooking.api.service.impl;

import com.akash.moviebooking.api.dto.MovieRequest;
import com.akash.moviebooking.api.dto.MovieResponse;
import com.akash.moviebooking.api.entity.Feedback;
import com.akash.moviebooking.api.entity.Movie;
import com.akash.moviebooking.api.exceptions.MovieNotFoundByIdException;
import com.akash.moviebooking.api.mapper.MovieMapper;
import com.akash.moviebooking.api.repository.MovieRepository;
import com.akash.moviebooking.api.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;


    @Override
    public MovieResponse addMovie(MovieRequest request) {
        Movie movie = new Movie();
        movie.setTitle(request.title());
        movie.setDescription(request.description());
        movie.setRuntime(request.runtime());
        movie.setCertificate(request.certificate());
        movie.setGenre(request.genre());
        movie.setCastList(request.castList());

        Movie saved = movieRepository.save(movie);
        return movieMapper.movieResponseMapper(saved, 0.0); // new movie has no ratings yet
    }

    @Override
    public MovieResponse updateMovie(String movieId, MovieRequest request) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundByIdException("Movie not found"));

        movie.setTitle(request.title());
        movie.setDescription(request.description());
        movie.setRuntime(request.runtime());
        movie.setCertificate(request.certificate());
        movie.setGenre(request.genre());
        movie.setCastList(request.castList());

        Movie updated = movieRepository.save(movie);

        double avgRatings = movie.getFeedbacks().stream()
                .mapToDouble(Feedback::getRating)
                .average()
                .orElse(0.0);

        return movieMapper.movieResponseMapper(updated, avgRatings);
    }


    @Override
    public MovieResponse getMovie(String movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundByIdException("Movie not found in Database"));

        List<Feedback> feedbacks = movie.getFeedbacks();

        double avgRatings = 0.0;
        if (!feedbacks.isEmpty()) {
            for (Feedback feedback : feedbacks) {
                avgRatings += feedback.getRating();
            }
            avgRatings /= feedbacks.size();
        }

        return movieMapper.movieResponseMapper(movie, avgRatings);
    }

    @Override
    public Set<MovieResponse> searchMovies(String search) {
        if (search == null || search.isBlank()) {
            return Set.of();
        }

        List<Movie> fetchedMovies = movieRepository.findByTitleContainingIgnoreCase(search);

        return movieMapper.movieResponseMapper(fetchedMovies);
    }
}
