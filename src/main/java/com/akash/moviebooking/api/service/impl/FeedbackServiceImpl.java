////package com.akash.moviebooking.api.service.impl;
////
////import com.akash.moviebooking.api.dto.FeedbackRequest;
////import com.akash.moviebooking.api.dto.FeedbackResponse;
////import com.akash.moviebooking.api.entity.Feedback;
////import com.akash.moviebooking.api.entity.User;
////import com.akash.moviebooking.api.exceptions.MovieNotFoundByIdException;
////import com.akash.moviebooking.api.mapper.FeedbackMapper;
////import com.akash.moviebooking.api.repository.FeedbackRepository;
////import com.akash.moviebooking.api.repository.MovieRepository;
////import com.akash.moviebooking.api.repository.UserRepository;
////import com.akash.moviebooking.api.service.FeedbackService;
////import lombok.AllArgsConstructor;
////import org.springframework.stereotype.Service;
////
////@Service
////@AllArgsConstructor
////public class FeedbackServiceImpl implements FeedbackService {
////
////    private final MovieRepository movieRepository;
////    private final UserRepository userRepository;
////    private final FeedbackRepository feedbackRepository;
////    private final FeedbackMapper feedbackMapper;
////
////    @Override
////    public FeedbackResponse createFeedback(String movieId, FeedbackRequest feedbackRequest, String email) {
////        if(movieRepository.existsById(movieId)){
////            Feedback feedback = copy(feedbackRequest, new Feedback(), movieId, email);
////
////            return feedbackMapper.feedbackResponseMapper(feedback);
////        }
////        throw new MovieNotFoundByIdException("No movie found in database");
////    }
////
////    private Feedback copy(FeedbackRequest feedbackRequest, Feedback feedback, String movieId, String email) {
////        feedback.setRating(feedbackRequest.rating());
////        feedback.setReview(feedbackRequest.review());
////        feedback.setMovie(movieRepository.findById(movieId).get());
////        feedback.setUser((User) userRepository.findByEmail(email));
////        feedbackRepository.save(feedback);
////        return feedback;
////    }
////}
//package com.akash.moviebooking.api.service.impl;
//
//import com.akash.moviebooking.api.dto.FeedbackRequest;
//import com.akash.moviebooking.api.dto.FeedbackResponse;
//import com.akash.moviebooking.api.entity.Feedback;
//import com.akash.moviebooking.api.entity.Movie;
//import com.akash.moviebooking.api.entity.User;
//import com.akash.moviebooking.api.entity.UserDetails;
//import com.akash.moviebooking.api.exceptions.MovieNotFoundByIdException;
//import com.akash.moviebooking.api.exceptions.UserNotFoundByEmailException;
//import com.akash.moviebooking.api.mapper.FeedbackMapper;
//import com.akash.moviebooking.api.repository.FeedbackRepository;
//import com.akash.moviebooking.api.repository.MovieRepository;
//import com.akash.moviebooking.api.repository.UserRepository;
//import com.akash.moviebooking.api.service.FeedbackService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class FeedbackServiceImpl implements FeedbackService {
//
//    private final MovieRepository movieRepository;
//    private final UserRepository userRepository;
//    private final FeedbackRepository feedbackRepository;
//    private final FeedbackMapper feedbackMapper;
//
//    @Override
//    public FeedbackResponse createFeedback(String movieId,
//                                           FeedbackRequest request,
//                                           String email) {
//
//        Movie movie = movieRepository.findById(movieId)
//                .orElseThrow(() -> new MovieNotFoundByIdException("Movie not found"));
//
//        UserDetails user = userRepository.findByEmail(email);
//
//        if (user == null) {
//            throw new UserNotFoundByEmailException("User not found");
//        }
//
//        Feedback feedback = new Feedback();
//        feedback.setRating(request.rating());
//        feedback.setReview(request.review());
//        feedback.setMovie(movie);
//        feedback.setUser((User) user);
//
//        return feedbackMapper.feedbackResponseMapper(
//                feedbackRepository.save(feedback)
//        );
//    }
//}
package com.akash.moviebooking.api.service.impl;

import com.akash.moviebooking.api.dto.FeedbackRequest;
import com.akash.moviebooking.api.dto.FeedbackResponse;
import com.akash.moviebooking.api.entity.Feedback;
import com.akash.moviebooking.api.entity.Movie;
import com.akash.moviebooking.api.entity.User;
import com.akash.moviebooking.api.entity.UserDetails;
import com.akash.moviebooking.api.exceptions.MovieNotFoundByIdException;
import com.akash.moviebooking.api.exceptions.ResourceNotFoundException;
import com.akash.moviebooking.api.mapper.FeedbackMapper;
import com.akash.moviebooking.api.repository.FeedbackRepository;
import com.akash.moviebooking.api.repository.MovieRepository;
import com.akash.moviebooking.api.repository.UserRepository;
import com.akash.moviebooking.api.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final FeedbackMapper mapper;

    @Override
    public FeedbackResponse addFeedback(String userId,
                                        String movieId,
                                        FeedbackRequest request) {

        UserDetails userDetails = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        if (!(userDetails instanceof User user)) {
            throw new IllegalArgumentException("Only regular users can submit feedback.");
        }

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new MovieNotFoundByIdException("Movie not found with the given ID."));

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setMovie(movie);
        feedback.setRating(request.rating());
        feedback.setReview(request.review());

        return mapper.toDto(feedbackRepository.save(feedback));
    }

    @Override
    public List<FeedbackResponse> getMovieFeedbacks(String movieId) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new MovieNotFoundByIdException("Movie not found with the given ID."));

        return feedbackRepository.findAll()
                .stream()
                .filter(feedback -> feedback.getMovie().equals(movie))
                .map(mapper::toDto)
                .toList();
    }
}