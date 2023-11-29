package fr.cyrilcesco.footballdata.competitionservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI teamsMicroserviceOpenAPI() {
        return new OpenAPI();
    }
}