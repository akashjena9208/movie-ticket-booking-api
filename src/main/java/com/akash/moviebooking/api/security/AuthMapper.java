//package com.akash.moviebooking.api.security;
//
//import com.akash.moviebooking.api.entity.UserDetails;
//import com.akash.moviebooking.api.security.dto.AuthResponse;
//import com.akash.moviebooking.api.security.dto.TokenPayLoad;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthMapper {
//
//
//    public AuthResponse authResponseMapper(UserDetails userDetails, TokenPayLoad access, TokenPayLoad refresh, String accessToken, String refreshToken) {
//
//        return AuthResponse.builder()
//                .userId(userDetails.getUserId())
//                .username(userDetails.getUsername())
//                .email(userDetails.getEmail())
//                .role(userDetails.getUserRole().toString())
//                .accessExpiration(access.expiration().toEpochMilli())
//                .refreshExpiration(refresh.expiration().toEpochMilli())
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .refreshToken(refreshToken)
//                .build();
//
//    }
//}