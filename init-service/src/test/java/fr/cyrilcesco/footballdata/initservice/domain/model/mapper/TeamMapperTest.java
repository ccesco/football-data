package fr.cyrilcesco.footballdata.initservice.domain.model.mapper;

import fr.cyrilcesco.footballdata.client.transfermarkt.model.TransfermarktTeamResponse;
import fr.cyrilcesco.footballdata.client.transfermarkt.model.Player;
import fr.cyrilcesco.footballdata.initservice.domain.model.Team;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TeamMapperTest {

    private final TeamMapper mapper = new TeamMapperImpl();


    @Test
    void should_return_empty_when_response_empty() {
        TransfermarktTeamResponse teamResponse = TransfermarktTeamResponse.builder().build();
        Team team = mapper.mapFromClientResponse(teamResponse);

        assertNull(team.getId());
        assertNull(team.getName());
        assertNull(team.getPlayers());
        assertNull(team.getInformationsLink());
        assertNull(team.getSeasonYear());
    }

    @Test
    void should_return_team_with_players_empty_when_response_have_no_players() {
        TransfermarktTeamResponse teamResponse = TransfermarktTeamResponse.builder().name("PSG").id("123").seasonYear("2023").build();
        Team team = mapper.mapFromClientResponse(teamResponse);

        assertEquals("123", team.getId());
        assertEquals("PSG", team.getName());
        assertNull(team.getPlayers());
        assertNull(team.getInformationsLink());
        assertEquals("2023", team.getSeasonYear());
    }

    @Test
    void should_return_team_with_players_when_response_have_players() {
        Player player = Player.builder().id("1111").name("TOTO").build();
        TransfermarktTeamResponse teamResponse = TransfermarktTeamResponse.builder().name("PSG").id("123").players(List.of(player)).seasonYear("2023").build();
        Team team = mapper.mapFromClientResponse(teamResponse);

        assertEquals("123", team.getId());
        assertEquals("PSG", team.getName());
        assertEquals("2023", team.getSeasonYear());

        fr.cyrilcesco.footballdata.initservice.domain.model.Player player1 = team.getPlayers().get(0);
        assertEquals("1111", player1.getId());
        assertEquals("TOTO",player1.getName());
    }
}