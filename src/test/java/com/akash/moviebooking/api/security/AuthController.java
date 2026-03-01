//package com.akash.moviebooking.api.security;
//
//import com.akash.moviebooking.api.util.RestResponseBuilder;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import jakarta.servlet.http.HttpServletResponse;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//class AuthControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private AuthService authService;
//
//    @Mock
//    private RestResponseBuilder responseBuilder;
//
//    @InjectMocks
//    private AuthController authController;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(authController)
//                .build();
//    }
//
//    @Test
//    void login_shouldReturn200() throws Exception {
//
//        AuthRequest request = new AuthRequest(
//                "akash@gmail.com",
//                "Password@123"
//        );
//
//        when(authService.login(any(), any(HttpServletResponse.class)))
//                .thenReturn(
//                        AuthResponse.builder()
//                                .accessToken("fake-token")
//                                .email("akash@gmail.com")
//                                .build()
//                );
//
//        mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk());
//
//        verify(authService, times(1))
//                .login(any(), any(HttpServletResponse.class));
//    }
//}
package com.akash.moviebooking.api.security;

import com.akash.moviebooking.api.util.RestResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @Mock
    private RestResponseBuilder responseBuilder;

    @InjectMocks
    private AuthController authController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .build();
    }

    @Test
    void login_shouldReturn200() throws Exception {

        AuthRequest request = new AuthRequest(
                "akash@gmail.com",
                "Password@123"
        );

        AuthResponse authResponse = AuthResponse.builder()
                .accessToken("fake-token")
                .email("akash@gmail.com")
                .build();

        when(authService.login(any(), any(HttpServletResponse.class)))
                .thenReturn(authResponse);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(authService, times(1))
                .login(any(), any(HttpServletResponse.class));
    }
}