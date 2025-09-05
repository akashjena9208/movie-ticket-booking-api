package com.akash.moviebooking.api.service;


import com.akash.moviebooking.api.dto.FeedbackRequest;
import com.akash.moviebooking.api.dto.FeedbackResponse;

public interface FeedbackService {
    FeedbackResponse createFeedback(String movieId, FeedbackRequest feedbackRequest, String email);
}
