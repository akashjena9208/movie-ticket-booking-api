//
//package com.akash.moviebooking.api.controller;
//
//import com.akash.moviebooking.api.dto.*;
//import com.akash.moviebooking.api.service.MovieService;
//import com.akash.moviebooking.api.util.ApiResponse;
//import com.akash.moviebooking.api.util.RestResponseBuilder;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Set;
//@RestController
//@RequestMapping("/movies")
//@RequiredArgsConstructor
//@Tag(name = "Movie Management")
//public class MovieController {
//
//    private final MovieService movieService;
//    private final RestResponseBuilder responseBuilder;
//
//    @PostMapping
//    @PreAuthorize("hasRole('THEATER_OWNER')")
//    @SecurityRequirement(name = "bearerAuth")
//    public ResponseEntity<ApiResponse<MovieResponse>> createMovie(
//            @Valid @RequestBody MovieRequest request,
//            Authentication authentication,
//            HttpServletRequest httpRequest) {
//
//        return responseBuilder.success(
//                HttpStatus.CREATED,
//                "Movie created successfully.",
//                movieService.addMovie(request, authentication.getName()),
//                httpRequest
//        );
//    }
//
//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('THEATER_OWNER') and @movieSecurity.isOwner(#id, authentication)")
//    @SecurityRequirement(name = "bearerAuth")
//    public ResponseEntity<ApiResponse<MovieResponse>> updateMovie(
//            @PathVariable String id,
//            @Valid @RequestBody MovieRequest request,
//            HttpServletRequest httpRequest) {
//
//        return responseBuilder.success(
//                HttpStatus.OK,
//                "Movie updated successfully.",
//                movieService.updateMovie(id, request),
//                httpRequest
//        );
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('THEATER_OWNER') and @movieSecurity.isOwner(#id, authentication)")
//    @SecurityRequirement(name = "bearerAuth")
//    public ResponseEntity<ApiResponse<String>> deleteMovie(
//            @PathVariable String id,
//            HttpServletRequest httpRequest) {
//
//        movieService.deleteMovie(id);
//
//        return responseBuilder.success(
//                HttpStatus.OK,
//                "Movie deleted successfully.",
//                "Deleted",
//                httpRequest
//        );
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse<MovieResponse>> getMovie(
//            @PathVariable String id,
//            HttpServletRequest httpRequest) {
//
//        return responseBuilder.success(
//                HttpStatus.OK,
//                "Movie retrieved successfully.",
//                movieService.getMovieById(id),
//                httpRequest
//        );
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<ApiResponse<Set<MovieResponse>>> searchMovies(
//            @RequestParam String search,
//            HttpServletRequest httpRequest) {
//
//        return responseBuilder.success(
//                HttpStatus.OK,
//                "Movies retrieved successfully.",
//                movieService.searchMovies(search),
//                httpRequest
//        );
//    }
//}
package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.*;
import com.akash.moviebooking.api.service.MovieService;
import com.akash.moviebooking.api.util.ApiResponse;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Tag(
        name = "Movie Management",
        description = """
                APIs for managing movies in the system.

                Access Control:
                • Only THEATER_OWNER can create/update/delete movies.
                • Public users can view and search movies.

                Ownership validation ensures that only the owner who created
                the movie can modify or delete it.
                """
)
public class MovieController {

    private final MovieService movieService;
    private final RestResponseBuilder responseBuilder;

    // ================= CREATE MOVIE =================

    @PostMapping
    @PreAuthorize("hasRole('THEATER_OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Create a new movie",
            description = """
                    Allows a THEATER_OWNER to add a new movie.

                    Required Role:
                    • THEATER_OWNER

                    The authenticated owner becomes the movie creator.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Movie created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<ApiResponse<MovieResponse>> createMovie(
            @Valid @RequestBody MovieRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.CREATED,
                "Movie created successfully.",
                movieService.addMovie(request, authentication.getName()),
                httpRequest
        );
    }

    // ================= UPDATE MOVIE =================

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('THEATER_OWNER') and @movieSecurity.isOwner(#id, authentication)")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Update movie details",
            description = """
                    Updates movie information.

                    Access Rules:
                    • Role: THEATER_OWNER
                    • Must be the owner of the movie

                    Ownership is validated using custom security component.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Movie updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Not the movie owner"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<ApiResponse<MovieResponse>> updateMovie(
            @Parameter(description = "Unique Movie ID")
            @PathVariable String id,
            @Valid @RequestBody MovieRequest request,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Movie updated successfully.",
                movieService.updateMovie(id, request),
                httpRequest
        );
    }

    // ================= DELETE MOVIE =================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('THEATER_OWNER') and @movieSecurity.isOwner(#id, authentication)")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Delete a movie",
            description = """
                    Deletes a movie from the system.

                    Access Rules:
                    • Role: THEATER_OWNER
                    • Must be the movie owner

                    Once deleted, the movie is no longer available for booking.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Movie deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Not the movie owner"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<ApiResponse<String>> deleteMovie(
            @Parameter(description = "Unique Movie ID")
            @PathVariable String id,
            HttpServletRequest httpRequest) {

        movieService.deleteMovie(id);

        return responseBuilder.success(
                HttpStatus.OK,
                "Movie deleted successfully.",
                "Deleted",
                httpRequest
        );
    }

    // ================= GET MOVIE =================

    @GetMapping("/{id}")
    @Operation(
            summary = "Get movie by ID",
            description = """
                    Retrieves detailed information about a specific movie.

                    Public endpoint.
                    No authentication required.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Movie retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<ApiResponse<MovieResponse>> getMovie(
            @Parameter(description = "Unique Movie ID")
            @PathVariable String id,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Movie retrieved successfully.",
                movieService.getMovieById(id),
                httpRequest
        );
    }

    // ================= SEARCH MOVIES =================

    @GetMapping("/search")
    @Operation(
            summary = "Search movies",
            description = """
                    Searches movies by title keyword.

                    Query Parameter:
                    • search → Partial or full movie title

                    Public endpoint.
                    """
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Movies retrieved successfully")
    })
    public ResponseEntity<ApiResponse<Set<MovieResponse>>> searchMovies(
            @Parameter(description = "Movie title search keyword")
            @RequestParam String search,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Movies retrieved successfully.",
                movieService.searchMovies(search),
                httpRequest
        );
    }
}