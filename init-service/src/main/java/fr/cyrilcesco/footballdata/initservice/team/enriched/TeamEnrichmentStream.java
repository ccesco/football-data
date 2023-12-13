package fr.cyrilcesco.footballdata.initservice.team.enriched;

import fr.cyrilcesco.footballdata.initservice.config.TopicsName;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitTeamRequest;
import fr.cyrilcesco.footballdata.initservice.domain.model.Team;
import fr.cyrilcesco.footballdata.initservice.team.GetTeamInformationsService;
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

import static fr.cyrilcesco.footballdata.initservice.team.config.TeamEnrichedConfig.STREAM_TEAM_NAME;

@Configuration
@Slf4j
public class TeamEnrichmentStream {

    public static final Serde<String> STRING_SERDES = Serdes.String();
    public static final JsonSerde<Team> TEAM_SERDES = new JsonSerde<>(Team.class);

    public static final String BRANCHES = "branches-";
    private final GetTeamInformationsService getTeamInformationsService;

    public TeamEnrichmentStream(GetTeamInformationsService getTeamInformationsService) {
        this.getTeamInformationsService = getTeamInformationsService;
    }

    @Bean
    public Map<String, KStream<String, Team>> createTeamsEnrichmentStream(@Qualifier(STREAM_TEAM_NAME) StreamsBuilder streamsBuilder) {
        Map<String, KStream<String, Team>> streamEnriched = streamsBuilder.stream(TopicsName.INIT_TEAM, Consumed.with(Serdes.String(), new JsonSerde<>(InitTeamRequest.class)))
                .mapValues((readOnlyKey, request) -> getTeamInformationsService.callClient(request))
                .split(Named.as(BRANCHES)) // split the stream
                .branch((key, team) -> isTeamEnriched(team), Branched.as(TopicsName.TEAM_ENRICHED))
                .defaultBranch(Branched.as(TopicsName.TEAM_ENRICHED_ERROR));

        streamEnriched.get(BRANCHES + TopicsName.TEAM_ENRICHED_ERROR)
                .to(TopicsName.TEAM_ENRICHED_ERROR, Produced.with(STRING_SERDES, TEAM_SERDES));

        streamEnriched.get(BRANCHES + TopicsName.TEAM_ENRICHED)
                .to(TopicsName.TEAM_ENRICHED, Produced.with(STRING_SERDES, TEAM_SERDES));

        return streamEnriched;
    }

    private static boolean isTeamEnriched(Team team) {
        return Objects.nonNull(team) && Objects.nonNull(team.getName());
    }
}
