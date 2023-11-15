package fr.cyrilcesco.footballdata.initservice.competitions.model.mapper;

import fr.cyrilcesco.footballdata.client.transfermarkt.model.Team;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktCompetitionResponse;
import fr.cyrilcesco.footballdata.initservice.competitions.model.Competition;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompetitionMapperTest {

    private final CompetitionMapper mapper = new CompetitionMapperImpl();

    @Test
    void should_return_empty_when_team_empty() {
        TransfermarktCompetitionResponse team = TransfermarktCompetitionResponse.builder().build();
        Competition competition = mapper.mapFromClient(team);

        assertNull(competition.getId());
        assertNull(competition.getName());
        assertNull(competition.getTeams());
        assertNull(competition.getSeasonYear());
    }

    @Test
    void should_return_competitions_with_team_empty_when_response_have_no_teams() {
        TransfermarktCompetitionResponse team = TransfermarktCompetitionResponse.builder().id("FR1").seasonYear("2023").name("Ligue 1").teams(Collections.emptyList()).build();
        Competition competition = mapper.mapFromClient(team);

        assertEquals("FR1", competition.getId());
        assertEquals("Ligue 1", competition.getName());
        assertTrue(competition.getTeams().isEmpty());
        assertEquals("2023", competition.getSeasonYear());
    }

    @Test
    void should_return_competitions_with_team_when_response_have_teams() {
        List<Team> teams = List.of(Team.builder().name("PSG").id("1").build(), Team.builder().id("2").name("Lens").build());
        TransfermarktCompetitionResponse team = TransfermarktCompetitionResponse.builder().id("FR1").seasonYear("2023").name("Ligue 1").teams(teams).build();
        Competition competition = mapper.mapFromClient(team);

        assertEquals("FR1", competition.getId());
        assertEquals("Ligue 1", competition.getName());
        assertEquals("PSG", competition.getTeams().get(0).getName());
        assertEquals("1", competition.getTeams().get(0).getId());
        assertEquals("Lens", competition.getTeams().get(1).getName());
        assertEquals("2", competition.getTeams().get(1).getId());
        assertEquals("2023", competition.getSeasonYear());
    }
}