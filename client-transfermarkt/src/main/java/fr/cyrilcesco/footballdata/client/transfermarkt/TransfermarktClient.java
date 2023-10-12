package fr.cyrilcesco.footballdata.client.transfermarkt;


import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktCompetitionResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TransfermarktClient {

    private final CompetitionService competitionService;

    public TransfermarktClient(@Qualifier("TransfermarktClientCompetitionService") CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    public TransfermarktCompetitionResponse getTeamsOfCompetition(String competitionId, String year) {
        return competitionService.getTeamsOfCompetition(competitionId, year);
    }
}
