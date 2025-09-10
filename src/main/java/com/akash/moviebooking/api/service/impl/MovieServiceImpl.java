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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;


    @Override
    @CachePut(value = "movies", key = "#result.title") // ✅ after adding, put in cache
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
    @CachePut(value = "movies", key = "#movieId") // ✅ update cache when movie updates
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
    @Cacheable(value = "movies", key = "#movieId") // ✅ fetch from cache if available
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
    // ⚡ optional: cache search results also (expire quickly)
    @Cacheable(value = "movieSearch", key = "#search", unless = "#result.isEmpty()")
    public Set<MovieResponse> searchMovies(String search) {
        if (search == null || search.isBlank()) {
            return Set.of();
        }

        List<Movie> fetchedMovies = movieRepository.findByTitleContainingIgnoreCase(search);

        return movieMapper.movieResponseMapper(fetchedMovies);
    }

    // ✅ You may also add cache eviction if movies are deleted in future
    @CacheEvict(value = "movies", key = "#movieId")
    public void deleteMovie(String movieId) {
        movieRepository.deleteById(movieId);
    }
}
