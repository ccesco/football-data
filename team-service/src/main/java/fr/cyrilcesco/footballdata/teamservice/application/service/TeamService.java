package fr.cyrilcesco.footballdata.teamservice.application.service;

import fr.cyrilcesco.footballdata.teamservice.controller.TeamsApiDelegate;
import fr.cyrilcesco.footballdata.teamservice.domain.api.GetTeamUseCase;
import fr.cyrilcesco.footballdata.teamservice.application.mapper.TeamDtoMapper;
import fr.cyrilcesco.footballdata.teamservice.model.TeamDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService implements TeamsApiDelegate {

    private final GetTeamUseCase getTeamUseCase;
    private final TeamDtoMapper mapper;

    public TeamService(GetTeamUseCase getTeamUseCase, TeamDtoMapper mapper) {
        this.getTeamUseCase = getTeamUseCase;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<TeamDto> teamsIdGet(String identifiant) {
        TeamDto teamDto = mapper.mapFromDomain(getTeamUseCase.getTeam(identifiant));
        return ResponseEntity.ok(teamDto);
    }

    @Override
    public ResponseEntity<List<TeamDto>> teamsGet() {
        return ResponseEntity.ok(getTeamUseCase.getTeams().stream().map(mapper::mapFromDomain).toList());
    }
}

