//////package com.akash.moviebooking.api.security;
//////
//////import org.springframework.data.jpa.repository.JpaRepository;
//////
//////import java.util.Optional;
//////
//////public interface RefreshTokenRepository
//////        extends JpaRepository<RefreshToken, String> {
//////
//////    Optional<RefreshToken> findByTokenHash(String tokenHash);
//////
//////    void deleteByUser_Email(String email);
//////
//////    Optional<RefreshToken> findByUser_Email(String email);
//////}
////package com.akash.moviebooking.api.security;
////
////import org.springframework.data.jpa.repository.JpaRepository;
////
////import java.util.Optional;
////
////public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
////
////    Optional<RefreshToken> findByEmail(String email);
////
////    void deleteByEmail(String email);
////}
//package com.akash.moviebooking.api.security;
//
//import com.akash.moviebooking.api.entity.UserDetails;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface RefreshTokenRepository
//        extends JpaRepository<RefreshToken, String> {
//
//
//    void deleteByUser(UserDetails user);
//
//    Optional<RefreshToken> findByUser(UserDetails user);
//}
//
//
package com.akash.moviebooking.api.security;

import com.akash.moviebooking.api.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByUser(UserDetails user);

    void deleteByUser(UserDetails user);
}
