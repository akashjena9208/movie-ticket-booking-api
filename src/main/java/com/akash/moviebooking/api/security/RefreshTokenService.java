//////////////package com.akash.moviebooking.api.security;
//////////////
//////////////import com.akash.moviebooking.api.entity.UserDetails;
//////////////import com.akash.moviebooking.api.repository.UserRepository;
//////////////import lombok.RequiredArgsConstructor;
//////////////import org.springframework.security.crypto.password.PasswordEncoder;
//////////////import org.springframework.stereotype.Service;
//////////////
//////////////import java.time.Instant;
//////////////import java.util.Optional;
//////////////
//////////////@Service
//////////////@RequiredArgsConstructor
//////////////public class RefreshTokenService {
//////////////
//////////////    private final RefreshTokenRepository refreshTokenRepository;
//////////////    private final UserRepository userRepository;
//////////////    private final PasswordEncoder passwordEncoder;
//////////////
//////////////    // 🔹 Create or Replace Refresh Token
//////////////    public void createOrReplaceToken(String email,
//////////////                                     String rawToken,
//////////////                                     long expiryMillis) {
//////////////
//////////////        UserDetails user = userRepository.findByEmail(email);
//////////////
//////////////        if (user == null) {
//////////////            throw new RuntimeException("User not found");
//////////////        }
//////////////
//////////////        // Delete old token if exists
//////////////        refreshTokenRepository.deleteByUser_Email(email);
//////////////
//////////////        RefreshToken refreshToken = RefreshToken.builder()
//////////////                .user(user)
//////////////                .tokenHash(passwordEncoder.encode(rawToken))
//////////////                .expiryDate(Instant.now().plusMillis(expiryMillis))
//////////////                .build();
//////////////
//////////////        refreshTokenRepository.save(refreshToken);
//////////////    }
//////////////
//////////////    // 🔹 Validate Refresh Token
//////////////    public RefreshToken validateRefreshToken(String rawToken) {
//////////////
//////////////        Optional<RefreshToken> tokens =
//////////////                refreshTokenRepository.findAll()
//////////////                        .stream()
//////////////                        .filter(t -> passwordEncoder.matches(rawToken, t.getTokenHash()))
//////////////                        .findFirst();
//////////////
//////////////        if (tokens.isEmpty()) {
//////////////            throw new RuntimeException("Invalid refresh token");
//////////////        }
//////////////
//////////////        RefreshToken refreshToken = tokens.get();
//////////////
//////////////        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
//////////////            refreshTokenRepository.delete(refreshToken);
//////////////            throw new RuntimeException("Refresh token expired");
//////////////        }
//////////////
//////////////        return refreshToken;
//////////////    }
//////////////
//////////////    // 🔹 Delete token (Logout)
//////////////    public void deleteByEmail(String email) {
//////////////        refreshTokenRepository.deleteByUser_Email(email);
//////////////    }
//////////////}
////////////package com.akash.moviebooking.api.security;
////////////
////////////import lombok.RequiredArgsConstructor;
////////////import org.springframework.security.crypto.password.PasswordEncoder;
////////////import org.springframework.stereotype.Service;
////////////
////////////import java.time.Instant;
////////////
////////////@Service
////////////@RequiredArgsConstructor
////////////public class RefreshTokenService {
////////////
////////////    private final RefreshTokenRepository refreshTokenRepository;
////////////    private final PasswordEncoder passwordEncoder;
////////////
////////////    // ====================================================
////////////    // CREATE OR REPLACE REFRESH TOKEN
////////////    // ====================================================
////////////    public void createOrReplaceToken(String email,
////////////                                     String rawToken,
////////////                                     Long durationMillis) {
////////////
////////////        refreshTokenRepository.deleteByEmail(email);
////////////
////////////        RefreshToken refreshToken = new RefreshToken();
////////////        refreshToken.setEmail(email);
////////////        refreshToken.setTokenHash(passwordEncoder.encode(rawToken));
////////////        refreshToken.setExpiryDate(
////////////                Instant.now().plusMillis(durationMillis)
////////////        );
////////////
////////////        refreshTokenRepository.save(refreshToken);
////////////    }
////////////
////////////    // ====================================================
////////////    // VALIDATE REFRESH TOKEN
////////////    // ====================================================
////////////    public String validateRefreshToken(String rawToken) {
////////////
////////////        for (RefreshToken storedToken : refreshTokenRepository.findAll()) {
////////////
////////////            if (passwordEncoder.matches(rawToken, storedToken.getTokenHash())) {
////////////
////////////                if (storedToken.getExpiryDate().isBefore(Instant.now())) {
////////////                    throw new RuntimeException("Refresh token expired");
////////////                }
////////////
////////////                return storedToken.getEmail();
////////////            }
////////////        }
////////////
////////////        throw new RuntimeException("Invalid refresh token");
////////////    }
////////////
////////////    // ====================================================
////////////    // DELETE TOKEN (LOGOUT)
////////////    // ====================================================
////////////    public void deleteByToken(String rawToken) {
////////////
////////////        for (RefreshToken storedToken : refreshTokenRepository.findAll()) {
////////////
////////////            if (passwordEncoder.matches(rawToken, storedToken.getTokenHash())) {
////////////
////////////                refreshTokenRepository.delete(storedToken);
////////////                return;
////////////            }
////////////        }
////////////    }
////////////}
//////////package com.akash.moviebooking.api.security;
//////////
//////////import lombok.RequiredArgsConstructor;
//////////import org.springframework.security.crypto.password.PasswordEncoder;
//////////import org.springframework.stereotype.Service;
//////////
//////////import java.time.Instant;
//////////
//////////@Service
//////////@RequiredArgsConstructor
//////////public class RefreshTokenService {
//////////
//////////    private final RefreshTokenRepository refreshTokenRepository;
//////////    private final PasswordEncoder passwordEncoder;
//////////
//////////    // ====================================================
//////////    // CREATE NEW TOKEN
//////////    // ====================================================
//////////    public void createToken(String email,
//////////                            String rawToken,
//////////                            Long durationMillis) {
//////////
//////////        RefreshToken refreshToken = new RefreshToken();
//////////        refreshToken.setEmail(email);
//////////        refreshToken.setTokenHash(passwordEncoder.encode(rawToken));
//////////        refreshToken.setExpiryDate(
//////////                Instant.now().plusMillis(durationMillis)
//////////        );
//////////
//////////        refreshTokenRepository.save(refreshToken);
//////////    }
//////////
//////////    // ====================================================
//////////    // VALIDATE & RETURN ENTITY
//////////    // ====================================================
//////////    public RefreshToken validateAndGetToken(String rawToken) {
//////////
//////////        for (RefreshToken storedToken : refreshTokenRepository.findAll()) {
//////////
//////////            if (passwordEncoder.matches(rawToken, storedToken.getTokenHash())) {
//////////
//////////                if (storedToken.getExpiryDate().isBefore(Instant.now())) {
//////////                    throw new RuntimeException("Refresh token expired");
//////////                }
//////////
//////////                return storedToken;
//////////            }
//////////        }
//////////
//////////        throw new RuntimeException("Invalid refresh token");
//////////    }
//////////
//////////    // ====================================================
//////////    // DELETE TOKEN
//////////    // ====================================================
//////////    public void deleteToken(RefreshToken token) {
//////////        refreshTokenRepository.delete(token);
//////////    }
//////////
//////////    public void deleteByEmail(String email) {
//////////        refreshTokenRepository.deleteByEmail(email);
//////////    }
//////////}
////////package com.akash.moviebooking.api.security;
////////
////////import lombok.RequiredArgsConstructor;
////////import org.springframework.security.crypto.password.PasswordEncoder;
////////import org.springframework.stereotype.Service;
////////
////////import java.time.Instant;
////////
////////@Service
////////@RequiredArgsConstructor
////////public class RefreshTokenService {
////////
////////    private final RefreshTokenRepository refreshTokenRepository;
////////    private final PasswordEncoder passwordEncoder;
////////
////////    // ====================================================
////////    // CREATE NEW REFRESH TOKEN
////////    // ====================================================
////////    public void createToken(String email,
////////                            String rawToken,
////////                            Long durationMillis) {
////////
////////        RefreshToken refreshToken = new RefreshToken();
////////        refreshToken.setEmail(email);
////////        refreshToken.setTokenHash(passwordEncoder.encode(rawToken));
////////        refreshToken.setExpiryDate(
////////                Instant.now().plusMillis(durationMillis)
////////        );
////////
////////        refreshTokenRepository.save(refreshToken);
////////    }
////////
////////    // ====================================================
////////    // VALIDATE AND RETURN TOKEN ENTITY
////////    // ====================================================
////////    public RefreshToken validateAndGetToken(String rawToken) {
////////
////////        for (RefreshToken storedToken : refreshTokenRepository.findAll()) {
////////
////////            if (passwordEncoder.matches(rawToken, storedToken.getTokenHash())) {
////////
////////                if (storedToken.getExpiryDate().isBefore(Instant.now())) {
////////                    throw new RuntimeException("Refresh token expired");
////////                }
////////
////////                return storedToken;
////////            }
////////        }
////////
////////        throw new RuntimeException("Invalid refresh token");
////////    }
////////
////////    // ====================================================
////////    // DELETE SPECIFIC TOKEN
////////    // ====================================================
////////    public void deleteToken(RefreshToken token) {
////////        refreshTokenRepository.delete(token);
////////    }
////////
////////    // ====================================================
////////    // DELETE BY EMAIL (LOGIN CLEANUP)
////////    // ====================================================
////////    public void deleteByEmail(String email) {
////////        refreshTokenRepository.deleteByEmail(email);
////////    }
////////}
//////package com.akash.moviebooking.api.security;
//////
//////
//////import com.akash.moviebooking.api.entity.UserDetails;
//////import com.akash.moviebooking.api.repository.UserRepository;
//////import lombok.RequiredArgsConstructor;
//////import org.springframework.security.crypto.password.PasswordEncoder;
//////import org.springframework.stereotype.Service;
//////
//////import java.time.Instant;
//////import java.util.Optional;
//////
//////@Service
//////@RequiredArgsConstructor
//////public class RefreshTokenService {
//////
//////    private final RefreshTokenRepository refreshTokenRepository;
//////    private final UserRepository userRepository;
//////    private final PasswordEncoder passwordEncoder;
//////
//////    public void createOrReplaceToken(String email,
//////                                     String rawToken,
//////                                     Long durationMillis) {
//////
//////        UserDetails user = userRepository.findByEmail(email);
//////
//////        refreshTokenRepository.deleteByUser(user);
//////
//////        RefreshToken refreshToken = new RefreshToken();
//////        refreshToken.setUser(user);
//////        refreshToken.setToken(passwordEncoder.encode(rawToken));
//////        refreshToken.setExpiryDate(
//////                Instant.now().plusMillis(durationMillis)
//////        );
//////
//////        refreshTokenRepository.save(refreshToken);
//////    }
//////
//////    public RefreshToken validateToken(String rawToken) {
//////
//////        Optional<RefreshToken> tokenOptional =
//////                refreshTokenRepository.findAll()
//////                        .stream()
//////                        .filter(token ->
//////                                passwordEncoder.matches(
//////                                        rawToken,
//////                                        token.getToken()
//////                                )
//////                        )
//////                        .findFirst();
//////
//////        if (tokenOptional.isEmpty()) {
//////            throw new RuntimeException("Invalid refresh token");
//////        }
//////
//////        RefreshToken refreshToken = tokenOptional.get();
//////
//////        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
//////            refreshTokenRepository.delete(refreshToken);
//////            throw new RuntimeException("Refresh token expired");
//////        }
//////
//////        return refreshToken;
//////    }
//////
//////    public void deleteToken(String rawToken) {
//////
//////        refreshTokenRepository.findAll()
//////                .stream()
//////                .filter(token ->
//////                        passwordEncoder.matches(
//////                                rawToken,
//////                                token.getToken()
//////                        )
//////                )
//////                .findFirst()
//////                .ifPresent(refreshTokenRepository::delete);
//////    }
//////}
////package com.akash.moviebooking.api.security;
////
////import com.akash.moviebooking.api.entity.UserDetails;
////import com.akash.moviebooking.api.repository.UserRepository;
////import jakarta.transaction.Transactional;
////import lombok.RequiredArgsConstructor;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.stereotype.Service;
////
////import java.time.Instant;
////@Transactional
////@Service
////@RequiredArgsConstructor
////public class RefreshTokenService {
////
////    private final RefreshTokenRepository refreshTokenRepository;
////    private final UserRepository userRepository;
////    private final PasswordEncoder passwordEncoder;
////
//////    public void createOrReplaceToken(String email,
//////                                     String rawToken,
//////                                     Long durationMillis) {
//////
//////        UserDetails user = userRepository.findByEmail(email);
//////
//////        refreshTokenRepository.deleteByUser(user);
//////
//////        RefreshToken token = new RefreshToken();
//////        token.setUser(user);
//////        token.setToken(passwordEncoder.encode(rawToken));
//////        token.setExpiryDate(
//////                Instant.now().plusMillis(durationMillis)
//////        );
//////
//////        refreshTokenRepository.save(token);
//////    }
////
////    public RefreshToken createOrReplaceToken(String email,
////                                             String token,
////                                             long durationMillis) {
////
////        UserDetails user =
////                userRepository.findByEmail(email);
////
////        if (user == null) {
////            throw new RuntimeException("User not found");
////        }
////
////        RefreshToken refreshToken =
////                refreshTokenRepository.findByUser(user)
////                        .orElse(new RefreshToken());
////
////        refreshToken.setUser(user);
////        refreshToken.setToken(token);
////        refreshToken.setExpiryDate(
////                Instant.now().plusMillis(durationMillis)
////        );
////
////        return refreshTokenRepository.save(refreshToken);
////    }
////
////
////    public RefreshToken validateToken(String rawToken) {
////
////        return refreshTokenRepository.findAll()
////                .stream()
////                .filter(token ->
////                        passwordEncoder.matches(
////                                rawToken,
////                                token.getToken()
////                        )
////                )
////                .findFirst()
////                .orElseThrow(() ->
////                        new RuntimeException("Invalid refresh token")
////                );
////    }
////
////    public void deleteToken(String rawToken) {
////
////        refreshTokenRepository.findAll()
////                .stream()
////                .filter(token ->
////                        passwordEncoder.matches(
////                                rawToken,
////                                token.getToken()
////                        )
////                )
////                .findFirst()
////                .ifPresent(refreshTokenRepository::delete);
////    }
////}
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
//    // ================= CREATE OR REPLACE =================
//    public void createOrReplaceToken(String email,
//                                     String rawToken,
//                                     long durationMillis) {
//
//        UserDetails user = userRepository.findByEmail(email);
//
//        if (user == null) {
//            throw new RuntimeException("User not found");
//        }
//
//        refreshTokenRepository.deleteByUser(user);
//
//        RefreshToken refreshToken = new RefreshToken();
//        refreshToken.setUser(user);
//        refreshToken.setTokenHash(passwordEncoder.encode(rawToken));
//        refreshToken.setExpiryDate(
//                Instant.now().plusMillis(durationMillis)
//        );
//
//        refreshTokenRepository.save(refreshToken);
//    }
//
//    // ================= VALIDATE =================
//    public RefreshToken validateToken(String rawToken) {
//
//        return refreshTokenRepository.findAll()
//                .stream()
//                .filter(token ->
//                        passwordEncoder.matches(
//                                rawToken,
//                                token.getTokenHash()
//                        )
//                )
//                .findFirst()
//                .orElseThrow(() ->
//                        new RuntimeException("Invalid refresh token")
//                );
//    }
//
//    // ================= DELETE =================
//    public void deleteToken(String rawToken) {
//
//        refreshTokenRepository.findAll()
//                .stream()
//                .filter(token ->
//                        passwordEncoder.matches(
//                                rawToken,
//                                token.getTokenHash()
//                        )
//                )
//                .findFirst()
//                .ifPresent(refreshTokenRepository::delete);
//    }
//}
package com.akash.moviebooking.api.security;

import com.akash.moviebooking.api.entity.UserDetails;
import com.akash.moviebooking.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshToken createOrReplaceToken(String email,
                                             String rawToken,
                                             long durationMillis) {

        UserDetails user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        RefreshToken token = refreshTokenRepository
                .findByUser(user)
                .orElse(new RefreshToken());

        token.setUser(user);
        token.setToken(rawToken);
        token.setExpiryDate(
                Instant.now().plusMillis(durationMillis)
        );

        return refreshTokenRepository.save(token);
    }

    public RefreshToken validateToken(String rawToken) {

        return refreshTokenRepository.findAll()
                .stream()
                .filter(t -> t.getToken().equals(rawToken))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token")
                );
    }

    public void deleteByUser(String email) {

        UserDetails user = userRepository.findByEmail(email);
        refreshTokenRepository.deleteByUser(user);
    }
}
