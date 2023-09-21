package fr.cyrilcesco.footballdata.playersservice.mapper;

import fr.cyrilcesco.footballdata.playersservice.domain.model.MainFoot;
import fr.cyrilcesco.footballdata.playersservice.domain.model.MainPosition;
import fr.cyrilcesco.footballdata.playersservice.domain.model.Player;
import fr.cyrilcesco.footballdata.playersservice.model.PlayerDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDtoMapperTest {

    @Test
    void should_return_PlayerDto_from_Player() {
        Player player = new Player();
        player.setId("1");
        player.setLastName("test");
        player.setFirstName("toto");
        player.setMainFoot(MainFoot.LEFT);
        player.setMainPosition(MainPosition.GOAL_KEEPER);

        PlayerDto playerDto = new PlayerDtoMapper().mapFromDomain(player);

        assertEquals("toto", playerDto.getFirstName());
        assertEquals("test", playerDto.getLastName());
        assertEquals("1", playerDto.getId());
    }
}