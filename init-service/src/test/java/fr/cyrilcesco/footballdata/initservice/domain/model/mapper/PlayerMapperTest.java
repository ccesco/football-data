package fr.cyrilcesco.footballdata.initservice.domain.model.mapper;

import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktPlayerResponse;
import fr.cyrilcesco.footballdata.initservice.domain.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlayerMapperTest {

    private final PlayerMapper mapper = new PlayerMapperImpl();

    @Test
    void should_return_empty_when_response_empty() {
        TransfermarktPlayerResponse playerResponse = TransfermarktPlayerResponse.builder().build();
        Player player = mapper.mapFromClientResponse(playerResponse);

        assertNull(player.getId());
        assertNull(player.getName());
        assertNull(player.getMainPosition());
        assertNull(player.getMainFoot());
        assertNull(player.getHeight());
        assertNull(player.getShirtNumber());
        assertNull(player.getBirthDate());
        assertNull(player.getBirthPlace());
        assertNull(player.getNationality());
    }

    @Test
    void should_player_mapped_when_response_not_empty() {
        Player player = mapper.mapFromClientResponse(getTransfermarktPlayerResponse());

        assertEquals("1111", player.getId());
        assertEquals("toto", player.getName());
        assertEquals("goalkeeper", player.getMainPosition());
        assertEquals("right", player.getMainFoot());
        assertEquals("1,96", player.getHeight());
        assertEquals("99", player.getShirtNumber());
        assertEquals("1999-02-25", player.getBirthDate());
        assertEquals("Castellammare di Stabia", player.getBirthPlace());
        assertEquals("Italy", player.getNationality());
    }

    private static TransfermarktPlayerResponse getTransfermarktPlayerResponse() {
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