package com.akash.moviebooking.api.mapper;

import com.akash.moviebooking.api.dto.FeedbackResponse;
import com.akash.moviebooking.api.entity.Feedback;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {


    public FeedbackResponse feedbackResponseMapper(Feedback feedback) {
        if (feedback == null)
            return null;
        return FeedbackResponse.builder()
                .feedbackId(feedback.getFeedbackId())
                .rating(feedback.getRating())
                .review(feedback.getReview())
                .build();
    }



}
