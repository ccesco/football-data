package fr.cyrilcesco.footballdata.initservice.competitions.enriched;

import fr.cyrilcesco.footballdata.initservice.competitions.GetCompetitionInformationsService;
import fr.cyrilcesco.footballdata.initservice.config.TopicsName;
import fr.cyrilcesco.footballdata.initservice.domain.model.Competition;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitCompetitionRequest;
import fr.cyrilcesco.footballdata.initservice.domain.model.Player;
import fr.cyrilcesco.footballdata.initservice.domain.model.Team;
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

import java.util.List;

import static fr.cyrilcesco.footballdata.initservice.competitions.common.CommonSerdes.COMPETITION_SERDES;
import static fr.cyrilcesco.footballdata.initservice.competitions.common.CommonSerdes.INIT_COMPETITION_SERDES;
import static fr.cyrilcesco.footballdata.initservice.competitions.common.CommonSerdes.STRING_SERDES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompetitionsEnrichmentStreamTest {

    private TopologyTestDriver topologyTestDriver = null;
    private TestInputTopic<String, InitCompetitionRequest> initCompetitionTopic = null;
    private TestOutputTopic<String, Competition> competitionEnrichedTopic = null;
    private TestOutputTopic<String, Competition> competitionEnrichedErrorTopic = null;

    @Mock
    private GetCompetitionInformationsService getCompetitionInformationsService;

    CompetitionsEnrichmentStream competitionsEnrichmentStream;

    @BeforeEach
    void setUp() {

        competitionsEnrichmentStream = new CompetitionsEnrichmentStream(getCompetitionInformationsService);

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        competitionsEnrichmentStream.createCompetitionsEnrichmentStream(streamsBuilder);
        topologyTestDriver = new TopologyTestDriver(streamsBuilder.build());

        initCompetitionTopic =
                topologyTestDriver.
                        createInputTopic(
                                TopicsName.INIT_COMPETITION, STRING_SERDES.serializer(),
                                INIT_COMPETITION_SERDES.serializer());

        competitionEnrichedTopic =
                topologyTestDriver.
                        createOutputTopic(
                                TopicsName.COMPETITION_ENRICHED, STRING_SERDES.deserializer(),
                                COMPETITION_SERDES.deserializer());

        competitionEnrichedErrorTopic =
                topologyTestDriver.
                        createOutputTopic(
                                TopicsName.COMPETITION_ENRICHED_ERROR, STRING_SERDES.deserializer(),
                                COMPETITION_SERDES.deserializer());


    }

    @AfterEach
    void tearDown() {
        topologyTestDriver.close();
    }


    @Test
    void givenInputMessagesEnrichedTopic_whenProcessed_then_client_call_ok_with_teams_null() {
        when(getCompetitionInformationsService.callClient(any())).thenReturn(Competition.builder().id("FR1").name("Ligue 1").teams(null).seasonYear("2023").build());

        initCompetitionTopic.pipeInput("FR1-2023", InitCompetitionRequest.builder().competitionId("FR1").competitionId("2023").build());

        Competition competition = competitionEnrichedTopic.readValuesToList().get(0);
        assertEquals("Ligue 1", competition.getName());
        assertEquals("2023", competition.getSeasonYear());
        assertEquals("FR1", competition.getId());
        assertNull(competition.getTeams());
        assertTrue(competitionEnrichedErrorTopic.readValuesToList().isEmpty());
    }

    @Test
    void givenInputMessagesEnrichedTopic_whenProcessed_then_client_call_ok_with_teams() {
        Player player = Player.builder().id("1111").name("TOTO").build();
        Team team = Team.builder().id("583").seasonYear("2023").players(List.of(player)).name("Paris Saint Germain").build();
        when(getCompetitionInformationsService.callClient(any())).thenReturn(Competition.builder().id("FR1").name("Ligue 1").teams(List.of(team)).seasonYear("2023").build());

        initCompetitionTopic.pipeInput("FR1-2023", InitCompetitionRequest.builder().competitionId("FR1").competitionId("2023").build());

        Competition competition = competitionEnrichedTopic.readValuesToList().get(0);
        assertEquals("Ligue 1", competition.getName());
        assertEquals("2023", competition.getSeasonYear());
        assertEquals("FR1", competition.getId());
        assertEquals(team, competition.getTeams().get(0));
        assertTrue(competitionEnrichedErrorTopic.readValuesToList().isEmpty());
    }


    @Test
    void givenInputMessagesErrorTopic_whenProcessed_then_client_call_ko() {
        when(getCompetitionInformationsService.callClient(any())).thenReturn(null);

        initCompetitionTopic.pipeInput("FR1-2023", InitCompetitionRequest.builder().competitionId("FR1").competitionId("2023").build());

        assertFalse(competitionEnrichedErrorTopic.readValuesToList().isEmpty());
        assertTrue(competitionEnrichedTopic.readValuesToList().isEmpty());
    }

}