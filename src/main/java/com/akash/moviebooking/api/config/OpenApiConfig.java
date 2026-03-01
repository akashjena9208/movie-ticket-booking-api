package com.akash.moviebooking.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Movie Ticket Booking System API", version = "v1.0.0", description = """
        Enterprise-grade RESTful backend system for managing:
        
        • User Authentication & Authorization (JWT)
        • Theater & Screen Management
        • Show Scheduling
        • Seat Booking
        • Payment Processing
        • Movie Feedback & Ratings
        
        Role-Based Access:
        • USER
        • THEATER_OWNER
        """, contact = @Contact(name = "Akash Jena", email = "akashjena9208@gmail.com"), license = @License(name = "MIT License", url = "https://opensource.org/licenses/MIT")), security = @SecurityRequirement(name = "bearerAuth"))
@SecurityScheme(name = "bearerAuth", description = "JWT Bearer Token Authentication. Example: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...'", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {
}