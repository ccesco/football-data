package fr.cyrilcesco.footballdata.client.transfermarkt.jsoup;

import fr.cyrilcesco.footballdata.client.transfermarkt.model.Team;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PageCompetitionTest {

    public static final String URL_TO_CONNECT = "https://www.transfermarkt.com/-/startseite/wettbewerb/FR1";
    public static final String CONNECT_SUFFIX = "/plus/?saison_id=2023";

    @Mock
    private JsoupClient jsoupClient;

    @Test
    void should_get_france_ligue1_when_we_call_page() throws IOException {
        File input = new File("src/test/resources/pageFR1.html");
        Document doc = Jsoup.parse(input, "UTF-8", "https://www.transfermarkt.com/-/startseite/wettbewerb/FR1");

        when(jsoupClient.getDocument(URL_TO_CONNECT + CONNECT_SUFFIX)).thenReturn(doc);

        PageCompetition pageCompetition = new PageCompetition(jsoupClient, URL_TO_CONNECT, CONNECT_SUFFIX);
        List<Team> teams = pageCompetition.getTeams();

        assertEquals("Ligue 1", pageCompetition.getCompetitionName());
        assertEquals(18, teams.size());

        Team team1 = teams.get(0);
        assertEquals("583", team1.getId());
        assertEquals("2023", team1.getSeasonYear());
        assertEquals("Paris Saint-Germain", team1.getName());
        assertEquals("/fc-paris-saint-germain/startseite/verein/583/saison_id/2023", team1.getLink());

        Team team2 = teams.get(1);
        assertEquals("162", team2.getId());
        assertEquals("2023", team2.getSeasonYear());
        assertEquals("AS Monaco", team2.getName());
        assertEquals("/as-monaco/startseite/verein/162/saison_id/2023", team2.getLink());
    }
}