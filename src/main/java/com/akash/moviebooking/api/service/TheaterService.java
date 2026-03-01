package com.akash.moviebooking.api.service;

import com.akash.moviebooking.api.dto.TheaterRequest;
import com.akash.moviebooking.api.dto.TheaterResponse;

import java.util.List;

public interface TheaterService {

    TheaterResponse addTheater(String ownerEmail, TheaterRequest request);

    TheaterResponse getTheaterById(String theaterId);

    TheaterResponse updateTheater(String theaterId, TheaterRequest request);

    void deleteTheater(String theaterId);

    List<TheaterResponse> getMyTheaters(String ownerEmail);
}