package fr.cyrilcesco.footballdata.playersservice.domain;

import fr.cyrilcesco.footballdata.playersservice.domain.model.MainFoot;
import fr.cyrilcesco.footballdata.playersservice.domain.model.MainPosition;
import fr.cyrilcesco.footballdata.playersservice.domain.model.Player;
import fr.cyrilcesco.footballdata.playersservice.domain.spi.PlayerRepositoryPort;
import fr.cyrilcesco.footballdata.playersservice.domain.exception.PlayerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DomainPlayerServiceTest {

    private DomainPlayerService service;

    @Mock
    private PlayerRepositoryPort playerRepositoryPort;

    @BeforeEach
    void init() {
        service = new DomainPlayerService(playerRepositoryPort);
    }

    @Test
    void should_return_player_with_correct_id_when_get_with_specific_id() {
        when(playerRepositoryPort.getPlayer("1")).thenReturn(Optional.of(getPlayer("1")));

        Player player = service.getPlayer("1");
        assertEquals("toto", player.getFirstName());
        assertEquals("test", player.getLastName());
        assertEquals("1", player.getId());
        assertEquals(MainPosition.GOAL_KEEPER, player.getMainPosition());
        assertEquals(MainFoot.LEFT, player.getMainFoot());
    }

    @Test
    void should_throw_error_when_player_with_id_not_found() {
        when(playerRepositoryPort.getPlayer("1")).thenReturn(Optional.empty());

        PlayerNotFoundException playerNotFoundException = assertThrows(PlayerNotFoundException.class, () -> service.getPlayer("1"));
        assertEquals("Player with identifiant 1 not found", playerNotFoundException.getMessage());
    }

    @Test
    void should_return_top_10_player_when_number_players_is_not_enough() {
        when(playerRepositoryPort.getAllPlayers()).thenReturn(generatePlayers(5));
        List<Player> players = service.getTop10Players();
        assertEquals(5, players.size());
        assertEquals("0", players.get(0).getId());
        assertEquals("4", players.get(4).getId());
    }

    @Test
    void should_return_top_10_player_when_number_is_enough() {
        when(playerRepositoryPort.getAllPlayers()).thenReturn(generatePlayers(15));
        List<Player> players = service.getTop10Players();
        assertEquals(10, players.size());
        assertEquals("0", players.get(0).getId());
        assertEquals("9", players.get(9).getId());
    }

    private static Player getPlayer(String id) {
        Player player = new Player();
        player.setId(id);
        player.setLastName("test");
        player.setFirstName("toto");
        player.setMainFoot(MainFoot.LEFT);
        player.setMainPosition(MainPosition.GOAL_KEEPER);
        return player;
    }

    private static List<Player> generatePlayers(int numberPlayer) {
        List<Player> result = new ArrayList<>();
        for(int i=0; i < numberPlayer; i++) {
            result.add(getPlayer(String.valueOf(i)));
        }
        return result;
    }
}