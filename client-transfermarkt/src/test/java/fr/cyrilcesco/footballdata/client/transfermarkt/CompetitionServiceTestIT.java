package fr.cyrilcesco.footballdata.client.transfermarkt;

import fr.cyrilcesco.footballdata.client.transfermarkt.jsoup.JsoupClient;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.Team;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktCompetitionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {
        JsoupClient.class
})
@AutoConfigureWebClient
class CompetitionServiceTestIT {

    @Autowired
    private JsoupClient jsoupClient;

    @Test
    void test() {
        TransfermarktCompetitionResponse ligue1 = new CompetitionService(jsoupClient).getTeamsOfCompetition("FR1", "2023");

        assertEquals("FR1", ligue1.getId());
        assertEquals("Ligue 1", ligue1.getName());
        assertEquals("2023", ligue1.getSeasonYear());
        assertEquals(18, ligue1.getTeams().size());
        assertEquals(Team.builder().id("583").name("Paris Saint-Germain").link("/fc-paris-saint-germain/startseite/verein/583/saison_id/2023").build(), ligue1.getTeams().get(0));
        assertEquals(Team.builder().id("162").name("AS Monaco").link("/as-monaco/startseite/verein/162/saison_id/2023").build(), ligue1.getTeams().get(1));
    }

}