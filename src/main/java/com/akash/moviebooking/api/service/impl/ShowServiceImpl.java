package com.akash.moviebooking.api.service.impl;

import com.akash.moviebooking.api.dto.MovieShowsRequest;
import com.akash.moviebooking.api.dto.ShowResponse;
import com.akash.moviebooking.api.dto.TheaterShowProjection;
import com.akash.moviebooking.api.entity.Movie;
import com.akash.moviebooking.api.entity.Screen;
import com.akash.moviebooking.api.entity.Show;
import com.akash.moviebooking.api.entity.Theater;
import com.akash.moviebooking.api.exceptions.*;
import com.akash.moviebooking.api.mapper.ShowMapper;
import com.akash.moviebooking.api.mapper.TheaterMapper;
import com.akash.moviebooking.api.repository.MovieRepository;
import com.akash.moviebooking.api.repository.ScreenRepository;
import com.akash.moviebooking.api.repository.ShowRepository;
import com.akash.moviebooking.api.repository.TheaterRepository;
import com.akash.moviebooking.api.service.ShowService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ShowServiceImpl implements ShowService {

    private final TheaterRepository theaterRepository;
    private final ScreenRepository screenRepository;
    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final ShowMapper showMapper;
    private final TheaterMapper theaterMapper;

    @Override
    public ShowResponse addShow(String theaterId, String screenId, String movieId, Long startTime, @NotNull String zoneId) {
        //Adds a new movie show to a given screen in a theater, at a specified start time.

        if (theaterRepository.existsById(theaterId)) {

            if (screenRepository.existsById(screenId)) {

                if (movieRepository.existsById(movieId)) {

                    //Fetch screen & existing shows
                    Screen screen = screenRepository.findById(screenId).get();
                    Set<Show> shows = screen.getShows();

                    //Get the Movie entity (so we know its runtime)
                    Movie movie = movieRepository.findById(movieId).get();

                    Instant instantStartTime = Instant.ofEpochMilli(startTime);
                    //Each screen can have many shows scheduled in a day. Before adding a new show, we must check all existing shows on that screen to make sure the new show’s timing does not clash with any of them.
                    //We don’t want two shows to overlap on the same screen.If one movie is already playing from 2:00 PM to 4:00 PM, then we cannot add another show at 3:00 PM on the same screen.
                    for (Show s : shows) //all the already scheduled shows for that screen.
                    {
                        Instant showStartTime = s.getStartsAt();// existing show start
                        Instant showEndTime = s.getEndsAt();// existing show end
                        Instant movieCompletionTime = instantStartTime.plus(movie.getRuntime());
                        //instantStartTime → when the new show will begin (from user input).
                        //movie.getRuntime() → how long the movie runs (e.g., 2 hours).
                        //instantStartTime.plus(...) → adds runtime to start time.
                        //movieCompletionTime → the end time of the new show.
                        //👉 Example: instantStartTime = 2:00 Pm movie runtime = 2 hours movieCompletionTime = 4:00 PM
                        //So the new show will run from 2:00 PM → 4:00 PM.

                        //instantStartTime → when the new show will start. movieCompletionTime → when the new show will finish. showStartTime and showEndTime → timings of an existing show.
                        //movieCompletionTime.isBefore(showStartTime) → The new show finishes before the existing one starts ✅ (no conflict).
                        //instantStartTime.isAfter(showEndTime)→ The new show starts after the existing one ends ✅
                        //Existing show → 10:00 AM to 12:00 PM New show → 12:30 PM to 2:30 PM
                        //movieCompletionTime (2:30) before showStartTime (10:00) ❌ instantStartTime (12:30) after showEndTime (12:00) ✅
                        //So inside (...) = true → !(true) = false → ✅ no exception → show allowed.
//                        if (!(movieCompletionTime.isBefore(showStartTime) || instantStartTime.isAfter(showEndTime)))
//                        {
//                            throw new ShowTimeConflictException("Another Show is been Booked");
//                        }


                        if (!(movieCompletionTime.isBefore(showStartTime) || movieCompletionTime.equals(showStartTime)
                                || instantStartTime.isAfter(showEndTime) || instantStartTime.equals(showEndTime))) {
                            throw new ShowTimeConflictException("Another Show is been Booked");
                        }

                    }

                    Show show = copy(new Show(), startTime, screen, movie, zoneId);


                    return showMapper.showResponseMapper(show);

                }

                throw new MovieNotFoundByIdException("Movie Id not found in the database");
            }
            throw new ScreenNotFoundByIdException("Screen Id not found in the database");

        }
        throw new TheaterNotFoundByIdException("Theater Id not found in the database");
    }




    @Override
    public Page<TheaterShowProjection> fetchShows(String movieId, MovieShowsRequest showsRequest, String city) {

        //Zone (time zone handling) If user didn’t provide a time zone → default to UTC.Otherwise → use given zoneId (example: IST, PST)
        ZoneId zoneId = showsRequest.zoneId() == null || showsRequest.zoneId().isBlank()
                ? ZoneId.of("UTC")
                : ZoneId.of(ZoneId.SHORT_IDS.getOrDefault(showsRequest.zoneId().toUpperCase(), "UTC"));

        //If city is missing, → throw error.
        if(city == null || city.isBlank()){
            throw new CityNotFoundException("No city found by name");
        }

        //Calculate the start & end of the day
        //Example: Date = 2025-09-05  ||  start = 2025-09-05 00:00:00 ||  end = 2025-09-05 23:59:59 So we only fetch shows for that date
        Instant start = showsRequest.date().atStartOfDay(zoneId).toInstant();
        Instant end = showsRequest.date().plusDays(1).atStartOfDay(zoneId).minusNanos(1).toInstant();

        //Pagination (page number & size). Example: Page 1, size 10 → fetch first 10 theaters.
        Pageable pageable = PageRequest.of(showsRequest.page() - 1, showsRequest.size());

        //Step 1: Query DB → find theater IDs where this movie is playing in that city & date.
        // Fetch paged theater IDs || Result is paged (not all theaters at once).
        Page<String> theaterIdsPage = showRepository.findTheaterIdsWithMatchingShowsAndCity(
                movieId, start, end, showsRequest.screenType(), city,  pageable
        );

        List<String> theaterIds = theaterIdsPage.getContent();

        // Step 2: Fetch shows for those theaters || For those theater IDs → fetch the actual shows (with start/end time, screen, etc.).
        List<Show> shows = showRepository.findShowsForTheaters(
                movieId, start, end, showsRequest.screenType(), theaterIds
        );

        // Step 3: Group shows by theater
        //Group all shows under each theater ID. PVR → [Show1, Show2, Show3] INOX → [Show4, Show5]
        Map<String, List<Show>> grouped = shows.stream()
                .collect(Collectors.groupingBy(show -> show.getTheater().getTheaterId()));

        // Step 4: Map to projections
        //Get theater details (id, name, address).
        //Map all its shows into ShowResponse objects.
        //Build a TheaterShowProjection (theater + list of shows).
        List<TheaterShowProjection> results = theaterIds.stream()
                .map(theaterId -> {
                    Show show = grouped.get(theaterId).get(0); // get any show to extract theater
                    Theater theater = show.getTheater();

                    List<ShowResponse> showProjections = grouped.get(theaterId).stream()
                            .map(s -> new ShowResponse(
                                    s.getShowId(),
                                    s.getStartsAt(),
                                    s.getEndsAt(),
                                    s.getScreen().getScreenId(),
                                    s.getScreen().getScreenType()
                            ))
                            .toList();

                    return new TheaterShowProjection(
                            theater.getTheaterId(),
                            theater.getName(),
                            theater.getAddress(),
                            showProjections
                    );
                })
                .toList();

        return new PageImpl<>(results, pageable, theaterIdsPage.getTotalElements());
    }
   /*                     [
                        {
                            "theaterId": "T1",
                                "name": "PVR Koramangala",
                                "address": "Koramangala, Bangalore",
                                "shows": [
                            { "showId": "S1", "start": "2025-09-05T10:00", "end": "12:30", "screenId": "SCR1", "screenType": "IMAX" },
                            { "showId": "S2", "start": "2025-09-05T13:00", "end": "15:30", "screenId": "SCR2", "screenType": "2D" }
                        ]
                        },
                        {
                            "theaterId": "T2",
                                "name": "INOX Garuda",
                                "address": "MG Road, Bangalore",
                                "shows": [
                            { "showId": "S3", "start": "2025-09-05T11:00", "end": "13:30", "screenId": "SCR5", "screenType": "3D" }
                        ]
                        }
                    ]*/


    //This method takes the details of a new show, calculates start & end time, attaches it to the right screen & theater, and saves it.
    private Show copy(Show show, Long startTime, Screen screen, Movie movie, String zoneId) {
        show.setScreen(screen);
        show.setMovie(movie);
        Instant instantStartTime = Instant.ofEpochMilli(startTime);
        log.info(instantStartTime.toString());
        show.setStartsAt(instantStartTime);
        Instant endTime = instantStartTime.plus(movie.getRuntime());
        show.setEndsAt(endTime);
        show.setTheater(screen.getTheater());
        showRepository.save(show);
        return show;
    }
//    🎬 Real-Life Example
//    You call the method with:
//    Screen = "Screen 1"
//    Movie = "Inception" (runtime 2h 28m)
//    StartTime = 7:00 PM
//    The method will create a Show:
//    Screen = Screen 1
//    Movie = Inception
//    Start Time = 7:00 PM
//    End Time = 9:28 PM
//    Theater = PVR
//    And then save it in the database.
}