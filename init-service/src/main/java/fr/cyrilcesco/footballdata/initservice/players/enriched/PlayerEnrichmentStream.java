package fr.cyrilcesco.footballdata.initservice.players.enriched;

import fr.cyrilcesco.footballdata.initservice.config.TopicsName;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitPlayerRequest;
import fr.cyrilcesco.footballdata.initservice.domain.model.Player;
import fr.cyrilcesco.footballdata.initservice.players.GetPlayerInformationsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Map;
import java.util.Objects;

import static fr.cyrilcesco.footballdata.initservice.players.config.PlayerEnrichedConfig.STREAM_PLAYER_NAME;

@Configuration
@Slf4j
public class PlayerEnrichmentStream {

    public static final Serde<String> STRING_SERDES = Serdes.String();
    public static final JsonSerde<Player> PLAYER_SERDES = new JsonSerde<>(Player.class);

    public static final String BRANCHES = "branches-";
    private final GetPlayerInformationsService getPlayerInformationsService;

    public PlayerEnrichmentStream(GetPlayerInformationsService getPlayerInformationsService) {
        this.getPlayerInformationsService = getPlayerInformationsService;
    }

    @Bean
    public Map<String, KStream<String, Player>> createPlayersEnrichmentStream(@Qualifier(STREAM_PLAYER_NAME) StreamsBuilder streamsBuilder) {
        Map<String, KStream<String, Player>> streamEnriched = streamsBuilder.stream(TopicsName.INIT_PLAYER, Consumed.with(Serdes.String(), new JsonSerde<>(InitPlayerRequest.class)))
                .mapValues((readOnlyKey, request) -> getPlayerInformationsService.callClient(request))
                .split(Named.as(BRANCHES)) // split the stream
                .branch((key, player) -> isPlayerEnriched(player), Branched.as(TopicsName.PLAYER_ENRICHED))
                .defaultBranch(Branched.as(TopicsName.PLAYER_ENRICHED_ERROR));

        streamEnriched.get(BRANCHES + TopicsName.PLAYER_ENRICHED_ERROR)
                .to(TopicsName.PLAYER_ENRICHED_ERROR, Produced.with(STRING_SERDES, PLAYER_SERDES));

        streamEnriched.get(BRANCHES + TopicsName.PLAYER_ENRICHED)
                .to(TopicsName.PLAYER_ENRICHED, Produced.with(STRING_SERDES, PLAYER_SERDES));

        return streamEnriched;
    }

    private static boolean isPlayerEnriched(Player player) {
        return Objects.nonNull(player) && Objects.nonNull(player.getMainPosition());
    }
}
