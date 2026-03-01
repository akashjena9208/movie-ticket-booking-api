package com.akash.moviebooking.api.service;

import com.akash.moviebooking.api.dto.UserRegistrationRequest;
import com.akash.moviebooking.api.dto.UserResponse;
import com.akash.moviebooking.api.dto.UserUpdationRequest;

public interface UserService {

    UserResponse registerUser(UserRegistrationRequest request);

    UserResponse updateUser(UserUpdationRequest request, String email);

    UserResponse softDeleteUser(String email);
}