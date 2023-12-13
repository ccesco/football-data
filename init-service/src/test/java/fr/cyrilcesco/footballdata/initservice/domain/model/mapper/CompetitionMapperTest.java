package fr.cyrilcesco.footballdata.initservice.domain.model.mapper;

import fr.cyrilcesco.footballdata.client.transfermarkt.model.Team;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktCompetitionResponse;
import fr.cyrilcesco.footballdata.initservice.domain.model.Competition;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        List<Team> teams = List.of(Team.builder().name("PSG").seasonYear("2023").link("lien_psg").id("1").build(), Team.builder().id("2").seasonYear("2023").link("lien_lens").name("Lens").build());
        TransfermarktCompetitionResponse competitionResponse = TransfermarktCompetitionResponse.builder().id("FR1").seasonYear("2023").name("Ligue 1").teams(teams).build();
        Competition competition = mapper.mapFromClient(competitionResponse);

        assertEquals("FR1", competition.getId());
        assertEquals("Ligue 1", competition.getName());
        assertEquals("2023", competition.getSeasonYear());

        fr.cyrilcesco.footballdata.initservice.domain.model.Team team1 = competition.getTeams().get(0);
        assertEquals("PSG", team1.getName());
        assertEquals("1", team1.getId());
        assertEquals("2023", team1.getSeasonYear());
        assertNull(team1.getPlayers());
        assertEquals("lien_psg", team1.getInformationsLink());

        fr.cyrilcesco.footballdata.initservice.domain.model.Team team2 = competition.getTeams().get(1);
        assertEquals("Lens", team2.getName());
        assertEquals("2", team2.getId());
        assertEquals("2023", team2.getSeasonYear());
        assertNull(team2.getPlayers());
        assertEquals("lien_lens", team2.getInformationsLink());
    }

    @Test
    void should_return_competitions_with_team_when_response_have_teams_with_links() {
        List<Team> teams = List.of(Team.builder().name("PSG").id("1").link("LINK1").build(), Team.builder().id("2").name("Lens").link("LINK2").build());
        TransfermarktCompetitionResponse team = TransfermarktCompetitionResponse.builder().id("FR1").seasonYear("2023").name("Ligue 1").teams(teams).build();
        Competition competition = mapper.mapFromClient(team);

        assertEquals("FR1", competition.getId());
        assertEquals("Ligue 1", competition.getName());
        assertEquals("2023", competition.getSeasonYear());

        fr.cyrilcesco.footballdata.initservice.domain.model.Team team1 = competition.getTeams().get(0);
        assertEquals("PSG", team1.getName());
        assertEquals("1", team1.getId());
        assertEquals("LINK1", team1.getInformationsLink());
        assertNull(team1.getPlayers());

        fr.cyrilcesco.footballdata.initservice.domain.model.Team team2 = competition.getTeams().get(1);
        assertEquals("Lens", team2.getName());
        assertEquals("2", team2.getId());
        assertEquals("LINK2", team2.getInformationsLink());
        assertNull(team1.getPlayers());
    }
}