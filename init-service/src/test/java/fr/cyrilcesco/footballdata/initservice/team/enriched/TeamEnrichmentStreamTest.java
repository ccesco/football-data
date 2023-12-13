package fr.cyrilcesco.footballdata.initservice.team.enriched;

import fr.cyrilcesco.footballdata.initservice.config.TopicsName;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitTeamRequest;
import fr.cyrilcesco.footballdata.initservice.domain.model.Player;
import fr.cyrilcesco.footballdata.initservice.domain.model.Team;
import fr.cyrilcesco.footballdata.initservice.team.GetTeamInformationsService;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.List;

import static fr.cyrilcesco.footballdata.initservice.team.enriched.TeamEnrichmentStream.STRING_SERDES;
import static fr.cyrilcesco.footballdata.initservice.team.enriched.TeamEnrichmentStream.TEAM_SERDES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamEnrichmentStreamTest {

    private TopologyTestDriver topologyTestDriver = null;
    private TestInputTopic<String, InitTeamRequest> initTeamTopic = null;
    private TestOutputTopic<String, Team> teamEnrichedTopic = null;
    private TestOutputTopic<String, Team> teamEnrichedErrorTopic = null;

    @Mock
    private GetTeamInformationsService getTeamInformationsService;

    TeamEnrichmentStream teamEnrichmentStream;

    @BeforeEach
    void setUp() {

        teamEnrichmentStream = new TeamEnrichmentStream(getTeamInformationsService);

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        teamEnrichmentStream.createTeamsEnrichmentStream(streamsBuilder);
        topologyTestDriver = new TopologyTestDriver(streamsBuilder.build());

        initTeamTopic =
                topologyTestDriver.
                        createInputTopic(
                                TopicsName.INIT_TEAM, STRING_SERDES.serializer(),
                                new JsonSerde<>(InitTeamRequest.class).serializer());

        teamEnrichedTopic =
                topologyTestDriver.
                        createOutputTopic(
                                TopicsName.TEAM_ENRICHED, STRING_SERDES.deserializer(),
                                TEAM_SERDES.deserializer());

        teamEnrichedErrorTopic =
                topologyTestDriver.
                        createOutputTopic(
                                TopicsName.TEAM_ENRICHED_ERROR, STRING_SERDES.deserializer(),
                                TEAM_SERDES.deserializer());
    }

    @AfterEach
    void tearDown() {
        topologyTestDriver.close();
    }


    @Test
    void givenInputMessagesEnrichedTopic_whenProcessed_then_client_call_ok_with_teams_null() {
        when(getTeamInformationsService.callClient(any())).thenReturn(Team.builder().id("123").name("PSG").players(null).seasonYear("2023").build());

        initTeamTopic.pipeInput("583-2023", InitTeamRequest.builder().teamId("583").year("2023").build());

        Team team = teamEnrichedTopic.readValuesToList().get(0);
        assertEquals("PSG", team.getName());
        assertEquals("2023", team.getSeasonYear());
        assertEquals("123", team.getId());
        assertNull(team.getPlayers());
        assertTrue(teamEnrichedErrorTopic.readValuesToList().isEmpty());
    }

    @Test
    void givenInputMessagesEnrichedTopic_whenProcessed_then_client_call_ok_with_teams() {
        Player player = Player.builder().id("1111").name("TOTO").build();
        Team team = Team.builder().id("583").seasonYear("2023").players(List.of(player)).name("Paris Saint Germain").build();
        when(getTeamInformationsService.callClient(any())).thenReturn(team);

        initTeamTopic.pipeInput("583-2023", InitTeamRequest.builder().teamId("583").year("2023").build());

        Team teamFromTopic = teamEnrichedTopic.readValuesToList().get(0);
        assertEquals("Paris Saint Germain", teamFromTopic.getName());
        assertEquals("2023", teamFromTopic.getSeasonYear());
        assertEquals("583", teamFromTopic.getId());
        assertEquals(player, teamFromTopic.getPlayers().get(0));
        assertTrue(teamEnrichedErrorTopic.readValuesToList().isEmpty());
    }


    @Test
    void givenInputMessagesErrorTopic_whenProcessed_then_client_call_ko() {
        when(getTeamInformationsService.callClient(any())).thenReturn(null);

        initTeamTopic.pipeInput("583-2023", InitTeamRequest.builder().teamId("583").year("2023").build());

        assertFalse(teamEnrichedErrorTopic.readValuesToList().isEmpty());
        assertTrue(teamEnrichedTopic.readValuesToList().isEmpty());
    }

}