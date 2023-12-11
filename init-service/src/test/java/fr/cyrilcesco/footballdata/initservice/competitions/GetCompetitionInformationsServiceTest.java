package fr.cyrilcesco.footballdata.initservice.competitions;

import fr.cyrilcesco.footballdata.client.transfermarkt.TransfermarktClient;
import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktSocketTimeOut;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.Team;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktCompetitionResponse;
import fr.cyrilcesco.footballdata.initservice.domain.model.Competition;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitCompetitionRequest;
import fr.cyrilcesco.footballdata.initservice.domain.model.mapper.CompetitionMapper;
import fr.cyrilcesco.footballdata.initservice.domain.model.mapper.CompetitionMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCompetitionInformationsServiceTest {

    @Mock
    private TransfermarktClient transfermarktClient;

    private final CompetitionMapper mapper = new CompetitionMapperImpl();

    @Test
    void should_get_competition_without_timeout() {
        List<Team> teams = List.of(Team.builder().name("PSG").seasonYear("2023").link("lien_psg").id("1").build(), Team.builder().id("2").link("lien_lens").seasonYear("2023").name("Lens").build());
        TransfermarktCompetitionResponse competitionResponse = TransfermarktCompetitionResponse.builder().id("FR1").seasonYear("2023").name("Ligue 1").teams(teams).build();
        when(transfermarktClient.getTeamsOfCompetition("FR1", "2023")).thenReturn(competitionResponse);
        GetCompetitionInformationsService service = new GetCompetitionInformationsService(transfermarktClient, mapper);

        Competition competition = service.callClient(InitCompetitionRequest.builder().competitionId("FR1").year("2023").build());

        assertEquals("FR1", competition.getId());
        assertEquals("2023", competition.getSeasonYear());
        assertEquals("Ligue 1", competition.getName());

        assertEquals("PSG", competition.getTeams().get(0).getName());
        assertEquals("1", competition.getTeams().get(0).getId());
        assertEquals("2023", competition.getTeams().get(0).getSeasonYear());
        assertEquals("lien_psg", competition.getTeams().get(0).getInformationsLink());

        assertEquals("Lens", competition.getTeams().get(1).getName());
        assertEquals("2", competition.getTeams().get(1).getId());
        assertEquals("2023", competition.getTeams().get(1).getSeasonYear());
        assertEquals("lien_lens", competition.getTeams().get(1).getInformationsLink());
    }

    @Test
    void should_get_light_competition_when_call_client_timeout() {
        when(transfermarktClient.getTeamsOfCompetition("FR1", "2023")).thenThrow(new TransfermarktSocketTimeOut("FR1"));

        GetCompetitionInformationsService service = new GetCompetitionInformationsService(transfermarktClient, mapper);

        Competition competition = service.callClient(InitCompetitionRequest.builder().competitionId("FR1").year("2023").build());

        assertEquals("FR1", competition.getId());
        assertEquals("2023", competition.getSeasonYear());
    }

}