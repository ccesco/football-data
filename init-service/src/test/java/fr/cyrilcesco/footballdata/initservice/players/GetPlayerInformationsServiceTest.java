package fr.cyrilcesco.footballdata.initservice.players;

import fr.cyrilcesco.footballdata.client.transfermarkt.TransfermarktClient;
import fr.cyrilcesco.footballdata.client.transfermarkt.exception.TransfermarktSocketTimeOut;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktPlayerResponse;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitPlayerRequest;
import fr.cyrilcesco.footballdata.initservice.domain.model.Player;
import fr.cyrilcesco.footballdata.initservice.domain.model.mapper.PlayerMapper;
import fr.cyrilcesco.footballdata.initservice.domain.model.mapper.PlayerMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPlayerInformationsServiceTest {

    @Mock
    private TransfermarktClient transfermarktClient;

    private final PlayerMapper mapper = new PlayerMapperImpl();

    @Test
    void should_get_player_without_timeout() {
        TransfermarktPlayerResponse playerResponse = getPlayerResponse();
        when(transfermarktClient.getPlayerInformations("1111", "toto")).thenReturn(playerResponse);
        GetPlayerInformationsService service = new GetPlayerInformationsService(transfermarktClient, mapper);

        Player player = service.callClient(InitPlayerRequest.builder().playerId("1111").name("toto").build());

        assertEquals("1111", player.getId());
        assertEquals("toto", player.getName());
        assertEquals("goalkeeper", player.getMainPosition());
        assertEquals("right", player.getMainFoot());
        assertEquals("99", player.getShirtNumber());
        assertEquals("1999-02-25", player.getBirthDate());
        assertEquals("Castellammare di Stabia", player.getBirthPlace());
        assertEquals("Italy", player.getNationality());
        assertEquals("1,96", player.getHeight());
    }

    @Test
    void should_get_player_when_call_client_timeout() {
        when(transfermarktClient.getPlayerInformations("1111", "TOTO")).thenThrow(new TransfermarktSocketTimeOut("FR1"));

        GetPlayerInformationsService service = new GetPlayerInformationsService(transfermarktClient, mapper);

        Player player = service.callClient(InitPlayerRequest.builder().playerId("1111").name("TOTO").build());

        assertEquals("1111", player.getId());
        assertEquals("TOTO", player.getName());
        assertNull(player.getMainPosition());
        assertNull(player.getMainFoot());
        assertNull(player.getShirtNumber());
        assertNull(player.getBirthDate());
        assertNull(player.getBirthPlace());
        assertNull(player.getNationality());
        assertNull(player.getHeight());
    }

    private static TransfermarktPlayerResponse getPlayerResponse() {
        return TransfermarktPlayerResponse
                .builder()
                .id("1111")
                .mainPosition("goalkeeper")
                .mainFoot("right")
                .name("toto")
                .shirtNumber("99")
                .birthDate("1999-02-25")
                .birthPlace("Castellammare di Stabia")
                .nationality("Italy")
                .height("1,96")
                .build();
    }
}