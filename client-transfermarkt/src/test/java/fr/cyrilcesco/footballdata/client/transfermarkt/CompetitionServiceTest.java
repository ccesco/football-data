package fr.cyrilcesco.footballdata.client.transfermarkt;

import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktSocketTimeOut;
import fr.cyrilcesco.footballdata.client.transfermarkt.jsoup.JsoupClient;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.Team;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktCompetitionResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompetitionServiceTest {

    public static final String URL_TO_CONNECT = "https://www.transfermarkt.com/-/startseite/wettbewerb/FR1";
    public static final String CONNECT_SUFFIX = "/plus/?saison_id=2023";

    @Mock
    private JsoupClient jsoupClient;

    @Test
    void should_return_time_out_exception_when_too_long() throws IOException {
        when(jsoupClient.getDocument(any())).thenThrow(new SocketTimeoutException());

        CompetitionService competitionService = new CompetitionService(jsoupClient);
        TransfermarktSocketTimeOut socketTimeOut = assertThrows(TransfermarktSocketTimeOut.class, () -> competitionService.getTeamsOfCompetition("FR1", "2023"));
        assertEquals("Socket Time Out for id FR1", socketTimeOut.getMessage());
    }

    @Test
    void should_get_france_ligue1_when_we_call_page() throws IOException {
        File input = new File("src/test/resources/pageFR1.html");
        Document doc = Jsoup.parse(input, "UTF-8", "https://www.transfermarkt.com/-/startseite/wettbewerb/FR1");

        when(jsoupClient.getDocument(URL_TO_CONNECT + CONNECT_SUFFIX)).thenReturn(doc);

        TransfermarktCompetitionResponse response = new CompetitionService(jsoupClient).getTeamsOfCompetition("FR1", "2023");

        assertEquals("FR1", response.getId());
        assertEquals("Ligue 1", response.getName());
        assertEquals("2023", response.getSeasonYear());
        assertEquals(18, response.getTeams().size());

        Team team1 = response.getTeams().get(0);
        assertEquals("583", team1.getId());
        assertEquals("2023", team1.getSeasonYear());
        assertEquals("Paris Saint-Germain", team1.getName());
        assertEquals("/fc-paris-saint-germain/startseite/verein/583/saison_id/2023", team1.getLink());

        Team team2 = response.getTeams().get(1);
        assertEquals("162", team2.getId());
        assertEquals("2023", team2.getSeasonYear());
        assertEquals("AS Monaco", team2.getName());
        assertEquals("/as-monaco/startseite/verein/162/saison_id/2023", team2.getLink());
    }

}