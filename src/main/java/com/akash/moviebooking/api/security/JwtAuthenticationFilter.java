//package com.akash.moviebooking.api.security;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.security.SignatureException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//    private final CustomUserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String header = request.getHeader("Authorization");
//
//        try {
//
//            if (header != null && header.startsWith("Bearer ")) {
//
//                String token = header.substring(7);
//                String email = jwtUtil.extractEmail(token);
//
//                if (email != null &&
//                        SecurityContextHolder.getContext().getAuthentication() == null) {
//
//                    UserDetails userDetails =
//                            userDetailsService.loadUserByUsername(email);
//
//                    if (jwtUtil.validateToken(token, email)) {
//
//                        UsernamePasswordAuthenticationToken auth =
//                                new UsernamePasswordAuthenticationToken(
//                                        userDetails,
//                                        null,
//                                        userDetails.getAuthorities()
//                                );
//
//                        SecurityContextHolder.getContext().setAuthentication(auth);
//                    }
//                }
//            }
//
//            filterChain.doFilter(request, response);
//
//        } catch (ExpiredJwtException ex) {
//            request.setAttribute("jwt_exception", "Token expired");
//            SecurityContextHolder.clearContext();
//            throw ex;
//        } catch (MalformedJwtException ex) {
//            request.setAttribute("jwt_exception", "Invalid token structure");
//            SecurityContextHolder.clearContext();
//            throw ex;
//        } catch (UnsupportedJwtException ex) {
//            request.setAttribute("jwt_exception", "Unsupported token");
//            SecurityContextHolder.clearContext();
//            throw ex;
//        } catch (SignatureException ex) {
//            request.setAttribute("jwt_exception", "Invalid token signature");
//            SecurityContextHolder.clearContext();
//            throw ex;
//        } catch (IllegalArgumentException ex) {
//            request.setAttribute("jwt_exception", "Token is empty or invalid");
//            SecurityContextHolder.clearContext();
//            throw ex;
//        }
//    }
//}
package com.akash.moviebooking.api.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        System.out.println("Authorization Header = " + header);

        try {

            if (header != null && header.startsWith("Bearer ")) {

                String token = header.substring(7);
                String email = jwtUtil.extractEmail(token);

                if (email != null &&
                        SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails =
                            userDetailsService.loadUserByUsername(email);

                    if (jwtUtil.validateToken(token, email)) {

                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );

                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }

            filterChain.doFilter(request, response);

        } catch (JwtException | IllegalArgumentException ex) {

            // 🔥 DO NOT throw
            request.setAttribute("jwt_exception", ex.getMessage());
            SecurityContextHolder.clearContext();

            // Let Spring Security handle 401
            filterChain.doFilter(request, response);
        }
    }
}