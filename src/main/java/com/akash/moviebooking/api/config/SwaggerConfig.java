//package com.akash.moviebooking.api.config;
//
//import io.swagger.v3.oas.models.ExternalDocumentation;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public OpenAPI movieBookingOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("Movie Booking API")
//                        .description("API documentation for Movie Booking System")
//                        .version("v1.0")
//                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))
//                .externalDocs(new ExternalDocumentation()
//                        .description("Movie Booking API Docs")
//                        .url("http://localhost:8080/api/v1"));
//    }
//}
package com.akash.moviebooking.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI movieBookingOpenAPI() {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Movie Ticket Booking System API")
                        .description("""
                                REST API documentation for Movie Ticket Booking System.

                                Roles:
                                • USER
                                • THEATER_OWNER

                                All secured endpoints require JWT Bearer token.
                                """)
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Akash Jena")
                                .email("akash@example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}