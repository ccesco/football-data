package fr.cyrilcesco.footballdata.playersservice.config;

import fr.cyrilcesco.footballdata.playersservice.domain.DomainPlayerService;
import fr.cyrilcesco.footballdata.playersservice.service.PlayerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayersServiceConfig {

    private final PlayerRepository playerRepository;

    public PlayersServiceConfig(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Bean
    public DomainPlayerService domainPlayerService() {
        return new DomainPlayerService(playerRepository);
    }
}
