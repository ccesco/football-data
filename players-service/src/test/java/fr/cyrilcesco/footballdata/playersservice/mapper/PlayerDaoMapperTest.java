package fr.cyrilcesco.footballdata.playersservice.mapper;

import fr.cyrilcesco.footballdata.playersservice.domain.model.Player;
import fr.cyrilcesco.footballdata.playersservice.repository.entity.PlayerDao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDaoMapperTest {

    @Test
    void should_return_Player_from_PlayerDao() {
        PlayerDao playerDao = PlayerDao
                .builder()
                .id(1)
                .firstName("test")
                .lastName("toto")
                .build();

        Player player = new PlayerDaoMapper().mapToDomain(playerDao);

        assertEquals("test", player.getFirstName());
        assertEquals("toto", player.getLastName());
        assertEquals("1", player.getId());
    }
}