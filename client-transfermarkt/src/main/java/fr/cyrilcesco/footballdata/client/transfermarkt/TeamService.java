package fr.cyrilcesco.footballdata.client.transfermarkt;

import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktErrorParsing;
import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktSocketTimeOut;
import fr.cyrilcesco.footballdata.client.transfermarkt.jsoup.JsoupClient;
import fr.cyrilcesco.footballdata.client.transfermarkt.jsoup.PageTeamPlayers;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.Player;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktTeamResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

@Service("TransfermarktClientTeamService")
@Slf4j
public class TeamService {

    private static final String TEAM_PLAYERS_INFORMATIONS = "https://www.transfermarkt.com/-/kader/verein/{team_id}/saison_id/{season_year}/plus/1";

    private final JsoupClient jsoupClient;

    public TeamService(JsoupClient jsoupClient) {
        this.jsoupClient = jsoupClient;
    }

    public TransfermarktTeamResponse getTeamPlayersInformation(String teamId, String seasonYear) throws TransfermarktSocketTimeOut {
        log.info("GET teams {}", teamId);

        String urlToConnect = TEAM_PLAYERS_INFORMATIONS.replace("{team_id}", teamId);
        urlToConnect = urlToConnect.replace("{season_year}", seasonYear);

        try {
            List<Player> players = new PageTeamPlayers(jsoupClient, urlToConnect).getPlayers();
            String teamName = new PageTeamPlayers(jsoupClient, urlToConnect).getTeamName();

            return getResponse(teamId, teamName, seasonYear, players);
        } catch (SocketTimeoutException e) {
            throw new TransfermarktSocketTimeOut(teamId);
        } catch (IOException e) {
            throw new TransfermarktErrorParsing(teamId);
        }
    }

    private TransfermarktTeamResponse getResponse(String teamId, String name, String seasonYear, List<Player> players) {
        return TransfermarktTeamResponse.builder()
                .id(teamId)
                .name(name)
                .seasonYear(seasonYear)
                .players(players)
                .build();
    }


}

