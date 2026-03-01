package com.akash.moviebooking.api.security;

import com.akash.moviebooking.api.dto.ReactivationRequest;
import com.akash.moviebooking.api.entity.UserDetails;
import com.akash.moviebooking.api.exceptions.InvalidRefreshTokenException;
import com.akash.moviebooking.api.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final long REFRESH_TOKEN_VALIDITY = 1000L * 60 * 60 * 24 * 7; // 7 days

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(AuthRequest request, HttpServletResponse response) {

        UserDetails user = userRepository.findByEmail(request.email()).orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        //  Check soft delete BEFORE authentication
        if (user.isDelete()) {
            throw new IllegalStateException("Account is deactivated. Please reactivate your account.");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getUserRole().name());

        String refreshToken = jwtUtil.generateRefreshToken();

        refreshTokenService.createOrReplaceToken(user.getEmail(), refreshToken, REFRESH_TOKEN_VALIDITY);

        addRefreshTokenCookie(response, refreshToken);

        return AuthResponse.builder().accessToken(accessToken).userId(user.getUserId()).email(user.getEmail()).role(user.getUserRole().name()).build();
    }

    public AuthResponse refresh(HttpServletRequest request, HttpServletResponse response) {

        String oldToken = extractRefreshToken(request);

        if (oldToken == null) {
            throw new InvalidRefreshTokenException("Refresh token missing");
        }

        RefreshToken stored = refreshTokenService.validateToken(oldToken);

        UserDetails user = stored.getUser();

        String newAccessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getUserRole().name());

        String newRefreshToken = jwtUtil.generateRefreshToken();

        // Rotate refresh token (replace old)
        refreshTokenService.createOrReplaceToken(user.getEmail(), newRefreshToken, REFRESH_TOKEN_VALIDITY);

        addRefreshTokenCookie(response, newRefreshToken);

        return AuthResponse.builder().accessToken(newAccessToken).userId(user.getUserId()).email(user.getEmail()).role(user.getUserRole().name()).build();
    }


    public AuthResponse reactivateAccount(ReactivationRequest request, HttpServletResponse response) {

        UserDetails user = userRepository.findByEmail(request.email()).orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        // If already active
        if (!user.isDelete()) {
            throw new IllegalStateException("Account is already active.");
        }

        // Validate password manually
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        // 🔥 Reactivate account
        user.setDelete(false);
        user.setDeletedAt(null);
        userRepository.save(user);

        // 🔥 Generate new tokens
        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getUserRole().name());

        String refreshToken = jwtUtil.generateRefreshToken();

        refreshTokenService.createOrReplaceToken(user.getEmail(), refreshToken, REFRESH_TOKEN_VALIDITY);

        addRefreshTokenCookie(response, refreshToken);

        return AuthResponse.builder().accessToken(accessToken).userId(user.getUserId()).email(user.getEmail()).role(user.getUserRole().name()).build();
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {

        String token = extractRefreshToken(request);

        if (token != null) {

            RefreshToken stored = refreshTokenService.validateToken(token);

            // 🔥 PROPER delete (not expire hack)
            refreshTokenService.deleteByUser(stored.getUser());
        }

        clearRefreshTokenCookie(response);
    }


    private void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); //  set TRUE in production (HTTPS)
        cookie.setPath("/");
        cookie.setMaxAge((int) (REFRESH_TOKEN_VALIDITY / 1000));

        response.addCookie(cookie);
    }

    private void clearRefreshTokenCookie(HttpServletResponse response) {

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }

    private String extractRefreshToken(HttpServletRequest request) {

        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("refreshToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}