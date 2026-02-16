//////////package com.akash.moviebooking.api.security;
//////////
//////////import com.akash.moviebooking.api.entity.UserDetails;
//////////import com.akash.moviebooking.api.repository.UserRepository;
//////////import jakarta.servlet.http.Cookie;
//////////import jakarta.servlet.http.HttpServletRequest;
//////////import jakarta.servlet.http.HttpServletResponse;
//////////import lombok.RequiredArgsConstructor;
//////////import org.springframework.security.authentication.AuthenticationManager;
//////////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//////////import org.springframework.stereotype.Service;
//////////
//////////@Service
//////////@RequiredArgsConstructor
//////////public class AuthService {
//////////
//////////    private final AuthenticationManager authenticationManager;
//////////    private final JwtUtil jwtUtil;
//////////    private final UserRepository userRepository;
//////////    private final RefreshTokenService refreshTokenService;
//////////
//////////    // ====================================================
//////////    // LOGIN
//////////    // ====================================================
//////////    public AuthResponse login(AuthRequest request,
//////////                              HttpServletResponse response) {
//////////
//////////        authenticationManager.authenticate(
//////////                new UsernamePasswordAuthenticationToken(
//////////                        request.email(),
//////////                        request.password()
//////////                )
//////////        );
//////////
//////////        UserDetails user = userRepository.findByEmail(request.email());
//////////
//////////        if (user == null) {
//////////            throw new RuntimeException("User not found");
//////////        }
//////////
//////////        String accessToken = jwtUtil.generateAccessToken(
//////////                user.getEmail(),
//////////                user.getUserRole().name()
//////////        );
//////////
//////////        // 🔥 DELETE old refresh tokens (clean session)
//////////        refreshTokenService.deleteByEmail(user.getEmail());
//////////
//////////        String refreshToken = jwtUtil.generateRefreshToken();
//////////
//////////        refreshTokenService.createToken(
//////////                user.getEmail(),
//////////                refreshToken,
//////////                1000L * 60 * 60 * 24 * 7
//////////        );
//////////
//////////        Cookie cookie = new Cookie("refreshToken", refreshToken);
//////////        cookie.setHttpOnly(true);
//////////        cookie.setSecure(false); // 🔥 set true in production HTTPS
//////////        cookie.setPath("/api/v1/auth/refresh");
//////////        cookie.setMaxAge(60 * 60 * 24 * 7);
//////////
//////////        response.addCookie(cookie);
//////////
//////////        return new AuthResponse(accessToken);
//////////    }
//////////
//////////    // ====================================================
//////////    // REFRESH TOKEN (ROTATION)
//////////    // ====================================================
//////////    public AuthResponse refreshToken(HttpServletRequest request,
//////////                                     HttpServletResponse response) {
//////////
//////////        String oldRefreshToken = extractRefreshToken(request);
//////////
//////////        if (oldRefreshToken == null) {
//////////            throw new RuntimeException("Refresh token missing");
//////////        }
//////////
//////////        RefreshToken storedToken =
//////////                refreshTokenService.validateAndGetToken(oldRefreshToken);
//////////
//////////        String email = storedToken.getEmail();
//////////
//////////        UserDetails user = userRepository.findByEmail(email);
//////////
//////////        if (user == null) {
//////////            throw new RuntimeException("User not found");
//////////        }
//////////
//////////        // 🔥 DELETE OLD TOKEN (rotation)
//////////        refreshTokenService.deleteToken(storedToken);
//////////
//////////        String newAccessToken = jwtUtil.generateAccessToken(
//////////                user.getEmail(),
//////////                user.getUserRole().name()
//////////        );
//////////
//////////        String newRefreshToken = jwtUtil.generateRefreshToken();
//////////
//////////        refreshTokenService.createToken(
//////////                email,
//////////                newRefreshToken,
//////////                1000L * 60 * 60 * 24 * 7
//////////        );
//////////
//////////        Cookie cookie = new Cookie("refreshToken", newRefreshToken);
//////////        cookie.setHttpOnly(true);
//////////        cookie.setSecure(false);
//////////        cookie.setPath("/api/v1/auth/refresh");
//////////        cookie.setMaxAge(60 * 60 * 24 * 7);
//////////
//////////        response.addCookie(cookie);
//////////
//////////        return new AuthResponse(newAccessToken);
//////////    }
//////////
//////////    // ====================================================
//////////    // LOGOUT
//////////    // ====================================================
//////////    public void logout(HttpServletRequest request,
//////////                       HttpServletResponse response) {
//////////
//////////        String refreshToken = extractRefreshToken(request);
//////////
//////////        if (refreshToken != null) {
//////////            RefreshToken storedToken =
//////////                    refreshTokenService.validateAndGetToken(refreshToken);
//////////            refreshTokenService.deleteToken(storedToken);
//////////        }
//////////
//////////        Cookie cookie = new Cookie("refreshToken", null);
//////////        cookie.setHttpOnly(true);
//////////        cookie.setSecure(false);
//////////        cookie.setPath("/api/v1/auth/refresh");
//////////        cookie.setMaxAge(0);
//////////
//////////        response.addCookie(cookie);
//////////    }
//////////
//////////    // ====================================================
//////////    // HELPER
//////////    // ====================================================
//////////    private String extractRefreshToken(HttpServletRequest request) {
//////////
//////////        if (request.getCookies() == null) return null;
//////////
//////////        for (Cookie cookie : request.getCookies()) {
//////////            if ("refreshToken".equals(cookie.getName())) {
//////////                return cookie.getValue();
//////////            }
//////////        }
//////////
//////////        return null;
//////////    }
//////////}
////////package com.akash.moviebooking.api.security;
////////
////////import com.akash.moviebooking.api.entity.UserDetails;
////////import com.akash.moviebooking.api.repository.UserRepository;
////////import jakarta.servlet.http.Cookie;
////////import jakarta.servlet.http.HttpServletRequest;
////////import jakarta.servlet.http.HttpServletResponse;
////////import lombok.RequiredArgsConstructor;
////////import org.springframework.security.authentication.AuthenticationManager;
////////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
////////import org.springframework.stereotype.Service;
////////
////////@Service
////////@RequiredArgsConstructor
////////public class AuthService {
////////
////////    private final AuthenticationManager authenticationManager;
////////    private final JwtUtil jwtUtil;
////////    private final UserRepository userRepository;
////////    private final RefreshTokenService refreshTokenService;
////////
////////    // ====================================================
////////    // LOGIN
////////    // ====================================================
////////    public AuthResponse login(AuthRequest request,
////////                              HttpServletResponse response) {
////////
////////        authenticationManager.authenticate(
////////                new UsernamePasswordAuthenticationToken(
////////                        request.email(),
////////                        request.password()
////////                )
////////        );
////////
////////        UserDetails user = userRepository.findByEmail(request.email());
////////
////////        if (user == null) {
////////            throw new RuntimeException("User not found");
////////        }
////////
////////        String accessToken = jwtUtil.generateAccessToken(
////////                user.getEmail(),
////////                user.getUserRole().name()
////////        );
////////
////////        // 🔥 DELETE old refresh tokens (clean session)
////////        refreshTokenService.deleteByEmail(user.getEmail());
////////
////////        String refreshToken = jwtUtil.generateRefreshToken();
////////
////////        refreshTokenService.createToken(
////////                user.getEmail(),
////////                refreshToken,
////////                1000L * 60 * 60 * 24 * 7
////////        );
////////
////////        Cookie cookie = new Cookie("refreshToken", refreshToken);
////////        cookie.setHttpOnly(true);
////////        cookie.setSecure(false); // 🔥 set true in production HTTPS
////////        cookie.setPath("/api/v1/auth/refresh");
////////        cookie.setMaxAge(60 * 60 * 24 * 7);
////////
////////        response.addCookie(cookie);
////////
////////        return new AuthResponse(accessToken);
////////    }
////////
////////    // ====================================================
////////    // REFRESH TOKEN (ROTATION)
////////    // ====================================================
////////    public AuthResponse refreshToken(HttpServletRequest request,
////////                                     HttpServletResponse response) {
////////
////////        String oldRefreshToken = extractRefreshToken(request);
////////
////////        if (oldRefreshToken == null) {
////////            throw new RuntimeException("Refresh token missing");
////////        }
////////
////////        RefreshToken storedToken =
////////                refreshTokenService.validateAndGetToken(oldRefreshToken);
////////
////////        String email = storedToken.getEmail();
////////
////////        UserDetails user = userRepository.findByEmail(email);
////////
////////        if (user == null) {
////////            throw new RuntimeException("User not found");
////////        }
////////
////////        // 🔥 DELETE OLD TOKEN (rotation)
////////        refreshTokenService.deleteToken(storedToken);
////////
////////        String newAccessToken = jwtUtil.generateAccessToken(
////////                user.getEmail(),
////////                user.getUserRole().name()
////////        );
////////
////////        String newRefreshToken = jwtUtil.generateRefreshToken();
////////
////////        refreshTokenService.createToken(
////////                email,
////////                newRefreshToken,
////////                1000L * 60 * 60 * 24 * 7
////////        );
////////
////////        Cookie cookie = new Cookie("refreshToken", newRefreshToken);
////////        cookie.setHttpOnly(true);
////////        cookie.setSecure(false);
////////        cookie.setPath("/api/v1/auth/refresh");
////////        cookie.setMaxAge(60 * 60 * 24 * 7);
////////
////////        response.addCookie(cookie);
////////
////////        return new AuthResponse(newAccessToken);
////////    }
////////
////////    // ====================================================
////////    // LOGOUT
////////    // ====================================================
//////////    public void logout(HttpServletRequest request,
//////////                       HttpServletResponse response) {
//////////
//////////        String refreshToken = extractRefreshToken(request);
//////////
//////////        if (refreshToken != null) {
//////////            RefreshToken storedToken =
//////////                    refreshTokenService.validateAndGetToken(refreshToken);
//////////            refreshTokenService.deleteToken(storedToken);
//////////        }
//////////
//////////        Cookie cookie = new Cookie("refreshToken", null);
//////////        cookie.setHttpOnly(true);
//////////        cookie.setSecure(false);
//////////        cookie.setPath("/api/v1/auth/refresh");
//////////        cookie.setMaxAge(0);
//////////
//////////        response.addCookie(cookie);
//////////    }
////////
////////    public void logout(HttpServletRequest request,
////////                       HttpServletResponse response) {
////////
////////        String refreshToken = null;
////////
////////        if (request.getCookies() != null) {
////////            for (Cookie cookie : request.getCookies()) {
////////                if ("refreshToken".equals(cookie.getName())) {
////////                    refreshToken = cookie.getValue();
////////                }
////////            }
////////        }
////////
////////        if (refreshToken != null) {
////////            refreshTokenService.deleteToken(refreshToken);
////////        }
////////
////////        Cookie cookie = new Cookie("refreshToken", null);
////////        cookie.setHttpOnly(true);
////////        cookie.setSecure(false); // true in production
////////        cookie.setPath("/");
////////        cookie.setMaxAge(0);
////////
////////        response.addCookie(cookie);
////////    }
////////
////////
////////    // ====================================================
////////    // HELPER
////////    // ====================================================
////////    private String extractRefreshToken(HttpServletRequest request) {
////////
////////        if (request.getCookies() == null) return null;
////////
////////        for (Cookie cookie : request.getCookies()) {
////////            if ("refreshToken".equals(cookie.getName())) {
////////                return cookie.getValue();
////////            }
////////        }
////////
////////        return null;
////////    }
////////}
//////package com.akash.moviebooking.api.security;
//////import com.akash.moviebooking.api.entity.UserDetails;
//////import com.akash.moviebooking.api.repository.UserRepository;
//////import jakarta.servlet.http.Cookie;
//////import jakarta.servlet.http.HttpServletRequest;
//////import jakarta.servlet.http.HttpServletResponse;
//////import lombok.RequiredArgsConstructor;
//////import org.springframework.security.authentication.AuthenticationManager;
//////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//////import org.springframework.stereotype.Service;
//////
//////@Service
//////@RequiredArgsConstructor
//////public class AuthService {
//////
//////    private final AuthenticationManager authenticationManager;
//////    private final JwtUtil jwtUtil;
//////    private final UserRepository userRepository;
//////    private final RefreshTokenService refreshTokenService;
//////
//////    public AuthResponse login(AuthRequest request,
//////                              HttpServletResponse response) {
//////
//////        authenticationManager.authenticate(
//////                new UsernamePasswordAuthenticationToken(
//////                        request.email(),
//////                        request.password()
//////                )
//////        );
//////
//////        UserDetails user =
//////                userRepository.findByEmail(request.email());
//////
//////        String accessToken =
//////                jwtUtil.generateAccessToken(
//////                        user.getEmail(),
//////                        user.getUserRole().name()
//////                );
//////
//////        String refreshToken =
//////                jwtUtil.generateRefreshToken();
//////
//////        refreshTokenService.createOrReplaceToken(
//////                user.getEmail(),
//////                refreshToken,
//////                1000L * 60 * 60 * 24 * 7
//////        );
//////
//////        Cookie cookie = new Cookie("refreshToken", refreshToken);
//////        cookie.setHttpOnly(true);
//////        cookie.setSecure(false);
//////        cookie.setPath("/");
//////        cookie.setMaxAge(60 * 60 * 24 * 7);
//////
//////        response.addCookie(cookie);
//////
//////        return new AuthResponse(accessToken);
//////    }
//////
//////    public AuthResponse refreshToken(HttpServletRequest request,
//////                                     HttpServletResponse response) {
//////
//////        String oldRefreshToken = null;
//////
//////        if (request.getCookies() != null) {
//////            for (Cookie cookie : request.getCookies()) {
//////                if ("refreshToken".equals(cookie.getName())) {
//////                    oldRefreshToken = cookie.getValue();
//////                }
//////            }
//////        }
//////
//////        if (oldRefreshToken == null) {
//////            throw new RuntimeException("Refresh token missing");
//////        }
//////
//////        RefreshToken storedToken =
//////                refreshTokenService.validateToken(oldRefreshToken);
//////
//////        String email = storedToken.getUser().getEmail();
//////
//////        UserDetails user =
//////                userRepository.findByEmail(email);
//////
//////        // ROTATION
//////        refreshTokenService.deleteToken(oldRefreshToken);
//////
//////        String newAccessToken =
//////                jwtUtil.generateAccessToken(
//////                        user.getEmail(),
//////                        user.getUserRole().name()
//////                );
//////
//////        String newRefreshToken =
//////                jwtUtil.generateRefreshToken();
//////
//////        refreshTokenService.createOrReplaceToken(
//////                user.getEmail(),
//////                newRefreshToken,
//////                1000L * 60 * 60 * 24 * 7
//////        );
//////
//////        Cookie cookie = new Cookie("refreshToken", newRefreshToken);
//////        cookie.setHttpOnly(true);
//////        cookie.setSecure(false);
//////        cookie.setPath("/");
//////        cookie.setMaxAge(60 * 60 * 24 * 7);
//////
//////        response.addCookie(cookie);
//////
//////        return new AuthResponse(newAccessToken);
//////    }
//////
//////    public void logout(HttpServletRequest request,
//////                       HttpServletResponse response) {
//////
//////        String refreshToken = null;
//////
//////        if (request.getCookies() != null) {
//////            for (Cookie cookie : request.getCookies()) {
//////                if ("refreshToken".equals(cookie.getName())) {
//////                    refreshToken = cookie.getValue();
//////                }
//////            }
//////        }
//////
//////        if (refreshToken != null) {
//////            refreshTokenService.deleteToken(refreshToken);
//////        }
//////
//////        Cookie cookie = new Cookie("refreshToken", null);
//////        cookie.setHttpOnly(true);
//////        cookie.setSecure(false);
//////        cookie.setPath("/");
//////        cookie.setMaxAge(0);
//////
//////        response.addCookie(cookie);
//////    }
//////}
////package com.akash.moviebooking.api.security;
////
////import com.akash.moviebooking.api.entity.UserDetails;
////import com.akash.moviebooking.api.repository.UserRepository;
////import jakarta.servlet.http.Cookie;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.servlet.http.HttpServletResponse;
////import lombok.RequiredArgsConstructor;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
////import org.springframework.stereotype.Service;
////
////@Service
////@RequiredArgsConstructor
////public class AuthService {
////
////    private final AuthenticationManager authenticationManager;
////    private final JwtUtil jwtUtil;
////    private final UserRepository userRepository;
////    private final RefreshTokenService refreshTokenService;
////
////    public AuthResponse login(AuthRequest request,
////                              HttpServletResponse response) {
////
////        authenticationManager.authenticate(
////                new UsernamePasswordAuthenticationToken(
////                        request.email(),
////                        request.password()
////                )
////        );
////
////        UserDetails user =
////                userRepository.findByEmail(request.email());
////
////        String accessToken =
////                jwtUtil.generateAccessToken(
////                        user.getEmail(),
////                        user.getUserRole().name()
////                );
////
////        String refreshToken =
////                jwtUtil.generateRefreshToken();
////
////        refreshTokenService.createOrReplaceToken(
////                user.getEmail(),
////                refreshToken,
////                1000L * 60 * 60 * 24 * 7
////        );
////
////        Cookie cookie = new Cookie("refreshToken", refreshToken);
////        cookie.setHttpOnly(true);
////        cookie.setSecure(false);
////        cookie.setPath("/");
////        cookie.setMaxAge(60 * 60 * 24 * 7);
////
////        response.addCookie(cookie);
////
////       // return new AuthResponse(accessToken);
////        return AuthResponse.builder()
////                .accessToken(accessToken)
////                .userId(user.getUserId())
////                .email(user.getEmail())
////                .role(user.getUserRole().name())
////                .build();
////
////    }
////
////    public AuthResponse refresh(HttpServletRequest request,
////                                HttpServletResponse response) {
////
////        String oldToken = null;
////
////        if (request.getCookies() != null) {
////            for (Cookie cookie : request.getCookies()) {
////                if ("refreshToken".equals(cookie.getName())) {
////                    oldToken = cookie.getValue();
////                }
////            }
////        }
////
////        if (oldToken == null) {
////            throw new RuntimeException("Refresh token missing");
////        }
////
////        RefreshToken stored =
////                refreshTokenService.validateToken(oldToken);
////
////        String email = stored.getUser().getEmail();
////
////        refreshTokenService.deleteToken(oldToken);
////
////        String newAccess =
////                jwtUtil.generateAccessToken(
////                        email,
////                        stored.getUser().getUserRole().name()
////                );
////
////        String newRefresh =
////                jwtUtil.generateRefreshToken();
////
////        refreshTokenService.createOrReplaceToken(
////                email,
////                newRefresh,
////                1000L * 60 * 60 * 24 * 7
////        );
////
////        Cookie cookie = new Cookie("refreshToken", newRefresh);
////        cookie.setHttpOnly(true);
////        cookie.setSecure(false);
////        cookie.setPath("/");
////        cookie.setMaxAge(60 * 60 * 24 * 7);
////
////        response.addCookie(cookie);
////
////       // return new AuthResponse(newAccess);
////        return new AuthResponse(
////                newAccess,
////                stored.getUser().getUserId(),
////                stored.getUser().getEmail(),
////                stored.getUser().getUserRole().name()
////        );
////
////    }
////
////    public void logout(HttpServletRequest request,
////                       HttpServletResponse response) {
////
////        String refreshToken = null;
////
////        if (request.getCookies() != null) {
////            for (Cookie cookie : request.getCookies()) {
////                if ("refreshToken".equals(cookie.getName())) {
////                    refreshToken = cookie.getValue();
////                }
////            }
////        }
////
////        if (refreshToken != null) {
////            refreshTokenService.deleteToken(refreshToken);
////        }
////
////        Cookie cookie = new Cookie("refreshToken", null);
////        cookie.setHttpOnly(true);
////        cookie.setSecure(false);
////        cookie.setPath("/");
////        cookie.setMaxAge(0);
////
////        response.addCookie(cookie);
////    }
////}
//package com.akash.moviebooking.api.security;
//
//import com.akash.moviebooking.api.entity.UserDetails;
//import com.akash.moviebooking.api.repository.UserRepository;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//    private final UserRepository userRepository;
//    private final RefreshTokenService refreshTokenService;
//
//    // ================= LOGIN =================
//    public AuthResponse login(AuthRequest request,
//                              HttpServletResponse response) {
//
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.email(),
//                        request.password()
//                )
//        );
//
//        UserDetails user =
//                userRepository.findByEmail(request.email());
//
//        String accessToken =
//                jwtUtil.generateAccessToken(
//                        user.getEmail(),
//                        user.getUserRole().name()
//                );
//
//        String refreshToken =
//                jwtUtil.generateRefreshToken();
//
//        refreshTokenService.createOrReplaceToken(
//                user.getEmail(),
//                refreshToken,
//                1000L * 60 * 60 * 24 * 7
//        );
//
//        Cookie cookie = new Cookie("refreshToken", refreshToken);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setPath("/");
//        cookie.setMaxAge(60 * 60 * 24 * 7);
//
//        response.addCookie(cookie);
//
//        return AuthResponse.builder()
//                .accessToken(accessToken)
//                .userId(user.getUserId())
//                .email(user.getEmail())
//                .role(user.getUserRole().name())
//                .build();
//    }
//
//    // ================= REFRESH =================
//    public AuthResponse refresh(HttpServletRequest request,
//                                HttpServletResponse response) {
//
//        String oldToken = extractRefreshToken(request);
//
//        RefreshToken stored =
//                refreshTokenService.validateToken(oldToken);
//
//        UserDetails user = stored.getUser();
//
//        refreshTokenService.deleteToken(oldToken);
//
//        String newAccess =
//                jwtUtil.generateAccessToken(
//                        user.getEmail(),
//                        user.getUserRole().name()
//                );
//
//        String newRefresh =
//                jwtUtil.generateRefreshToken();
//
//        refreshTokenService.createOrReplaceToken(
//                user.getEmail(),
//                newRefresh,
//                1000L * 60 * 60 * 24 * 7
//        );
//
//        Cookie cookie = new Cookie("refreshToken", newRefresh);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setPath("/");
//        cookie.setMaxAge(60 * 60 * 24 * 7);
//
//        response.addCookie(cookie);
//
//        return AuthResponse.builder()
//                .accessToken(newAccess)
//                .userId(user.getUserId())
//                .email(user.getEmail())
//                .role(user.getUserRole().name())
//                .build();
//    }
//
//    private String extractRefreshToken(HttpServletRequest request) {
//
//        if (request.getCookies() == null) {
//            throw new RuntimeException("Refresh token missing");
//        }
//
//        for (Cookie cookie : request.getCookies()) {
//            if ("refreshToken".equals(cookie.getName())) {
//                return cookie.getValue();
//            }
//        }
//
//        throw new RuntimeException("Refresh token missing");
//    }
//
//
//    public void logout(HttpServletRequest request,
//                       HttpServletResponse response) {
//
//        String refreshToken = null;
//
//        if (request.getCookies() != null) {
//            for (Cookie cookie : request.getCookies()) {
//                if ("refreshToken".equals(cookie.getName())) {
//                    refreshToken = cookie.getValue();
//                }
//            }
//        }
//
//        if (refreshToken != null) {
//            refreshTokenService.deleteToken(refreshToken);
//        }
//
//        Cookie cookie = new Cookie("refreshToken", null);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false); // true in production
//        cookie.setPath("/");
//        cookie.setMaxAge(0);
//
//        response.addCookie(cookie);
//    }
//
//}
package com.akash.moviebooking.api.security;

import com.akash.moviebooking.api.entity.UserDetails;
import com.akash.moviebooking.api.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    // ================= LOGIN =================
    public AuthResponse login(AuthRequest request,
                              HttpServletResponse response) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserDetails user =
                userRepository.findByEmail(request.email());

        String accessToken =
                jwtUtil.generateAccessToken(
                        user.getEmail(),
                        user.getUserRole().name()
                );

        String refreshToken =
                jwtUtil.generateRefreshToken();

        // 🔥 Replace existing refresh token
        refreshTokenService.createOrReplaceToken(
                user.getEmail(),
                refreshToken,
                1000L * 60 * 60 * 24 * 7
        );

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);

        response.addCookie(cookie);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .userId(user.getUserId())
                .email(user.getEmail())
                .role(user.getUserRole().name())
                .build();
    }

    // ================= REFRESH =================
    public AuthResponse refresh(HttpServletRequest request,
                                HttpServletResponse response) {

        String oldToken = extractRefreshToken(request);

        if (oldToken == null) {
            throw new RuntimeException("Refresh token missing");
        }

        RefreshToken stored =
                refreshTokenService.validateToken(oldToken);

        UserDetails user = stored.getUser();

        // 🔥 delete old token using user email
        refreshTokenService.deleteByUser(user.getEmail());

        String newAccess =
                jwtUtil.generateAccessToken(
                        user.getEmail(),
                        user.getUserRole().name()
                );

        String newRefresh =
                jwtUtil.generateRefreshToken();

        refreshTokenService.createOrReplaceToken(
                user.getEmail(),
                newRefresh,
                1000L * 60 * 60 * 24 * 7
        );

        Cookie cookie = new Cookie("refreshToken", newRefresh);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);

        response.addCookie(cookie);

        return AuthResponse.builder()
                .accessToken(newAccess)
                .userId(user.getUserId())
                .email(user.getEmail())
                .role(user.getUserRole().name())
                .build();
    }

    // ================= LOGOUT =================
    public void logout(HttpServletRequest request,
                       HttpServletResponse response) {

        String token = extractRefreshToken(request);

        if (token != null) {
            RefreshToken stored =
                    refreshTokenService.validateToken(token);

            refreshTokenService.deleteByUser(
                    stored.getUser().getEmail()
            );
        }

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }

    // ================= HELPER =================
    private String extractRefreshToken(HttpServletRequest request) {

        if (request.getCookies() == null)
            return null;

        for (Cookie cookie : request.getCookies()) {
            if ("refreshToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
