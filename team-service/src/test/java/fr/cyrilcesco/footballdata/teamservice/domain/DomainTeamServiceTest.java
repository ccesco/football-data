package fr.cyrilcesco.footballdata.teamservice.domain;

import fr.cyrilcesco.footballdata.teamservice.domain.exception.TeamNotFoundException;
import fr.cyrilcesco.footballdata.teamservice.domain.model.Team;
import fr.cyrilcesco.footballdata.teamservice.domain.spi.TeamRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DomainTeamServiceTest {

    private DomainTeamService service;

    @Mock
    private TeamRepositoryPort teamRepositoryPort;

    @BeforeEach
    void init() {
        service = new DomainTeamService(teamRepositoryPort);
    }

    @Test
    void should_return_team_with_correct_id_when_get_with_specific_id() {
        when(teamRepositoryPort.getTeam("123")).thenReturn(Optional.of(getTeam("123")));

        Team team = service.getTeam("123");
        assertEquals("TEAM123", team.getName());
        assertEquals("TEAM FC", team.getOfficialName());
        assertEquals("STADIUM NAME", team.getStadiumName());
        assertEquals("123", team.getId());
    }

    @Test
    void should_return_teams_when_we_want_all_teams() {
        when(teamRepositoryPort.getTeams()).thenReturn(List.of(getTeam("123"), getTeam("456")));

        List<Team> teams = service.getTeams();

        Team team1 = teams.get(0);
        assertEquals("TEAM123", team1.getName());
        assertEquals("TEAM FC", team1.getOfficialName());
        assertEquals("STADIUM NAME", team1.getStadiumName());
        assertEquals("123", team1.getId());

        Team team2 = teams.get(1);
        assertEquals("TEAM456", team2.getName());
        assertEquals("TEAM FC", team2.getOfficialName());
        assertEquals("STADIUM NAME", team2.getStadiumName());
        assertEquals("456", team2.getId());
    }

    @Test
    void should_throw_error_when_team_with_id_not_found() {
        when(teamRepositoryPort.getTeam("123")).thenReturn(Optional.empty());

        TeamNotFoundException teamNotFoundException = assertThrows(TeamNotFoundException.class, () -> service.getTeam("123"));
        assertEquals("Team with identifiant 123 not found", teamNotFoundException.getMessage());
    }

    private static Team getTeam(String id) {
        Team team = new Team();
        team.setId(id);
        team.setName("TEAM"+id);
        team.setOfficialName("TEAM FC");
        team.setStadiumName("STADIUM NAME");
        return team;
    }

}