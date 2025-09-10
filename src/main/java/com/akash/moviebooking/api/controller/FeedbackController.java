//package com.akash.moviebooking.api.controller;
//import com.akash.moviebooking.api.dto.FeedbackRequest;
//import com.akash.moviebooking.api.dto.FeedbackResponse;
//import com.akash.moviebooking.api.service.FeedbackService;
//import com.akash.moviebooking.api.util.ResponseStructure;
//import com.akash.moviebooking.api.util.RestResponseBuilder;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
////import org.springframework.security.access.prepost.PreAuthorize;
////import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@AllArgsConstructor
//@Tag(name = "Feedback Controller", description = "APIs for managing movie feedbacks")
//public class FeedbackController {
//
//    private final RestResponseBuilder responseBuilder;
//    private final FeedbackService feedbackService;
//
//    @PostMapping("/movies/{movieId}/feedbacks")
//    //@PreAuthorize("hasAuthority('USER')")
//    @Operation(
//            summary = "Create Feedback for a Movie",
//            description = "Allows a USER to submit feedback for a specific movie by providing its ID"
//    )
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Feedback successfully created"),
//            @ApiResponse(responseCode = "400", description = "Invalid feedback request"),
//            @ApiResponse(responseCode = "401", description = "Unauthorized - user not logged in"),
//            @ApiResponse(responseCode = "404", description = "Movie not found")
//    })
//    public ResponseEntity<ResponseStructure<FeedbackResponse>> createFeedback(
//            @PathVariable String movieId,
//            @RequestBody @Valid FeedbackRequest feedbackRequest) {
//
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        FeedbackResponse feedbackResponse = feedbackService.createFeedback(movieId, feedbackRequest, email);
//
//        return responseBuilder.sucess(HttpStatus.OK, "Feedback successfully created", feedbackResponse);
//    }
//}
