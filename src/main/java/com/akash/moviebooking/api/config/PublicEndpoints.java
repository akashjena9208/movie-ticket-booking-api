package com.akash.moviebooking.api.config;

public final class PublicEndpoints {

    private PublicEndpoints() {
    }

    public static final String[] ENDPOINTS = {"/auth/**", "/register",

            // Swagger / OpenAPI
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",

            // Actuator
            "/actuator/**"};
}

//.requestMatchers(
//                                "/auth/**",
//                                        "/register",
//
//                                        // Swagger / OpenAPI
//                                        "/v3/api-docs/**",
//                                        "/swagger-ui/**",
//                                        "/swagger-ui.html",
//
//                                        // Actuator (optional)
//                                        "/actuator/**"
//).permitAll()