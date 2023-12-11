package fr.cyrilcesco.footballdata.client.transfermarkt;

import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktSocketTimeOut;
import fr.cyrilcesco.footballdata.client.transfermarkt.jsoup.JsoupClient;
import fr.cyrilcesco.footballdata.client.transfermarkt.jsoup.PageTeamPlayers;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.Player;
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
class TeamServiceTest {


    public static final String URL_TO_CONNECT = "https://www.transfermarkt.com/-/kader/verein/583/saison_id/2023/plus/1";

    @Mock
    private JsoupClient jsoupClient;

    @Test
    void should_return_time_out_exception_when_too_long() throws IOException {
        when(jsoupClient.getDocument(any())).thenThrow(new SocketTimeoutException());

        TeamService teamService = new TeamService(jsoupClient);
        TransfermarktSocketTimeOut socketTimeOut = assertThrows(TransfermarktSocketTimeOut.class, () -> teamService.getTeamPlayersInformation("583", "2023"));
        assertEquals("Socket Time Out for id 583", socketTimeOut.getMessage());
    }

    @Test
    void should_get_paris_saint_germain_players_when_we_call_page() throws IOException {
        File input = new File("src/test/resources/pagePSGPlayers.html");
        Document doc = Jsoup.parse(input, "UTF-8", "https://www.transfermarkt.com/-/startseite/wettbewerb/FR1");

        when(jsoupClient.getDocument(URL_TO_CONNECT)).thenReturn(doc);

        PageTeamPlayers pageTeamPlayers = new PageTeamPlayers(jsoupClient, URL_TO_CONNECT);

        assertEquals(29, pageTeamPlayers.getPlayers().size());
        assertEquals("Paris Saint-Germain", pageTeamPlayers.getTeamName());

        Player player1 = pageTeamPlayers.getPlayers().get(0);
        assertEquals(Player.builder().name("Gianluigi Donnarumma").id("315858").link("/gianluigi-donnarumma/profil/spieler/315858").build(), player1);

        Player player2 = pageTeamPlayers.getPlayers().get(1);
        assertEquals(Player.builder().name("Arnau Tenas").id("466783").link("/arnau-tenas/profil/spieler/466783").build(), player2);
    }

}