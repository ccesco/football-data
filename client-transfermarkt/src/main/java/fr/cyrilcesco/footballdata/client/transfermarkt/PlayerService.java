package fr.cyrilcesco.footballdata.client.transfermarkt;

import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktErrorParsing;
import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktSocketTimeOut;
import fr.cyrilcesco.footballdata.client.transfermarkt.jsoup.JsoupClient;
import fr.cyrilcesco.footballdata.client.transfermarkt.jsoup.PagePlayer;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.Player;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktPlayerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;

@Service("TransfermarktClientPlayerService")
@Slf4j
public class PlayerService {

    private static final String PLAYER_INFORMATIONS = "https://www.transfermarkt.com/-/profil/spieler/{player_id}";

    private final JsoupClient jsoupClient;

    public PlayerService(JsoupClient jsoupClient) {
        this.jsoupClient = jsoupClient;
    }

    public TransfermarktPlayerResponse getPlayerInformation(String playerId, String name) throws TransfermarktSocketTimeOut {
        log.info("GET player {}", playerId);

        String urlToConnect = PLAYER_INFORMATIONS.replace("{player_id}", playerId);

        try {
            Player player = new PagePlayer(jsoupClient, urlToConnect).getPlayer();
            return getResponse(playerId, name, player);
        } catch (SocketTimeoutException e) {
            throw new TransfermarktSocketTimeOut(playerId);
        } catch (IOException e) {
            throw new TransfermarktErrorParsing(playerId);
        }
    }

    private TransfermarktPlayerResponse getResponse(String playerId, String name, Player player) {
        return TransfermarktPlayerResponse.builder()
                .id(playerId)
                .name(name)
                .mainPosition(player.getMainPosition())
                .mainFoot(player.getMainFoot())
                .shirtNumber(player.getShirtNumber())
                .birthDate(player.getBirthDate())
                .birthPlace(player.getBirthPlace())
                .nationality(player.getNationality())
                .height(player.getHeight())
                .build();
    }


}

