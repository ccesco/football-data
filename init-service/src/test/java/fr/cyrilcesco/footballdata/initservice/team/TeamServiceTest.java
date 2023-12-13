package fr.cyrilcesco.footballdata.initservice.team;

import fr.cyrilcesco.footballdata.initservice.domain.model.Competition;
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
class TeamServiceTest {

    @Mock
    private TeamInitProducer teamProducer;

    @Test
    void should_send_all_players_of_team() {
        Player player1 = Player.builder().id("1111").name("TOTO").build();
        Player player2 = Player.builder().id("2222").name("TEST").build();
        Team team1 = Team.builder().id("583").seasonYear("2023").players(List.of(player1)).name("Paris Saint Germain").build();
        Team team2 = Team.builder().id("741").seasonYear("2023").players(List.of(player2)).name("Lens").build();
        Competition competitionToReply = Competition.builder().id("FR1").teams(List.of(team1, team2)).seasonYear("2023").build();

        TeamService teamService = new TeamService(teamProducer);
        teamService.listener(competitionToReply);

        verify(teamProducer, times(1)).sendMessage(team1);
        verify(teamProducer, times(1)).sendMessage(team2);
    }
}