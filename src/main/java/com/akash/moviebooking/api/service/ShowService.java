package com.akash.moviebooking.api.service;


import com.akash.moviebooking.api.dto.MovieShowsRequest;
import com.akash.moviebooking.api.dto.ShowResponse;
import com.akash.moviebooking.api.dto.TheaterShowProjection;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

public interface ShowService {


    ShowResponse addShow(String theaterId, String screenId, String movieId, @NotNull Long startTime, String zoneId);

    Page<TheaterShowProjection> fetchShows(String movieId, MovieShowsRequest showsRequest, String city);

}
