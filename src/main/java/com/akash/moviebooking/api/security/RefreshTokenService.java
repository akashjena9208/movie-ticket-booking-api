//
//package com.akash.moviebooking.api.security;
//
//import com.akash.moviebooking.api.entity.UserDetails;
//import com.akash.moviebooking.api.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class RefreshTokenService {
//
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    /**
//     * Create new refresh token if not exists.
//     * Update existing refresh token if already present.
//     */
//    public RefreshToken createOrReplaceToken(String email,
//                                             String rawToken,
//                                             long durationMillis) {
//
//        UserDetails user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        RefreshToken token = refreshTokenRepository
//                .findByUser(user)
//                .orElse(new RefreshToken());
//
//        token.setUser(user);
//        token.setTokenHash(passwordEncoder.encode(rawToken));
//        token.setExpiryDate(
//                Instant.now().plusMillis(durationMillis)
//        );
//
//        return refreshTokenRepository.save(token);
//    }
//
//    /**
//     * Validate refresh token (hashed comparison + expiry check)
//     */
//    public RefreshToken validateToken(String rawToken) {
//
//        if (rawToken == null) {
//            throw new RuntimeException("Refresh token missing");
//        }
//
//        return refreshTokenRepository.findAll()
//                .stream()
//                .filter(token ->
//                        passwordEncoder.matches(
//                                rawToken,
//                                token.getTokenHash()
//                        )
//                )
//                .filter(token ->
//                        token.getExpiryDate()
//                                .isAfter(Instant.now()))
//                .findFirst()
//                .orElseThrow(() ->
//                        new RuntimeException("Invalid or expired refresh token"));
//    }
//}
package com.akash.moviebooking.api.security;

import com.akash.moviebooking.api.entity.UserDetails;
import com.akash.moviebooking.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Create or update refresh token (1 per user)
     */
    public RefreshToken createOrReplaceToken(String email,
                                             String rawToken,
                                             long durationMillis) {

        UserDetails user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        RefreshToken token = refreshTokenRepository
                .findByUser(user)
                .orElse(new RefreshToken());

        token.setUser(user);
        token.setTokenHash(passwordEncoder.encode(rawToken));
        token.setExpiryDate(
                Instant.now().plusMillis(durationMillis)
        );

        return refreshTokenRepository.save(token);
    }

    /**
     * Validate refresh token
     */
    public RefreshToken validateToken(String rawToken) {

        if (rawToken == null) {
            throw new RuntimeException("Refresh token missing");
        }

        return refreshTokenRepository.findAll()
                .stream()
                .filter(token ->
                        passwordEncoder.matches(
                                rawToken,
                                token.getTokenHash()
                        )
                )
                .filter(token ->
                        token.getExpiryDate()
                                .isAfter(Instant.now()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid or expired refresh token"
                        ));
    }

    /**
     * 🔥 DELETE refresh token on logout
     */
    public void deleteByUser(UserDetails user) {
        refreshTokenRepository.findByUser(user)
                .ifPresent(refreshTokenRepository::delete);
    }
}