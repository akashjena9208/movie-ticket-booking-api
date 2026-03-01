package com.akash.moviebooking.api.security;
import com.akash.moviebooking.api.dto.UserRegistrationRequest;
import com.akash.moviebooking.api.dto.UserResponse;
import com.akash.moviebooking.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.akash.moviebooking.api.dto.ReactivationRequest;
import com.akash.moviebooking.api.util.ApiResponse;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Login, Refresh and Logout APIs")
public class AuthController {

    private final AuthService authService;
    private final RestResponseBuilder responseBuilder;
    private final UserService userService;


    // ================= REGISTER =================
    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody UserRegistrationRequest request,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.CREATED,
                "User registered successfully.",
                userService.registerUser(request),
                httpRequest
        );
    }

    @PostMapping("/login")
    @Operation(summary = "User login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody AuthRequest request,
            HttpServletResponse response,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Login successful.",
                authService.login(request, response),
                httpRequest
        );
    }


    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(
            HttpServletRequest request,
            HttpServletResponse response) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Access token refreshed successfully.",
                authService.refresh(request, response),
                request
        );
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout user")
    public ResponseEntity<ApiResponse<Object>> logout(
            HttpServletRequest request,
            HttpServletResponse response) {

        authService.logout(request, response);

        return responseBuilder.success(
                HttpStatus.OK,
                "Logged out successfully.",
                null,
                request
        );
    }


    @PostMapping("/reactivate")
    @Operation(summary = "Reactivate deactivated account")
    public ResponseEntity<ApiResponse<AuthResponse>> reactivate(
            @Valid @RequestBody ReactivationRequest request,
            HttpServletResponse response,
            HttpServletRequest httpRequest) {

        return responseBuilder.success(
                HttpStatus.OK,
                "Account reactivated successfully.",
                authService.reactivateAccount(request, response),
                httpRequest
        );
    }
}
