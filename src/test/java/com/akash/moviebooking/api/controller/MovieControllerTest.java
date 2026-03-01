//package com.akash.moviebooking.api.controller;//package com.akash.moviebooking.api.controller;
////
////import com.akash.moviebooking.api.dto.MovieRequest;
////import com.akash.moviebooking.api.dto.MovieResponse;
////import com.akash.moviebooking.api.enums.Certificate;
////import com.akash.moviebooking.api.enums.Genre;
////import com.akash.moviebooking.api.service.MovieService;
////import com.akash.moviebooking.api.util.RestResponseBuilder;
////import com.fasterxml.jackson.databind.ObjectMapper;
////import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////import org.mockito.InjectMocks;
////import org.mockito.Mock;
////import org.mockito.MockitoAnnotations;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.MediaType;
////import org.springframework.http.ResponseEntity;
////import org.springframework.mock.web.MockHttpServletRequest;
////import org.springframework.test.web.servlet.MockMvc;
////import org.springframework.test.web.servlet.setup.MockMvcBuilders;
////
////import java.time.Duration;
////import java.util.Set;
////
////import static org.mockito.ArgumentMatchers.*;
////import static org.mockito.Mockito.*;
////import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
////import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
////
////class MovieControllerTest {
////
////    private MockMvc mockMvc;
////
////    @Mock
////    private MovieService movieService;
////
////    @Mock
////    private RestResponseBuilder responseBuilder;
////
////    @InjectMocks
////    private MovieController movieController;
////
////    private ObjectMapper objectMapper;
////
////    @BeforeEach
////    void setup() {
////
////        MockitoAnnotations.openMocks(this);
////
////        // ✅ FIX: Register JavaTimeModule to support Duration
////        objectMapper = new ObjectMapper()
////                .registerModule(new JavaTimeModule());
////
////        mockMvc = MockMvcBuilders
////                .standaloneSetup(movieController)
////                .build();
////    }
////
////    @Test
////    void createMovie_shouldReturn201() throws Exception {
////
////        // Arrange
////        MovieRequest request = new MovieRequest(
////                "Avengers",
////                "Superhero action movie",
////                Set.of("Robert Downey Jr", "Chris Evans"),
////                Duration.ofHours(2).plusMinutes(30),
////                Certificate.UA,
////                Genre.ACTION
////        );
////
////        MovieResponse response = MovieResponse.builder()
////                .movieId("123")
////                .title("Avengers")
////                .description("Superhero action movie")
////                .ratings("0.00")
////                .runtime(Duration.ofHours(2).plusMinutes(30))
////                .certificate(Certificate.UA)
////                .genre(Genre.ACTION)
////                .castList(Set.of("Robert Downey Jr", "Chris Evans"))
////                .build();
////
////        when(movieService.addMovie(any())).thenReturn(response);
////
////        when(responseBuilder.success(
////                eq(HttpStatus.CREATED),
////                anyString(),
////                eq(response),
////                any(MockHttpServletRequest.class)
////        )).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
////
////        // Act & Assert
////        mockMvc.perform(post("/movies")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(request)))
////                .andExpect(status().isCreated());
////
////        verify(movieService, times(1)).addMovie(any());
////    }
////}
//
//import com.akash.moviebooking.api.dto.MovieRequest;
//import com.akash.moviebooking.api.dto.MovieResponse;
//import com.akash.moviebooking.api.entity.Movie;
//import com.akash.moviebooking.api.enums.Certificate;
//import com.akash.moviebooking.api.enums.Genre;
//import com.akash.moviebooking.api.exceptions.MovieNotFoundByIdException;
//import com.akash.moviebooking.api.mapper.MovieMapper;
//import com.akash.moviebooking.api.repository.MovieRepository;
//import com.akash.moviebooking.api.service.impl.MovieServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.Duration;
//import java.util.Optional;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class MovieServiceTest {
//
//    @Mock
//    private MovieRepository movieRepository;
//
//    @Mock
//    private MovieMapper mapper;
//
//    @InjectMocks
//    private MovieServiceImpl movieService;
//
//    // ✅ ADD MOVIE TEST
//    @Test
//    void addMovie_shouldSaveAndReturnDto() {
//
//        MovieRequest request = new MovieRequest(
//                "Avengers",
//                "Superhero action movie",
//                Set.of("Robert Downey Jr", "Chris Evans"),
//                Duration.ofHours(2),
//                Certificate.UA,
//                Genre.ACTION
//        );
//
//        Movie movie = new Movie();
//        movie.setTitle("Avengers");
//
//        Movie savedMovie = new Movie();
//        savedMovie.setTitle("Avengers");
//
//        MovieResponse response = MovieResponse.builder()
//                .movieId("123")
//                .title("Avengers")
//                .build();
//
//        when(movieRepository.save(any(Movie.class))).thenReturn(savedMovie);
//        when(mapper.toDto(savedMovie, 0.0)).thenReturn(response);
//
//        MovieResponse result = movieService.addMovie(request);
//
//        assertEquals("Avengers", result.title());
//        verify(movieRepository, times(1)).save(any(Movie.class));
//    }
//
//    // ✅ GET MOVIE TEST
//    @Test
//    void getMovieById_shouldReturnMovie_whenExists() {
//
//        Movie movie = new Movie();
//        movie.setTitle("Avengers");
//
//        MovieResponse response = MovieResponse.builder()
//                .movieId("123")
//                .title("Avengers")
//                .build();
//
//        when(movieRepository.findById("123"))
//                .thenReturn(Optional.of(movie));
//
//        when(mapper.toDto(movie, 0.0))
//                .thenReturn(response);
//
//        MovieResponse result = movieService.getMovieById("123");
//
//        assertEquals("Avengers", result.title());
//    }
//
//    // ❌ GET MOVIE NOT FOUND TEST
//    @Test
//    void getMovieById_shouldThrowException_whenNotFound() {
//
//        when(movieRepository.findById("999"))
//                .thenReturn(Optional.empty());
//
//        assertThrows(MovieNotFoundByIdException.class, () ->
//                movieService.getMovieById("999"));
//    }
//
//    // ✅ UPDATE MOVIE TEST
//    @Test
//    void updateMovie_shouldUpdateAndReturnDto() {
//
//        Movie existing = new Movie();
//        existing.setTitle("Old Title");
//
//        MovieRequest request = new MovieRequest(
//                "New Title",
//                "Updated description",
//                Set.of("Actor"),
//                Duration.ofHours(2),
//                Certificate.UA,
//                Genre.ACTION
//        );
//
//        MovieResponse response = MovieResponse.builder()
//                .movieId("123")
//                .title("New Title")
//                .build();
//
//        when(movieRepository.findById("123"))
//                .thenReturn(Optional.of(existing));
//
//        when(movieRepository.save(existing))
//                .thenReturn(existing);
//
//        when(mapper.toDto(existing, 0.0))
//                .thenReturn(response);
//
//        MovieResponse result =
//                movieService.updateMovie("123", request);
//
//        assertEquals("New Title", result.title());
//    }
//
//    // ✅ DELETE MOVIE TEST
//    @Test
//    void deleteMovie_shouldDelete_whenExists() {
//
//        when(movieRepository.existsById("123"))
//                .thenReturn(true);
//
//        movieService.deleteMovie("123");
//
//        verify(movieRepository, times(1))
//                .deleteById("123");
//    }
//
//    // ❌ DELETE MOVIE NOT FOUND
//    @Test
//    void deleteMovie_shouldThrowException_whenNotExists() {
//
//        when(movieRepository.existsById("999"))
//                .thenReturn(false);
//
//        assertThrows(MovieNotFoundByIdException.class, () ->
//                movieService.deleteMovie("999"));
//    }
//}
package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.MovieRequest;
import com.akash.moviebooking.api.dto.MovieResponse;
import com.akash.moviebooking.api.enums.Certificate;
import com.akash.moviebooking.api.enums.Genre;
import com.akash.moviebooking.api.service.MovieService;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Duration;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MovieControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MovieService movieService;

    @Mock
    private RestResponseBuilder responseBuilder;

    @InjectMocks
    private MovieController movieController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders
                .standaloneSetup(movieController)
                .build();
    }

    // ================= CREATE =================
    @Test
    void createMovie_shouldReturn201() throws Exception {

        String ownerEmail = "owner@gmail.com";

        MovieRequest request = MovieRequest.builder()
                .title("Interstellar")
                .description("Sci-fi movie")
                .runtime(Duration.ofMinutes(169))
                .certificate(Certificate.UA)
                .genre(Genre.ACTION)
                .castList(Set.of("Matthew McConaughey"))
                .build();

        MovieResponse response = MovieResponse.builder()
                .movieId("movie-1")
                .title("Interstellar")
                .description("Sci-fi movie")
                .runtime(Duration.ofMinutes(169))
                .certificate(Certificate.UA)
                .genre(Genre.ACTION)
                .castList(Set.of("Matthew McConaughey"))
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(ownerEmail, null);

        when(movieService.addMovie(any(MovieRequest.class), eq(ownerEmail)))
                .thenReturn(response);

        when(responseBuilder.success(
                eq(HttpStatus.CREATED),
                anyString(),
                eq(response),
                any(HttpServletRequest.class)
        )).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        mockMvc.perform(post("/movies")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(movieService, times(1))
                .addMovie(any(MovieRequest.class), eq(ownerEmail));
    }

    // ================= GET BY ID =================
    @Test
    void getMovie_shouldReturn200() throws Exception {

        MovieResponse response = MovieResponse.builder()
                .movieId("movie-1")
                .title("Interstellar")
                .description("Sci-fi movie")
                .runtime(Duration.ofMinutes(169))
                .certificate(Certificate.UA)
                .genre(Genre.ACTION)
                .castList(Set.of("Actor"))
                .build();

        when(movieService.getMovieById("movie-1"))
                .thenReturn(response);

        when(responseBuilder.success(
                eq(HttpStatus.OK),
                anyString(),
                eq(response),
                any(HttpServletRequest.class)
        )).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/movies/{id}", "movie-1"))
                .andExpect(status().isOk());

        verify(movieService, times(1))
                .getMovieById("movie-1");
    }

    // ================= DELETE =================
    @Test
    void deleteMovie_shouldReturn200() throws Exception {

        String ownerEmail = "owner@gmail.com";

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(ownerEmail, null);

        doNothing().when(movieService).deleteMovie("movie-1");

        when(responseBuilder.success(
                eq(HttpStatus.OK),
                anyString(),
                any(),
                any(HttpServletRequest.class)
        )).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/movies/{id}", "movie-1")
                        .principal(authentication))
                .andExpect(status().isOk());

        verify(movieService, times(1))
                .deleteMovie("movie-1");
    }
}