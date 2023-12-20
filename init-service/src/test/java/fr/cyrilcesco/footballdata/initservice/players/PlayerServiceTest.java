package fr.cyrilcesco.footballdata.initservice.players;

import fr.cyrilcesco.footballdata.initservice.domain.model.Player;
import fr.cyrilcesco.footballdata.initservice.domain.model.Team;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {


    @Mock
    private PlayerInitProducer playerInitProducer;

    @Test
    void should_send_all_players_of_team() {
        Player player1 = Player.builder().id("1111").name("TOTO").build();
        Player player2 = Player.builder().id("2222").name("TEST").build();
        Team team = Team.builder().id("583").seasonYear("2023").players(List.of(player1, player2)).name("Paris Saint Germain").build();

        PlayerService playerService = new PlayerService(playerInitProducer);
        playerService.listener(team);

        verify(playerInitProducer, times(1)).sendMessage(player1);
        verify(playerInitProducer, times(1)).sendMessage(player2);
    }
}