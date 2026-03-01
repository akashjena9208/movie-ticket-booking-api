//package com.akash.moviebooking.api.security;
//
//import com.akash.moviebooking.api.repository.TheaterRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//@Component("theaterSecurity")
//@RequiredArgsConstructor
//public class TheaterSecurity {
//
//    private final TheaterRepository theaterRepository;
//
//    public boolean isOwner(String theaterId, Authentication authentication) {
//
//        return theaterRepository.findById(theaterId)
//                .map(theater ->
//                        theater.getTheaterOwner()
//                                .getEmail()
//                                .equals(authentication.getName()))
//                .orElse(false);
//    }
//}
package com.akash.moviebooking.api.security;

import com.akash.moviebooking.api.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("theaterSecurity")
@RequiredArgsConstructor
public class TheaterSecurity {

    private final TheaterRepository theaterRepository;

    /**
     * Validates whether the authenticated user is the owner of the theater.
     *
     * @param theaterId the theater ID
     * @param authentication current authenticated user
     * @return true if owner matches, false otherwise
     */
    public boolean isOwner(String theaterId, Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String loggedInEmail = authentication.getName();

        return theaterRepository.findById(theaterId)
                .map(theater ->
                        theater.getTheaterOwner() != null &&
                                theater.getTheaterOwner().getEmail().equals(loggedInEmail)
                )
                .orElse(false);
    }
}