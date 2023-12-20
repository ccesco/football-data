package fr.cyrilcesco.footballdata.initservice.players.enriched;

import fr.cyrilcesco.footballdata.initservice.config.TopicsName;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitPlayerRequest;
import fr.cyrilcesco.footballdata.initservice.domain.model.Player;
import fr.cyrilcesco.footballdata.initservice.players.GetPlayerInformationsService;
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

import static fr.cyrilcesco.footballdata.initservice.players.enriched.PlayerEnrichmentStream.PLAYER_SERDES;
import static fr.cyrilcesco.footballdata.initservice.players.enriched.PlayerEnrichmentStream.STRING_SERDES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerEnrichmentStreamTest {

    private TopologyTestDriver topologyTestDriver = null;
    private TestInputTopic<String, InitPlayerRequest> initTeamTopic = null;
    private TestOutputTopic<String, Player> playerEnrichedTopic = null;
    private TestOutputTopic<String, Player> playerEnrichedErrorTopic = null;

    @Mock
    private GetPlayerInformationsService getPlayerInformationsService;

    PlayerEnrichmentStream playerEnrichmentStream;

    @BeforeEach
    void setUp() {

        playerEnrichmentStream = new PlayerEnrichmentStream(getPlayerInformationsService);

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        playerEnrichmentStream.createPlayersEnrichmentStream(streamsBuilder);
        topologyTestDriver = new TopologyTestDriver(streamsBuilder.build());

        initTeamTopic =
                topologyTestDriver.
                        createInputTopic(
                                TopicsName.INIT_PLAYER, STRING_SERDES.serializer(),
                                new JsonSerde<>(InitPlayerRequest.class).serializer());

        playerEnrichedTopic =
                topologyTestDriver.
                        createOutputTopic(
                                TopicsName.PLAYER_ENRICHED, STRING_SERDES.deserializer(),
                                PLAYER_SERDES.deserializer());

        playerEnrichedErrorTopic =
                topologyTestDriver.
                        createOutputTopic(
                                TopicsName.PLAYER_ENRICHED_ERROR, STRING_SERDES.deserializer(),
                                PLAYER_SERDES.deserializer());
    }

    @AfterEach
    void tearDown() {
        topologyTestDriver.close();
    }

    @Test
    void givenInputMessagesEnrichedTopic_whenProcessed_then_client_call_ok() {
        Player player = Player.builder().id("1111").name("TOTO").mainPosition("goalkeeper").build();
        when(getPlayerInformationsService.callClient(any())).thenReturn(player);

        initTeamTopic.pipeInput("1111", InitPlayerRequest.builder().playerId("1111").name("TOTO").build());

        Player playerFromTopic = playerEnrichedTopic.readValuesToList().get(0);
        assertEquals("TOTO", playerFromTopic.getName());
        assertEquals("1111", playerFromTopic.getId());
        assertEquals("goalkeeper", playerFromTopic.getMainPosition());
        assertTrue(playerEnrichedErrorTopic.readValuesToList().isEmpty());
    }


    @Test
    void givenInputMessagesErrorTopic_whenProcessed_then_client_call_ko() {
        when(getPlayerInformationsService.callClient(any())).thenReturn(null);

        initTeamTopic.pipeInput("1111", InitPlayerRequest.builder().playerId("1111").name("TOTO").build());

        assertFalse(playerEnrichedErrorTopic.readValuesToList().isEmpty());
        assertTrue(playerEnrichedTopic.readValuesToList().isEmpty());
    }

}