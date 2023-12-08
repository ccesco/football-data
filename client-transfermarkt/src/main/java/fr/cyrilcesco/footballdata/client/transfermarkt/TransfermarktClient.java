package fr.cyrilcesco.footballdata.client.transfermarkt;


import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktSocketTimeOut;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktCompetitionResponse;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktTeamResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TransfermarktClient {

    private final CompetitionService competitionService;
    private final TeamService teamService;

    public TransfermarktClient(@Qualifier("TransfermarktClientCompetitionService") CompetitionService competitionService, @Qualifier("TransfermarktClientTeamService") TeamService teamService) {
        this.competitionService = competitionService;
        this.teamService = teamService;
    }

    public TransfermarktCompetitionResponse getTeamsOfCompetition(String competitionId, String year) throws TransfermarktSocketTimeOut {
        return competitionService.getTeamsOfCompetition(competitionId, year);
    }


    public TransfermarktTeamResponse getTeamsPlayersInformations(String teamId, String seasonYear) throws TransfermarktSocketTimeOut {
        return teamService.getTeamPlayersInformation(teamId, seasonYear);
    }
}
