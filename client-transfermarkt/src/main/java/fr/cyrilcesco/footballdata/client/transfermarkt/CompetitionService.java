package fr.cyrilcesco.footballdata.client.transfermarkt;

import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktErrorParsing;
import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktSocketTimeOut;
import fr.cyrilcesco.footballdata.client.transfermarkt.jsoup.JsoupClient;
import fr.cyrilcesco.footballdata.client.transfermarkt.jsoup.PageCompetition;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.Team;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktCompetitionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

@Service("TransfermarktClientCompetitionService")
@Slf4j
public class CompetitionService {

    private static final String COMPETITION_URL = "https://www.transfermarkt.com/-/startseite/wettbewerb/{competition_id}";
    private static final String SUFFIX_SEASON_YEAR = "/plus/?saison_id={year}";

    private final JsoupClient jsoupClient;

    public CompetitionService(JsoupClient jsoupClient) {
        this.jsoupClient = jsoupClient;
    }

    public TransfermarktCompetitionResponse getTeamsOfCompetition(String competitionId, String year) throws TransfermarktSocketTimeOut {
        log.info("GET teams of competition {} with year {}", competitionId, year);

        String urlToConnect = COMPETITION_URL.replace("{competition_id}", competitionId);
        String urlToConnectSuffix = SUFFIX_SEASON_YEAR.replace("{year}", year);

        try {
            PageCompetition page = new PageCompetition(jsoupClient, urlToConnect, urlToConnectSuffix);

            List<Team> teams = page.getTeams();
            String competitionName = page.getCompetitionName();

            return getResponse(competitionId, year, teams, competitionName);
        } catch (SocketTimeoutException e) {
            throw new TransfermarktSocketTimeOut(competitionId);
        } catch (IOException e) {
            throw new TransfermarktErrorParsing(competitionId);
        }
    }

    private TransfermarktCompetitionResponse getResponse(String competitionId, String year, List<Team> teams, String competitionName) {
        return TransfermarktCompetitionResponse.builder()
                .id(competitionId)
                .name(competitionName)
                .seasonYear(year)
                .teams(teams)
                .build();
    }


}
