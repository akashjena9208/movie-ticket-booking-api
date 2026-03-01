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


    public RefreshToken createOrReplaceToken(String email, String rawToken, long durationMillis) {

        UserDetails user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken token = refreshTokenRepository.findByUser(user).orElse(new RefreshToken());

        token.setUser(user);
        token.setTokenHash(passwordEncoder.encode(rawToken));
        token.setExpiryDate(Instant.now().plusMillis(durationMillis));

        return refreshTokenRepository.save(token);
    }


    public RefreshToken validateToken(String rawToken) {

        if (rawToken == null) {
            throw new RuntimeException("Refresh token missing");
        }

        return refreshTokenRepository.findAll().stream().filter(token -> passwordEncoder.matches(rawToken, token.getTokenHash())).filter(token -> token.getExpiryDate().isAfter(Instant.now())).findFirst().orElseThrow(() -> new RuntimeException("Invalid or expired refresh token"));
    }

    public void deleteByUser(UserDetails user) {
        refreshTokenRepository.findByUser(user).ifPresent(refreshTokenRepository::delete);
    }
}