package fr.cyrilcesco.footballdata.client.transfermarkt;

import fr.cyrilcesco.footballdata.client.transfermarkt.jsoup.JsoupClient;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.Player;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.Team;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktCompetitionResponse;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktTeamResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = {
        CompetitionService.class,
        TeamService.class,
        JsoupClient.class
})
@AutoConfigureWebClient
class TransfermarktClientTestIT {

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private TeamService teamService;

    @Test
    void getTeamsOfCompetition() {
        TransfermarktCompetitionResponse ligue1 = new TransfermarktClient(competitionService, teamService).getTeamsOfCompetition("FR1", "2023");

        assertEquals("FR1", ligue1.getId());
        assertEquals("Ligue 1", ligue1.getName());
        assertEquals("2023", ligue1.getSeasonYear());
        assertEquals(18, ligue1.getTeams().size());
        assertEquals(Team.builder().id("583").name("Paris Saint-Germain").link("/fc-paris-saint-germain/startseite/verein/583/saison_id/2023").build(), ligue1.getTeams().get(0));
        assertEquals(Team.builder().id("162").name("AS Monaco").link("/as-monaco/startseite/verein/162/saison_id/2023").build(), ligue1.getTeams().get(1));
    }

    @Test
    void getTeamsPlayersInformations() {
        TransfermarktTeamResponse team = new TransfermarktClient(competitionService, teamService).getTeamsPlayersInformations("583", "2023");

        assertEquals("583", team.getId());
        assertEquals("2023", team.getSeasonYear());
        assertEquals(Player.builder().name("Gianluigi Donnarumma").id("315858").link("/gianluigi-donnarumma/profil/spieler/315858").build(), team.getPlayers().get(0));
        assertEquals(Player.builder().name("Arnau Tenas").id("466783").link("/arnau-tenas/profil/spieler/466783").build(), team.getPlayers().get(1));
        assertEquals(Player.builder().name("Keylor Navas").id("79422").link("/keylor-navas/profil/spieler/79422").build(), team.getPlayers().get(2));
    }
}