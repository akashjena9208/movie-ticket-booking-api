package com.akash.moviebooking.api.service;

import com.akash.moviebooking.api.dto.UserRegistrationRequest;
import com.akash.moviebooking.api.dto.UserResponse;
import com.akash.moviebooking.api.entity.User;
import com.akash.moviebooking.api.enums.UserRole;
import com.akash.moviebooking.api.mapper.UserDetailsMapper;
import com.akash.moviebooking.api.repository.UserRepository;
import com.akash.moviebooking.api.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetailsMapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRegistrationRequest request;

    @BeforeEach
    void setup() {
        request = new UserRegistrationRequest(
                "akash",
                "akash@gmail.com",
                "Password@123",
                "9876543210",
                UserRole.USER,
                LocalDate.of(2002, 1, 1)
        );
    }

    @Test
    void registerUser_success() {

        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setEmail(request.email());

        when(userRepository.save(any())).thenReturn(savedUser);
        when(mapper.toDto(any())).thenReturn(
                UserResponse.builder()
                        .email("akash@gmail.com")
                        .username("akash")
                        .build()
        );

        UserResponse response = userService.registerUser(request);

        assertNotNull(response);
        assertEquals("akash@gmail.com", response.email());

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void registerUser_duplicateEmail_shouldThrowException() {

        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> userService.registerUser(request));

        verify(userRepository, never()).save(any());
    }
}