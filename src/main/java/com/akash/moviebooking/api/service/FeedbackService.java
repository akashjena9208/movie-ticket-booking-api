package com.akash.moviebooking.api.service;

import com.akash.moviebooking.api.dto.FeedbackRequest;
import com.akash.moviebooking.api.dto.FeedbackResponse;

import java.util.List;

public interface FeedbackService {

    FeedbackResponse addFeedback(String userId, String movieId, FeedbackRequest request);

    List<FeedbackResponse> getMovieFeedbacks(String movieId);
}