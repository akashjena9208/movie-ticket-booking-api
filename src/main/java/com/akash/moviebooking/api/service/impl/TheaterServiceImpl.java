package com.akash.moviebooking.api.service.impl;

import com.akash.moviebooking.api.dto.TheaterRequest;
import com.akash.moviebooking.api.dto.TheaterResponse;
import com.akash.moviebooking.api.entity.Theater;
import com.akash.moviebooking.api.entity.TheaterOwner;
import com.akash.moviebooking.api.entity.UserDetails;
import com.akash.moviebooking.api.enums.UserRole;
import com.akash.moviebooking.api.exceptions.TheaterNotFoundByIdException;
import com.akash.moviebooking.api.exceptions.UserNotFoundByEmailException;
import com.akash.moviebooking.api.mapper.TheaterMapper;
import com.akash.moviebooking.api.repository.TheaterRepository;
import com.akash.moviebooking.api.repository.UserRepository;
import com.akash.moviebooking.api.service.TheaterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TheaterServiceImpl implements TheaterService
{
    private final TheaterRepository theaterRepository;
    private final TheaterMapper theaterMapper;
    private final UserRepository userRepository;

    @Override
    public TheaterResponse addTheater(String email, TheaterRequest theaterRequest) {

        if (userRepository.existsByEmail(email) && userRepository.findByEmail(email).getUserRole() == UserRole.THEATER_OWNER) {
            UserDetails user = userRepository.findByEmail(email);
            //DTO -> Entity happens here :--> Mapping TheaterRequest (DTO) to Theater (Entity)
            //DTO → Entity: Happens inside copy() method where you set fields from TheaterRequest to Theater
            Theater theater = copy(theaterRequest, new Theater(), user); //Map TheaterRequest fields to a new Theater entity and associate the owner
            // Entity -> DTO happens here :---- > Mapping Theater (Entity) to TheaterResponse (DTO) to send back as response
            return theaterMapper.theaterResponseMapper(theater);
        }
        throw new UserNotFoundByEmailException("No Theater Owner with the provided email is present");
    }




    private Theater copy(TheaterRequest registerationRequest, Theater theater, UserDetails userDetails) {
        // Map fields from request DTO to Theater entity
        // DTO -> Entity happens here (field by field) //theaterMapper.theaterResponseMapper(theater) to return a response.
        theater.setAddress(registerationRequest.address());
        theater.setCity(registerationRequest.city());
        theater.setName(registerationRequest.name());
        theater.setLandmark(registerationRequest.landmark());

        // Associate the TheaterOwner (casting UserDetails to TheaterOwner)
        theater.setTheaterOwner((TheaterOwner) userDetails);

        // Save the theater to DB (establishes foreign key relationship)
        theaterRepository.save(theater);

        // Return the saved entity
        return theater;
    }


    @Override
    public TheaterResponse findTheater(String theaterId) {
        if(theaterRepository.existsById(theaterId)){
            Theater theater = theaterRepository.findById(theaterId).get();
            return theaterMapper.theaterResponseMapper(theater);
        }
        throw new TheaterNotFoundByIdException("Theater not found by the id");
    }

    @Override
    public TheaterResponse updateTheater(String theaterId, TheaterRequest registerationRequest) {
        if(theaterRepository.existsById(theaterId)) {
            Theater theater = theaterRepository.findById(theaterId).get();
            theater = copy(registerationRequest, theater);
            return theaterMapper.theaterResponseMapper(theater);
        }
        throw new TheaterNotFoundByIdException("Theater not found by id");
    }

    private Theater copy(TheaterRequest registerationRequest, Theater theater) {
        theater.setAddress(registerationRequest.address());
        theater.setCity(registerationRequest.city());
        theater.setName(registerationRequest.name());
        theater.setLandmark(registerationRequest.landmark());
        theaterRepository.save(theater);
        return theater;
    }
}
