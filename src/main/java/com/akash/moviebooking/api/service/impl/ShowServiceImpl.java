package com.akash.moviebooking.api.service.impl;

import com.akash.moviebooking.api.dto.MovieShowsRequest;
import com.akash.moviebooking.api.dto.ShowResponse;
import com.akash.moviebooking.api.dto.TheaterShowProjection;
import com.akash.moviebooking.api.entity.Movie;
import com.akash.moviebooking.api.entity.Screen;
import com.akash.moviebooking.api.entity.Show;
import com.akash.moviebooking.api.entity.Theater;
import com.akash.moviebooking.api.exceptions.MovieNotFoundByIdException;
import com.akash.moviebooking.api.exceptions.ScreenNotFoundByIdException;
import com.akash.moviebooking.api.exceptions.ShowTimeConflictException;
import com.akash.moviebooking.api.exceptions.TheaterNotFoundByIdException;
import com.akash.moviebooking.api.mapper.ShowMapper;
import com.akash.moviebooking.api.repository.MovieRepository;
import com.akash.moviebooking.api.repository.ScreenRepository;
import com.akash.moviebooking.api.repository.ShowRepository;
import com.akash.moviebooking.api.repository.TheaterRepository;
import com.akash.moviebooking.api.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ShowServiceImpl implements ShowService {

    private final TheaterRepository theaterRepository;
    private final ScreenRepository screenRepository;
    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final ShowMapper mapper;

    @Override
    public ShowResponse addShow(String theaterId, String screenId, String movieId, Long startTime, String zoneId) {

        Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new TheaterNotFoundByIdException("Theater not found"));

        Screen screen = screenRepository.findById(screenId).orElseThrow(() -> new ScreenNotFoundByIdException("Screen not found"));

        if (!screen.getTheater().getTheaterId().equals(theaterId)) {
            throw new IllegalArgumentException("Screen does not belong to the given theater");
        }

        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundByIdException("Movie not found"));

        if (movie.getRuntime() == null) {
            throw new IllegalStateException("Movie runtime is not defined");
        }

        Instant start = Instant.ofEpochMilli(startTime);
        Instant end = start.plus(movie.getRuntime());

        // 🔥 Proper overlap check
        List<Show> conflicts = showRepository.findConflictingShows(screenId, start, end);

        if (!conflicts.isEmpty()) {
            throw new ShowTimeConflictException("Another show is already scheduled during this time slot.");
        }

        Show show = new Show();
        show.setTheater(theater);
        show.setScreen(screen);
        show.setMovie(movie);
        show.setStartsAt(start);
        show.setEndsAt(end);

        Show saved = showRepository.save(show);

        return mapper.toDto(saved);
    }

    @Override
    public Page<TheaterShowProjection> fetchShows(String movieId, MovieShowsRequest request, String city) {

        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City is required");
        }

        ZoneId zoneId = request.zoneId() == null || request.zoneId().isBlank() ? ZoneId.of("UTC") : ZoneId.of(ZoneId.SHORT_IDS.getOrDefault(request.zoneId(), "UTC"));

        Instant start = request.date().atStartOfDay(zoneId).toInstant();

        Instant end = request.date().plusDays(1).atStartOfDay(zoneId).minusNanos(1).toInstant();

        Pageable pageable = PageRequest.of(request.page() - 1, request.size());

        Page<String> theaterIdsPage = showRepository.findTheaterIds(movieId, start, end, request.screenType(), city, pageable);

        List<String> theaterIds = theaterIdsPage.getContent();

        if (theaterIds.isEmpty()) {
            return Page.empty(pageable);
        }

        List<Show> shows = showRepository.findShowsForTheaters(movieId, start, end, request.screenType(), theaterIds);

        Map<String, List<Show>> grouped = shows.stream().collect(Collectors.groupingBy(s -> s.getTheater().getTheaterId()));

        List<TheaterShowProjection> results = theaterIds.stream().map(theaterId -> {

            List<Show> theaterShows = grouped.get(theaterId);
            Theater theater = theaterShows.get(0).getTheater();

            List<ShowResponse> showResponses = theaterShows.stream().map(mapper::toDto).toList();

            return new TheaterShowProjection(theater.getTheaterId(), theater.getName(), theater.getAddress(), showResponses);
        }).toList();

        return new PageImpl<>(results, pageable, theaterIdsPage.getTotalElements());
    }
}