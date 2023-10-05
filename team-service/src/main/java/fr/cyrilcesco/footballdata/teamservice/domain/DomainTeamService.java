package fr.cyrilcesco.footballdata.teamservice.domain;

import fr.cyrilcesco.footballdata.teamservice.domain.api.GetTeamUseCase;
import fr.cyrilcesco.footballdata.teamservice.domain.exception.TeamNotFoundException;
import fr.cyrilcesco.footballdata.teamservice.domain.model.Team;
import fr.cyrilcesco.footballdata.teamservice.domain.spi.TeamRepositoryPort;

import java.util.List;

public class DomainTeamService implements GetTeamUseCase {

    private final TeamRepositoryPort teamRepository;

    public DomainTeamService(TeamRepositoryPort teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team getTeam(String identifiant) {
        return teamRepository.getTeam(identifiant).orElseThrow(() -> new TeamNotFoundException(identifiant));
    }

    @Override
    public List<Team> getTeams() {
        return teamRepository.getTeams();
    }


}
