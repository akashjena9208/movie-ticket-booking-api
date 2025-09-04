package com.akash.moviebooking.api.service;


import com.akash.moviebooking.api.dto.TheaterRequest;
import com.akash.moviebooking.api.dto.TheaterResponse;

public interface TheaterService {

    TheaterResponse addTheater(String email, TheaterRequest theaterRequest);

    TheaterResponse findTheater(String theaterId);

    TheaterResponse updateTheater(String theaterId, TheaterRequest registerationRequest);
}