package com.akash.moviebooking.api.service.impl;

import com.akash.moviebooking.api.dto.TheaterRequest;
import com.akash.moviebooking.api.dto.TheaterResponse;
import com.akash.moviebooking.api.entity.Theater;
import com.akash.moviebooking.api.entity.TheaterOwner;
import com.akash.moviebooking.api.entity.UserDetails;
import com.akash.moviebooking.api.exceptions.TheaterNotFoundByIdException;
import com.akash.moviebooking.api.exceptions.UserNotFoundByEmailException;
import com.akash.moviebooking.api.mapper.TheaterMapper;
import com.akash.moviebooking.api.repository.TheaterRepository;
import com.akash.moviebooking.api.repository.UserRepository;
import com.akash.moviebooking.api.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TheaterServiceImpl implements TheaterService {

    private final TheaterRepository theaterRepository;
    private final UserRepository userRepository;
    private final TheaterMapper mapper;

    @Override
    public TheaterResponse addTheater(String ownerEmail, TheaterRequest request) {

        UserDetails user = userRepository.findByEmail(ownerEmail).orElseThrow(() -> new UserNotFoundByEmailException("Theater owner not found."));

        if (!(user instanceof TheaterOwner owner)) {
            throw new IllegalArgumentException("Only users with THEATER_OWNER role can create theaters.");
        }

        Theater theater = new Theater();
        theater.setName(request.name());
        theater.setAddress(request.address());
        theater.setCity(request.city());
        theater.setLandmark(request.landmark());
        theater.setTheaterOwner(owner);

        return mapper.toDto(theaterRepository.save(theater));
    }

    // ================= GET =================
    @Override
    @Transactional(readOnly = true)
    public TheaterResponse getTheaterById(String theaterId) {

        Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new TheaterNotFoundByIdException("Theater not found with the given ID."));

        return mapper.toDto(theater);
    }

    @Override
    public TheaterResponse updateTheater(String theaterId, TheaterRequest request) {

        Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new TheaterNotFoundByIdException("Theater not found with the given ID."));

        theater.setName(request.name());
        theater.setAddress(request.address());
        theater.setCity(request.city());
        theater.setLandmark(request.landmark());

        return mapper.toDto(theaterRepository.save(theater));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TheaterResponse> getMyTheaters(String ownerEmail) {

        UserDetails user = userRepository.findByEmail(ownerEmail).orElseThrow(() -> new UserNotFoundByEmailException("Theater owner not found."));

        if (!(user instanceof TheaterOwner)) {
            throw new IllegalArgumentException("Only THEATER_OWNER can access their theaters.");
        }

        return theaterRepository.findByTheaterOwner_Email(ownerEmail).stream().map(mapper::toDto).toList();
    }


    // ================= DELETE =================
    @Override
    public void deleteTheater(String theaterId) {

        Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new TheaterNotFoundByIdException("Theater not found with the given ID."));

        theaterRepository.delete(theater);
    }
}