package fr.cyrilcesco.footballdata.playersservice.service;

import fr.cyrilcesco.footballdata.playersservice.domain.model.Player;
import fr.cyrilcesco.footballdata.playersservice.mapper.PlayerDaoMapper;
import fr.cyrilcesco.footballdata.playersservice.repository.SpringPlayerRepository;
import fr.cyrilcesco.footballdata.playersservice.repository.entity.PlayerDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerRepositoryTest {

    private PlayerRepository playerRepository;
    @Mock
    private SpringPlayerRepository springPlayerRepository;

    @BeforeEach
    void init(){
        playerRepository = new PlayerRepository(springPlayerRepository, new PlayerDaoMapper());
    }

    @Test
    void should_return_all_players() {
        when(springPlayerRepository.findAll()).thenReturn(generatePlayers(3));

        List<Player> allPlayers = playerRepository.getAllPlayers();
        assertEquals(3, allPlayers.size());
        assertEquals("0", allPlayers.get(0).getId());
        assertEquals("2", allPlayers.get(2).getId());
    }

    @Test
    void should_return_player_with_specific_id_when_get_with_id() {
        when(springPlayerRepository.findById(1L)).thenReturn(getPlayerDao(1));

        Player player = playerRepository.getPlayer("1").get();
        assertEquals("toto", player.getFirstName());
        assertEquals("test", player.getLastName());
        assertEquals("1", player.getId());

    }

    @Test
    void should_throw_error_when_player_not_found() {
        when(springPlayerRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Player> player = playerRepository.getPlayer("1");
        assertEquals(Optional.empty(), player);
    }

    private static Optional<PlayerDao> getPlayerDao(int id) {
        PlayerDao player = new PlayerDao();
        player.setId(id);
        player.setLastName("test");
        player.setFirstName("toto");
        return Optional.of(player);
    }

    private static List<PlayerDao> generatePlayers(int numberPlayer) {
        List<PlayerDao> result = new ArrayList<>();
        for(int i=0; i < numberPlayer; i++) {
            result.add(getPlayerDao(i).get());
        }
        return result;
    }
}