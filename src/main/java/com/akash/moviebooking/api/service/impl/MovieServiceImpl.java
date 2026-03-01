//package com.akash.moviebooking.api.service.impl;
//import com.akash.moviebooking.api.dto.MovieRequest;
//import com.akash.moviebooking.api.dto.MovieResponse;
//import com.akash.moviebooking.api.entity.Movie;
//import com.akash.moviebooking.api.exceptions.MovieNotFoundByIdException;
//import com.akash.moviebooking.api.mapper.MovieMapper;
//import com.akash.moviebooking.api.repository.MovieRepository;
//import com.akash.moviebooking.api.service.MovieService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Set;
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class MovieServiceImpl implements MovieService {
//
//    private final MovieRepository movieRepository;
//    private final MovieMapper mapper;
//
//
//    @Override
//    public MovieResponse getMovieById(String movieId) {
//
//        Movie movie = movieRepository.findById(movieId)
//                .orElseThrow(() ->
//                        new MovieNotFoundByIdException("Movie not found with the given ID."));
//
//        return mapper.toDto(movie, 0.0);
//    }
//
//    @Override
//    public Set<MovieResponse> searchMovies(String search) {
//
//        return mapper.toDto(movieRepository.findByTitleContainingIgnoreCase(search));
//    }
//
//    @Override
//    public MovieResponse addMovie(MovieRequest request) {
//
//        Movie movie = new Movie();
//        movie.setTitle(request.title());
//        movie.setDescription(request.description());
//        movie.setCastList(request.castList());
//        movie.setRuntime(request.runtime());
//        movie.setCertificate(request.certificate());
//        movie.setGenre(request.genre());
//
//        return mapper.toDto(movieRepository.save(movie), 0.0);
//    }
//
//    @Override
//    public MovieResponse updateMovie(String movieId, MovieRequest request) {
//
//        Movie movie = movieRepository.findById(movieId)
//                .orElseThrow(() ->
//                        new MovieNotFoundByIdException("Movie not found with the given ID."));
//
//        movie.setTitle(request.title());
//        movie.setDescription(request.description());
//        movie.setCastList(request.castList());
//        movie.setRuntime(request.runtime());
//        movie.setCertificate(request.certificate());
//        movie.setGenre(request.genre());
//
//        return mapper.toDto(movieRepository.save(movie), 0.0);
//    }
//
//    @Override
//    public void deleteMovie(String movieId) {
//
//        if (!movieRepository.existsById(movieId)) {
//            throw new MovieNotFoundByIdException("Movie not found with the given ID.");
//        }
//
//        movieRepository.deleteById(movieId);
//    }
//}
package com.akash.moviebooking.api.service.impl;
import com.akash.moviebooking.api.dto.MovieRequest;
import com.akash.moviebooking.api.dto.MovieResponse;
import com.akash.moviebooking.api.entity.Movie;
import com.akash.moviebooking.api.entity.UserDetails;
import com.akash.moviebooking.api.exceptions.MovieNotFoundByIdException;
import com.akash.moviebooking.api.exceptions.UserNotFoundByEmailException;
import com.akash.moviebooking.api.mapper.MovieMapper;
import com.akash.moviebooking.api.repository.MovieRepository;
import com.akash.moviebooking.api.repository.UserRepository;
import com.akash.moviebooking.api.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
@Service
@RequiredArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final MovieMapper mapper;

    @Override
    public MovieResponse addMovie(MovieRequest request, String ownerEmail) {

        UserDetails owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() ->
                        new UserNotFoundByEmailException("Owner not found"));

        Movie movie = new Movie();
        movie.setTitle(request.title());
        movie.setDescription(request.description());
        movie.setCastList(request.castList());
        movie.setRuntime(request.runtime());
        movie.setCertificate(request.certificate());
        movie.setGenre(request.genre());
        movie.setOwner(owner);

        return mapper.toDto(movieRepository.save(movie));
    }

    @Override
    public MovieResponse updateMovie(String movieId, MovieRequest request) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new MovieNotFoundByIdException("Movie not found"));

        movie.setTitle(request.title());
        movie.setDescription(request.description());
        movie.setCastList(request.castList());
        movie.setRuntime(request.runtime());
        movie.setCertificate(request.certificate());
        movie.setGenre(request.genre());

        return mapper.toDto(movieRepository.save(movie));
    }

    @Override
    public void deleteMovie(String movieId) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new MovieNotFoundByIdException("Movie not found"));

        movieRepository.delete(movie);
    }

    @Override
    @Transactional(readOnly = true)
    public MovieResponse getMovieById(String movieId) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new MovieNotFoundByIdException("Movie not found"));

        return mapper.toDto(movie);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<MovieResponse> searchMovies(String search) {

        if (search == null || search.isBlank()) {
            return Set.of();
        }

        return mapper.toDto(
                movieRepository.findByTitleContainingIgnoreCase(search)
        );
    }
}