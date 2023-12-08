package fr.cyrilcesco.footballdata.client.transfermarkt;

import fr.cyrilcesco.footballdata.client.transfermarkt.jsoup.JsoupClient;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.Player;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktTeamResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {
        JsoupClient.class
})
@AutoConfigureWebClient
class TeamPlayersServiceTestIT {

    @Autowired
    private JsoupClient jsoupClient;

    @Test
    void test() {
        TransfermarktTeamResponse team = new TeamService(jsoupClient).getTeamPlayersInformation("583", "2023");

        assertEquals("583", team.getId());
        assertEquals("2023", team.getSeasonYear());
        assertEquals(Player.builder().name("Gianluigi Donnarumma").id("315858").link("/gianluigi-donnarumma/profil/spieler/315858").build(), team.getPlayers().get(0));
        assertEquals(Player.builder().name("Arnau Tenas").id("466783").link("/arnau-tenas/profil/spieler/466783").build(), team.getPlayers().get(1));
        assertEquals(Player.builder().name("Keylor Navas").id("79422").link("/keylor-navas/profil/spieler/79422").build(), team.getPlayers().get(2));
    }

}