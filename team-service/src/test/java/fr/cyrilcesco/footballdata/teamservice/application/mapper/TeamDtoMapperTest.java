package fr.cyrilcesco.footballdata.teamservice.application.mapper;

import fr.cyrilcesco.footballdata.teamservice.domain.model.Team;
import fr.cyrilcesco.footballdata.teamservice.model.TeamDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TeamDtoMapperTest {

    private final TeamDtoMapper mapper = new TeamDtoMapperImpl();

    @Test
    void should_return_empty_when_team_empty() {
        Team team = new Team();
        TeamDto teamDto = mapper.mapFromDomain(team);

        assertNull(teamDto.getId());
        assertNull(teamDto.getName());
        assertNull(teamDto.getOfficialName());
        assertNull(teamDto.getStadiumName());
    }

    @Test
    void should_return_teamDto_when_team_is_not_empty() {
        Team team = new Team();
        team.setId("123");
        team.setName("TEAM");
        team.setOfficialName("TEAM FC");
        team.setStadiumName("STADIUM");

        TeamDto teamDto = mapper.mapFromDomain(team);

        assertEquals("123", teamDto.getId());
        assertEquals("TEAM", teamDto.getName());
        assertEquals("TEAM FC", teamDto.getOfficialName());
        assertEquals("STADIUM", teamDto.getStadiumName());
    }

}