package com.akash.moviebooking.api.service;

import com.akash.moviebooking.api.dto.ScreenRequest;
import com.akash.moviebooking.api.dto.ScreenResponse;

public interface ScreenService {

    ScreenResponse addScreen(ScreenRequest screenRequest, String theaterId);

    ScreenResponse findScreen(String theaterId, String screenId);

}