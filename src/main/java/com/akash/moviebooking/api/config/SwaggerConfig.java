package com.akash.moviebooking.api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI movieBookingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Movie Booking API")
                        .description("API documentation for Movie Booking System")
                        .version("v1.0")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Movie Booking API Docs")
                        .url("http://localhost:8080/api/v1"));
    }
}
