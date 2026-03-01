package com.akash.moviebooking.api.repository;

import com.akash.moviebooking.api.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
}