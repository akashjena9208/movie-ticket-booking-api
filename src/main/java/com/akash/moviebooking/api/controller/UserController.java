package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.UserResponse;
import com.akash.moviebooking.api.dto.UserUpdationRequest;
import com.akash.moviebooking.api.service.UserService;
import com.akash.moviebooking.api.util.ApiResponse;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
@Tag(name = "User Management", description = "APIs for managing authenticated user accounts (Profile Update & Soft Delete)")
public class UserController {

    private final UserService userService;
    private final RestResponseBuilder responseBuilder;


    @PutMapping("/users/{email}")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update user profile", description = """
            Updates profile details of the authenticated user.
            
            IMPORTANT RULES:
            • JWT token is required.
            • Path email must match logged-in user email.
            • Users cannot update other accounts.
            """)
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User updated successfully"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation error"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - Missing or invalid JWT"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Attempt to update another user"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(

            @Parameter(description = "User email (must match logged-in user)") @PathVariable String email,

            @Valid @RequestBody UserUpdationRequest request,

            Authentication authentication, HttpServletRequest httpRequest) {

        // Ownership validation
        if (!authentication.getName().equals(email)) {
            throw new AccessDeniedException("You can update only your own account.");
        }

        return responseBuilder.success(HttpStatus.OK, "User updated successfully.", userService.updateUser(request, email), httpRequest);
    }


    @DeleteMapping("/users/{email}")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Soft delete user account", description = """
            Soft deletes the authenticated user's account.
            
            BUSINESS LOGIC:
            • Account is NOT permanently removed.
            • 'isDelete' flag is set to true.
            • Only the account owner can delete their account.
            """)
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User account deleted successfully"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - Missing or invalid JWT"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Attempt to delete another user"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<ApiResponse<UserResponse>> deleteUser(

            @Parameter(description = "User email (must match logged-in user)") @PathVariable String email,

            Authentication authentication, HttpServletRequest httpRequest) {

        // Ownership validation
        if (!authentication.getName().equals(email)) {
            throw new AccessDeniedException("You can delete only your own account.");
        }

        return responseBuilder.success(HttpStatus.OK, "User account deleted successfully.", userService.softDeleteUser(email), httpRequest);
    }
}