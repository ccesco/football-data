package fr.cyrilcesco.footballdata.client.transfermarkt.jsoup;

import fr.cyrilcesco.footballdata.client.transfermarkt.model.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagePlayerTest {

    public static final String URL_TO_CONNECT = "https://www.transfermarkt.com/-/profil/spieler/315858";

    @Mock
    private JsoupClient jsoupClient;

    @Test
    void should_get_paris_saint_germain_players_when_we_call_page() throws IOException {
        File input = new File("src/test/resources/pagePlayer.html");
        Document doc = Jsoup.parse(input, "UTF-8", "https://www.transfermarkt.com/-/profil/spieler/315858");

        when(jsoupClient.getDocument(URL_TO_CONNECT)).thenReturn(doc);

        PagePlayer pagePlayer = new PagePlayer(jsoupClient, URL_TO_CONNECT);

        Player player = pagePlayer.getPlayer();
        assertEquals("goalkeeper", player.getMainPosition());
        assertEquals("right", player.getMainFoot());
        assertEquals("99", player.getShirtNumber());
        assertEquals("1999-02-25", player.getBirthDate());
        assertEquals("Castellammare di Stabia", player.getBirthPlace());
        assertEquals("Italy", player.getNationality());
        assertEquals("1,96", player.getHeight());
    }
}