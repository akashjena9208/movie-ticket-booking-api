//package com.akash.moviebooking.api.security;
//
//import com.akash.moviebooking.api.entity.UserDetails;
//import com.akash.moviebooking.api.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public org.springframework.security.core.userdetails.UserDetails
//    loadUserByUsername(String email)
//            throws UsernameNotFoundException {
//
//        UserDetails user = userRepository.findByEmail(email);
//
//        if (user == null || user.isDelete()) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        return org.springframework.security.core.userdetails.User
//                .builder()
//                .username(user.getEmail())
//                .password(user.getPassword())
//                .roles(user.getUserRole().name()) // USER / THEATER_OWNER
//                .build();
//    }
//}
package com.akash.moviebooking.api.security;

import com.akash.moviebooking.api.entity.UserDetails;
import com.akash.moviebooking.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails
    loadUserByUsername(String email)
            throws UsernameNotFoundException {

        UserDetails user = userRepository.findByEmail(email);

        if (user == null || user.isDelete()) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getUserRole().name()) // ✅ FIXED
                .build();
    }
}
