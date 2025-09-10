package com.akash.moviebooking.api.controller;

import com.akash.moviebooking.api.dto.UserRegistrationRequest;
import com.akash.moviebooking.api.dto.UserResponse;
import com.akash.moviebooking.api.dto.UserUpdationRequest;
import com.akash.moviebooking.api.service.UserService;
import com.akash.moviebooking.api.util.ResponseStructure;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@Tag(name = "User Controller", description = "APIs for managing users")
public class UserController {

    private final UserService userService;
    private final RestResponseBuilder responseBuilder;

    // ================= Register =================
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Create a new user account in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ResponseStructure<UserResponse>> addUser(
            @RequestBody @Valid UserRegistrationRequest user) {
        UserResponse userDetails = userService.addUser(user);
        return responseBuilder.sucess(HttpStatus.OK, "New User has been successfully registered", userDetails);
    }

    // ================= Update =================
    @PutMapping("/users/{email}")
    //@PreAuthorize("hasAnyAuthority('USER','THEATER_OWNER')")
    @Operation(summary = "Update user details", description = "Update profile details of an existing user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - user not logged in"),
            @ApiResponse(responseCode = "403", description = "Forbidden - user not allowed to update this account"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<ResponseStructure<UserResponse>> editUser(
            @PathVariable String email,
            @RequestBody @Valid UserUpdationRequest user) {
        UserResponse userDetails = userService.editUser(user, email);
        return responseBuilder.sucess(HttpStatus.OK, "User details have been successfully updated", userDetails);
    }

    // ================= Delete =================
    @DeleteMapping("/users/{email}")
    //@PreAuthorize("hasAnyAuthority('USER','THEATER_OWNER')")
    @Operation(summary = "Soft delete user account", description = "Marks a user account as deleted without permanently removing it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully soft-deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - user not logged in"),
            @ApiResponse(responseCode = "403", description = "Forbidden - user not allowed to delete this account"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<ResponseStructure<UserResponse>> softDeleteUser(@PathVariable String email) {
        UserResponse userDetails = userService.softDeleteUser(email);
        return responseBuilder.sucess(HttpStatus.OK, "User account has been successfully soft-deleted", userDetails);
    }
}
