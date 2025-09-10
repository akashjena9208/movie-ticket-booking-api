//package com.akash.moviebooking.api.security;
//
//
//import com.akash.moviebooking.api.entity.UserDetails;
//import com.akash.moviebooking.api.repository.UserRepository;
//import com.akash.moviebooking.api.security.config.AppEnv;
//import com.akash.moviebooking.api.security.config.SecurityConfig;
//import com.akash.moviebooking.api.security.dto.*;
//import com.akash.moviebooking.api.security.service.AuthService;
//import com.akash.moviebooking.api.security.service.JwtService;
//import lombok.AllArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//@AllArgsConstructor
//public class AuthServiceImpl implements AuthService {
//
//    private final SecurityConfig securityConfig;
//    private final AuthenticationManager authenticationManager;
//    private final JwtService jwtService;
//    private final AuthMapper authMapper;
//    private final UserRepository userRepository;
//
//    private final AppEnv env;
//
//
//    @Override
//    public AuthResponse login(LoginRequest loginRequest) {
//
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
//
//        Authentication authentication = authenticationManager.authenticate(token);
//
//        if (! authentication.isAuthenticated())
//            throw new UsernameNotFoundException("Invalid Login Details");
//
//        UserDetails userDetails = userRepository.findByEmail(authentication.getName());
//
//        TokenPayLoad access = tokenGenerator(userDetails, env.getToken().getAccessDuration() , TokenType.ACCESS);
//        TokenPayLoad refresh = tokenGenerator(userDetails, env.getToken().getRefreshDuration(), TokenType.REFRESH );
//
//        String accessToken = jwtService.createJwtToken(access);
//        String refreshToken = jwtService.createJwtToken(refresh);
//
//        return authMapper.authResponseMapper(userDetails,access, refresh, accessToken, refreshToken);
//    }
//
//    @Override
//    public AuthResponse refresh(AuthenticatedTokenDetails tokenDetails) {
//
//        UserDetails userDetails = userRepository.findByEmail(tokenDetails.email());
//
//        TokenPayLoad access = tokenGenerator(userDetails, env.getToken().getAccessDuration(), TokenType.ACCESS);
//        String accessToken = jwtService.createJwtToken(access);
//
//        return new AuthResponse(
//                userDetails.getUserId(),
//                userDetails.getUsername(),
//                tokenDetails.email(),
//                tokenDetails.role(),
//                access.expiration().toEpochMilli(),
//                tokenDetails.tokenExpiration().toEpochMilli(),
//                accessToken,
//                tokenDetails.currentToken()
//        );
//    }
//
//    private TokenPayLoad tokenGenerator(UserDetails userDetails, int minutesForExpiration, TokenType tokenType){
//        Map<String, Object> claims = new HashMap<>();
//
//        String role = userDetails.getUserRole().toString();
//        claims.put("role", role);
//
//        return new TokenPayLoad(
//                claims,
//                userDetails.getEmail(),
//                Instant.now(),
//                Instant.now().plusSeconds(minutesForExpiration* 60L),
//                tokenType
//        );
//    }
//}
//
