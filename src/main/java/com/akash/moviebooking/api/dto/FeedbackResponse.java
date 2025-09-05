package com.akash.moviebooking.api.dto;

import lombok.Builder;

@Builder
public record FeedbackResponse(
        String feedbackId,
        int rating,
        String review
) {}
