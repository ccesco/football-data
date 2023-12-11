package fr.cyrilcesco.footballdata.initservice.competitions.config;

import fr.cyrilcesco.footballdata.client.transfermarkt.TransferMarktClientConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({TransferMarktClientConfiguration.class})
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI initServiceOpenAPI() {
        return new OpenAPI();
    }
}