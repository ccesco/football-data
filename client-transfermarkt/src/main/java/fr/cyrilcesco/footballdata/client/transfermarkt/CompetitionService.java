package fr.cyrilcesco.footballdata.client.transfermarkt;

import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktSocketTimeOut;
import fr.cyrilcesco.footballdata.client.transfermarkt.jsoup.JsoupClient;
import fr.cyrilcesco.footballdata.client.transfermarkt.jsoup.PageCompetition;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.Team;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktCompetitionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.MessageFormat;
import java.util.List;

@Service("TransfermarktClientCompetitionService")
public class CompetitionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompetitionService.class.getName());

    private static final String COMPETITION_URL = "https://www.transfermarkt.com/-/startseite/wettbewerb/{competition_id}";
    private static final String SUFFIX_SEASON_YEAR = "/plus/?saison_id={year}";

    private final JsoupClient jsoupClient;

    public CompetitionService(JsoupClient jsoupClient) {
        this.jsoupClient = jsoupClient;
    }

    public TransfermarktCompetitionResponse getTeamsOfCompetition(String competitionId, String year) {
        LOGGER.info(MessageFormat.format("GET teams of competition {0} with year {1}", competitionId, year));

        String urlToConnect = COMPETITION_URL.replace("{competition_id}", competitionId);
        String urlToConnectSuffix = SUFFIX_SEASON_YEAR.replace("{year}", year);

        try {
            PageCompetition page = jsoupClient.getDocument(urlToConnect, urlToConnectSuffix);

            List<Team> teams = page.getTeams();
            String competitionName = page.getCompetitionName();

            return getResponse(competitionId, year, teams, competitionName);
        } catch (SocketTimeoutException e) {
            throw new TransfermarktSocketTimeOut(competitionId);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
