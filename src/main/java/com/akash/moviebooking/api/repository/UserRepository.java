package com.akash.moviebooking.api.repository;

import com.akash.moviebooking.api.entity.User;
import com.akash.moviebooking.api.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDetails, String> {
    boolean existsByEmail(String email);
    UserDetails findByEmail(String email);

}