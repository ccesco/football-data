package fr.cyrilcesco.footballdata.playersservice.service;

import fr.cyrilcesco.footballdata.playersservice.domain.api.GetPlayerUseCase;
import fr.cyrilcesco.footballdata.playersservice.domain.model.MainFoot;
import fr.cyrilcesco.footballdata.playersservice.domain.model.MainPosition;
import fr.cyrilcesco.footballdata.playersservice.domain.model.Player;
import fr.cyrilcesco.footballdata.playersservice.mapper.PlayerDtoMapper;
import fr.cyrilcesco.footballdata.playersservice.model.PlayerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private GetPlayerUseCase getPlayerUseCase;

    private PlayerService playerService;

    @BeforeEach
    void init() {
        playerService = new PlayerService(getPlayerUseCase, new PlayerDtoMapper());
    }

    @Test
    void should_return_player_with_specific_id() {
        when(getPlayerUseCase.getPlayer("2")).thenReturn(getPlayer());
        ResponseEntity<PlayerDto> playerDtoResponseEntity = playerService.playersIdGet("2");

        PlayerDto playerDto = new PlayerDto();
        playerDto.setId("1");
        playerDto.setFirstName("toto");
        playerDto.setLastName("test");

        assertEquals(playerDto, playerDtoResponseEntity.getBody());
        assertEquals(HttpStatusCode.valueOf(200), playerDtoResponseEntity.getStatusCode());
    }

    private static Player getPlayer() {
        Player player = new Player();
        player.setId("1");
        player.setLastName("test");
        player.setFirstName("toto");
        player.setMainFoot(MainFoot.LEFT);
        player.setMainPosition(MainPosition.GOAL_KEEPER);
        return player;
    }

}