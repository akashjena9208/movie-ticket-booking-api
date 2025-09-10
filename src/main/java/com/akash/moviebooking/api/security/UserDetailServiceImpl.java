//package com.akash.moviebooking.api.security;
//import com.akash.moviebooking.api.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//@Slf4j
//public class UserDetailServiceImpl implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        com.akash.moviebooking.api.entity.UserDetails user = userRepository.findByEmail(username);
//
//        if(user == null) {
//            log.error("Failed to find User by name: {}", username);
//            throw new UsernameNotFoundException("Email not found in the database");
//        }
//
//        return User.builder()
//                .username(user.getEmail())
//                .password(user.getPassword())
//                .authorities(user.getUserRole().name())
//                .build();
//    }
//
//}














//
//package com.akash.moviebooking.api.security;
//
//import com.akash.moviebooking.api.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//
//@Service
//@AllArgsConstructor
//@Slf4j
//public class UserDetailServiceImpl implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        com.akash.moviebooking.api.entity.UserDetails user = userRepository.findByEmail(email);
//        if (user == null) {
//            log.warn("Failed to find user by email: {}", email);
//            throw new UsernameNotFoundException("Email not found in the database");
//        }
//
//        return User.builder()
//                .username(user.getEmail())
//                .password(user.getPassword())
//                .authorities(Collections.singletonList(
//                        new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + user.getUserRole().name())
//                ))
//                .accountExpired(false)
//                .accountLocked(false)
//                .credentialsExpired(false)
//                .disabled(false) // ✅ set true if you add "isActive" in entity
//                .build();
//    }
//}
