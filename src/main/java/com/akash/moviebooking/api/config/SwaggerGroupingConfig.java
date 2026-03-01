package com.akash.moviebooking.api.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerGroupingConfig {

    // ==========================
    // ======== V1 APIs =========
    // ==========================

    @Bean
    public GroupedOpenApi v1UserApis() {
        return GroupedOpenApi.builder()
                .group("v1 - USER APIs")
                .pathsToMatch(
                        "/bookings/**",
                        "/payments/**",
                        "/movies/*/feedbacks/**"
                )
                .build();
    }

    @Bean
    public GroupedOpenApi v1OwnerApis() {
        return GroupedOpenApi.builder()
                .group("v1 - THEATER OWNER APIs")
                .pathsToMatch(
                        "/theaters/**",
                        "/theaters/*/screens/**",
                        "/shows/**",
                        "/movies/**"
                )
                .build();
    }

    @Bean
    public GroupedOpenApi v1PublicApis() {
        return GroupedOpenApi.builder()
                .group("v1 - PUBLIC APIs")
                .pathsToMatch(
                        "/auth/**",
                        "/register",
                        "/movies/search",
                        "/movies/{id}",
                        "/shows"
                )
                .build();
    }

    // ==========================
    // ======== V2 APIs =========
    // ==========================

    @Bean
    public GroupedOpenApi v2UserApis() {
        return GroupedOpenApi.builder()
                .group("v2 - USER APIs")
                .pathsToMatch("/v2/bookings/**", "/v2/payments/**")
                .build();
    }

    @Bean
    public GroupedOpenApi v2OwnerApis() {
        return GroupedOpenApi.builder()
                .group("v2 - THEATER OWNER APIs")
                .pathsToMatch("/v2/theaters/**", "/v2/movies/**")
                .build();
    }
}