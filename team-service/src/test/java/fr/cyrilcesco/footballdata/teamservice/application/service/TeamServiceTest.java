package fr.cyrilcesco.footballdata.teamservice.application.service;

import fr.cyrilcesco.footballdata.teamservice.application.mapper.TeamDtoMapperImpl;
import fr.cyrilcesco.footballdata.teamservice.domain.api.GetTeamUseCase;
import fr.cyrilcesco.footballdata.teamservice.domain.model.Team;
import fr.cyrilcesco.footballdata.teamservice.model.TeamDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private GetTeamUseCase getTeamUseCase;

    private TeamService teamService;

    @BeforeEach
    void init() {
        teamService = new TeamService(getTeamUseCase, new TeamDtoMapperImpl());
    }

    @Test
    void should_return_team_with_specific_id() {
        when(getTeamUseCase.getTeam("123")).thenReturn(getTeam());
        ResponseEntity<TeamDto> teamDtoResponseEntity = teamService.teamsIdGet("123");

        TeamDto teamDto = new TeamDto();
        teamDto.setId("123");
        teamDto.setName("TEAM");
        teamDto.setOfficialName("TEAM FC");
        teamDto.setStadiumName("STADIUM NAME");

        assertEquals(teamDto, teamDtoResponseEntity.getBody());
        assertEquals(HttpStatusCode.valueOf(200), teamDtoResponseEntity.getStatusCode());
    }

    @Test
    void should_return_teams() {
        when(getTeamUseCase.getTeams()).thenReturn(List.of(getTeam()));
        ResponseEntity<List<TeamDto>> teamDtoResponseEntity = teamService.teamsGet();

        TeamDto teamDto = new TeamDto();
        teamDto.setId("123");
        teamDto.setName("TEAM");
        teamDto.setOfficialName("TEAM FC");
        teamDto.setStadiumName("STADIUM NAME");

        assertEquals(teamDto, teamDtoResponseEntity.getBody().get(0));
        assertEquals(HttpStatusCode.valueOf(200), teamDtoResponseEntity.getStatusCode());
    }

    private static Team getTeam() {
        Team team = new Team();
        team.setId("123");
        team.setName("TEAM");
        team.setOfficialName("TEAM FC");
        team.setStadiumName("STADIUM NAME");
        return team;
    }
}