//
//package com.akash.moviebooking.api.controller;
//
//import com.akash.moviebooking.api.dto.*;
//import com.akash.moviebooking.api.service.UserService;
//import com.akash.moviebooking.api.util.ApiResponse;
//import com.akash.moviebooking.api.util.RestResponseBuilder;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@Tag(name = "User Management", description = "APIs for managing users")
//public class UserController {
//
//    private final UserService userService;
//    private final RestResponseBuilder responseBuilder;
//
//    @PostMapping("/register")
//    public ResponseEntity<ApiResponse<UserResponse>> register(
//            @Valid @RequestBody UserRegistrationRequest request,
//            HttpServletRequest httpRequest) {
//
//        return responseBuilder.success(
//                HttpStatus.CREATED,
//                "User registered successfully.",
//                userService.registerUser(request),
//                httpRequest
//        );
//    }
//
//    @PutMapping("/users/{email}")
//    @PreAuthorize("isAuthenticated()")
//    @SecurityRequirement(name = "bearerAuth")
//    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
//            @PathVariable String email,
//            @Valid @RequestBody UserUpdationRequest request,
//            HttpServletRequest httpRequest) {
//
//        return responseBuilder.success(
//                HttpStatus.OK,
//                "User updated successfully.",
//                userService.updateUser(request, email),
//                httpRequest
//        );
//    }
//
//    @DeleteMapping("/users/{email}")
//    @PreAuthorize("isAuthenticated()")
//    @SecurityRequirement(name = "bearerAuth")
//    public ResponseEntity<ApiResponse<UserResponse>> deleteUser(
//            @PathVariable String email,
//            HttpServletRequest httpRequest) {
//
//        return responseBuilder.success(
//                HttpStatus.OK,
//                "User account deleted successfully.",
//                userService.softDeleteUser(email),
//                httpRequest
//        );
//    }
//}



package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.UserRegistrationRequest;
import com.akash.moviebooking.api.dto.UserResponse;
import com.akash.moviebooking.api.dto.UserUpdationRequest;
import com.akash.moviebooking.api.service.UserService;
import com.akash.moviebooking.api.util.ApiResponse;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserService userService;
    private final RestResponseBuilder responseBuilder;


    // ================= UPDATE =================
    @PutMapping("/users/{email}")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update user profile")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable String email,
            @Valid @RequestBody UserUpdationRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest) {

        // 🔥 Ownership validation
        if (!authentication.getName().equals(email)) {
            throw new AccessDeniedException(
                    "You can update only your own account."
            );
        }

        return responseBuilder.success(
                HttpStatus.OK,
                "User updated successfully.",
                userService.updateUser(request, email),
                httpRequest
        );
    }

    // ================= DELETE (Soft) =================
    @DeleteMapping("/users/{email}")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Soft delete user account")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUser(
            @PathVariable String email,
            Authentication authentication,
            HttpServletRequest httpRequest) {

        // 🔥 Ownership validation
        if (!authentication.getName().equals(email)) {
            throw new AccessDeniedException(
                    "You can delete only your own account."
            );
        }

        return responseBuilder.success(
                HttpStatus.OK,
                "User account deleted successfully.",
                userService.softDeleteUser(email),
                httpRequest
        );
    }

}