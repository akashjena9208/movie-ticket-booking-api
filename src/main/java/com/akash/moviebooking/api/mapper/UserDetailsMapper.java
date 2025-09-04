package com.akash.moviebooking.api.mapper;


import com.akash.moviebooking.api.dto.UserResponse;
import com.akash.moviebooking.api.entity.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {


    public UserResponse userDetailsResponseMapper(UserDetails userDetails){
        if(userDetails == null)
            return null;

        return UserResponse.builder()
                .userId(userDetails.getUserId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .phoneNumber(userDetails.getPhoneNumber())
                .userRole(userDetails.getUserRole())
                .build();
    }

}
