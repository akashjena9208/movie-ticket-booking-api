package com.akash.moviebooking.api.service.impl;

import com.akash.moviebooking.api.dto.FeedbackRequest;
import com.akash.moviebooking.api.dto.FeedbackResponse;
import com.akash.moviebooking.api.entity.Feedback;
import com.akash.moviebooking.api.entity.User;
import com.akash.moviebooking.api.exceptions.MovieNotFoundByIdException;
import com.akash.moviebooking.api.mapper.FeedbackMapper;
import com.akash.moviebooking.api.repository.FeedbackRepository;
import com.akash.moviebooking.api.repository.MovieRepository;
import com.akash.moviebooking.api.repository.UserRepository;
import com.akash.moviebooking.api.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    @Override
    public FeedbackResponse createFeedback(String movieId, FeedbackRequest feedbackRequest, String email) {
        if(movieRepository.existsById(movieId)){
            Feedback feedback = copy(feedbackRequest, new Feedback(), movieId, email);

            return feedbackMapper.feedbackResponseMapper(feedback);
        }
        throw new MovieNotFoundByIdException("No movie found in database");
    }

    private Feedback copy(FeedbackRequest feedbackRequest, Feedback feedback, String movieId, String email) {
        feedback.setRating(feedbackRequest.rating());
        feedback.setReview(feedbackRequest.review());
        feedback.setMovie(movieRepository.findById(movieId).get());
        feedback.setUser((User) userRepository.findByEmail(email));
        feedbackRepository.save(feedback);
        return feedback;
    }
}
