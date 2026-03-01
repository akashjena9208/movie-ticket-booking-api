package com.akash.moviebooking.api.security;
import com.akash.moviebooking.api.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("movieSecurity")
@RequiredArgsConstructor
public class MovieSecurity {

    private final MovieRepository movieRepository;

    public boolean isOwner(String movieId, Authentication authentication) {

        return movieRepository.existsByMovieIdAndOwner_Email(movieId, authentication.getName());
    }
}