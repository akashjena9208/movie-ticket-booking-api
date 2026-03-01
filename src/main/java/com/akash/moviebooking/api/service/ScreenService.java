package com.akash.moviebooking.api.service;

import com.akash.moviebooking.api.dto.ScreenRequest;
import com.akash.moviebooking.api.dto.ScreenResponse;

public interface ScreenService {

    ScreenResponse addScreen(ScreenRequest request, String theaterId);

    ScreenResponse getScreen(String theaterId, String screenId);
}