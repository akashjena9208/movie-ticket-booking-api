//package com.akash.moviebooking.api.controller;
//
//import com.akash.moviebooking.api.dto.FeedbackRequest;
//import com.akash.moviebooking.api.dto.FeedbackResponse;
//import com.akash.moviebooking.api.entity.UserDetails;
//import com.akash.moviebooking.api.repository.UserRepository;
//import com.akash.moviebooking.api.service.FeedbackService;
//import com.akash.moviebooking.api.util.ApiResponse;
//import com.akash.moviebooking.api.util.RestResponseBuilder;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/movies/{movieId}/feedbacks")
//@RequiredArgsConstructor
//@Tag(name = "Feedback Management", description = "APIs for submitting and retrieving movie feedback")
//public class FeedbackController {
//
//    private final FeedbackService feedbackService;
//    private final RestResponseBuilder responseBuilder;
//    private final UserRepository userRepository;
//
//    // ================= ADD FEEDBACK =================
//    @PostMapping
//    @PreAuthorize("hasAuthority('USER')")
//    @SecurityRequirement(name = "bearerAuth")
//    @Operation(
//            summary = "Submit feedback",
//            description = "Allows an authenticated USER to submit rating and review for a movie."
//    )
//    public ResponseEntity<ApiResponse<FeedbackResponse>> addFeedback(
//            @PathVariable String movieId,
//            @Valid @RequestBody FeedbackRequest request,
//            HttpServletRequest httpRequest) {
//
//        String email = SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getName();
//
//        UserDetails user = userRepository.findByEmail(email)
//                .orElseThrow(() ->
//                        new RuntimeException("Authenticated user not found."));
//
//        FeedbackResponse response =
//                feedbackService.addFeedback(user.getUserId(), movieId, request);
//
//        return responseBuilder.success(
//                HttpStatus.CREATED,
//                "Feedback submitted successfully.",
//                response,
//                httpRequest
//        );
//    }
//
//    // ================= GET FEEDBACKS =================
//    @GetMapping
//    @Operation(
//            summary = "Get movie feedback",
//            description = "Fetch all feedback submitted for a specific movie."
//    )
//    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getMovieFeedbacks(
//            @PathVariable String movieId,
//            HttpServletRequest httpRequest) {
//
//        List<FeedbackResponse> feedbackList =
//                feedbackService.getMovieFeedbacks(movieId);
//
//        return responseBuilder.success(
//                HttpStatus.OK,
//                "Feedback retrieved successfully.",
//                feedbackList,
//                httpRequest
//        );
//    }
//}
package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.FeedbackRequest;
import com.akash.moviebooking.api.dto.FeedbackResponse;
import com.akash.moviebooking.api.entity.UserDetails;
import com.akash.moviebooking.api.exceptions.ResourceNotFoundException;
import com.akash.moviebooking.api.repository.UserRepository;
import com.akash.moviebooking.api.service.FeedbackService;
import com.akash.moviebooking.api.util.ApiResponse;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies/{movieId}/feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback Management", description = "APIs for submitting and retrieving movie feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final RestResponseBuilder responseBuilder;
    private final UserRepository userRepository;

    // ================= ADD FEEDBACK =================
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Submit feedback",
            description = "Allows an authenticated USER to submit rating and review for a movie."
    )
    public ResponseEntity<ApiResponse<FeedbackResponse>> addFeedback(
            @PathVariable String movieId,
            @Valid @RequestBody FeedbackRequest request,
            HttpServletRequest httpRequest) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        UserDetails user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Authenticated user not found."));

        FeedbackResponse response =
                feedbackService.addFeedback(user.getUserId(), movieId, request);

        return responseBuilder.success(
                HttpStatus.CREATED,
                "Feedback submitted successfully.",
                response,
                httpRequest
        );
    }

    // ================= GET FEEDBACKS =================
    @GetMapping
    @Operation(
            summary = "Get movie feedback",
            description = "Fetch all feedback submitted for a specific movie."
    )
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getMovieFeedbacks(
            @PathVariable String movieId,
            HttpServletRequest httpRequest) {

        List<FeedbackResponse> feedbackList =
                feedbackService.getMovieFeedbacks(movieId);

        return responseBuilder.success(
                HttpStatus.OK,
                "Feedback retrieved successfully.",
                feedbackList,
                httpRequest
        );
    }
}