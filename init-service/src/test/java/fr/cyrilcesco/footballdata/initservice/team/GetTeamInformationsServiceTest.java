package fr.cyrilcesco.footballdata.initservice.team;

import fr.cyrilcesco.footballdata.client.transfermarkt.TransfermarktClient;
import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktSocketTimeOut;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.Player;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktTeamResponse;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitTeamRequest;
import fr.cyrilcesco.footballdata.initservice.domain.model.Team;
import fr.cyrilcesco.footballdata.initservice.domain.model.mapper.TeamMapper;
import fr.cyrilcesco.footballdata.initservice.domain.model.mapper.TeamMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTeamInformationsServiceTest {

    @Mock
    private TransfermarktClient transfermarktClient;

    private final TeamMapper mapper = new TeamMapperImpl();

    @Test
    void should_get_team_without_timeout() {
        List<Player> players = List.of(Player.builder().name("toto").id("1111").build(), Player.builder().id("2222").name("titi").build());
        TransfermarktTeamResponse teamResponse = TransfermarktTeamResponse.builder().id("581").seasonYear("2023").name("PSG").players(players).build();
        when(transfermarktClient.getTeamsPlayersInformations("581", "2022")).thenReturn(teamResponse);
        GetTeamInformationsService service = new GetTeamInformationsService(transfermarktClient, mapper);

        Team team = service.callClient(InitTeamRequest.builder().teamId("581").year("2022").build());

        assertEquals("581", team.getId());
        assertEquals("2023", team.getSeasonYear());
        assertEquals("PSG", team.getName());

        fr.cyrilcesco.footballdata.initservice.domain.model.Player player1 = team.getPlayers().get(0);
        assertEquals("toto", player1.getName());
        assertEquals("1111", player1.getId());

        fr.cyrilcesco.footballdata.initservice.domain.model.Player player2 = team.getPlayers().get(1);
        assertEquals("titi", player2.getName());
        assertEquals("2222", player2.getId());
    }

    @Test
    void should_get_team_when_call_client_timeout() {
        when(transfermarktClient.getTeamsPlayersInformations("581", "2023")).thenThrow(new TransfermarktSocketTimeOut("FR1"));

        GetTeamInformationsService service = new GetTeamInformationsService(transfermarktClient, mapper);

        Team team = service.callClient(InitTeamRequest.builder().teamId("581").year("2023").build());

        assertEquals("581", team.getId());
        assertEquals("2023", team.getSeasonYear());
    }
}