package com.akash.moviebooking.api.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // ================= LOGIN =================
    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody AuthRequest request,
            HttpServletResponse response) {

        return authService.login(request, response);
    }

    // ================= REFRESH =================
    @PostMapping("/refresh")
    public AuthResponse refresh(
            HttpServletRequest request,
            HttpServletResponse response) {

        return authService.refresh(request, response);
    }

    // ================= LOGOUT =================
    @PostMapping("/logout")
    public String logout(
            HttpServletRequest request,
            HttpServletResponse response) {

        authService.logout(request, response);
        return "Logged out successfully";
    }
}
