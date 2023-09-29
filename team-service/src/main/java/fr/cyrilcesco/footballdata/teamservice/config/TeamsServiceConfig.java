package fr.cyrilcesco.footballdata.teamservice.config;

import fr.cyrilcesco.footballdata.teamservice.domain.DomainTeamService;
import fr.cyrilcesco.footballdata.teamservice.infrastructure.service.TeamRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamsServiceConfig {

    private final TeamRepository teamRepository;

    public TeamsServiceConfig(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Bean
    public DomainTeamService domainTeamService() {
        return new DomainTeamService(teamRepository);
    }
}
