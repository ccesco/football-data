package fr.cyrilcesco.footballdata.initservice.competitions.stream;

import fr.cyrilcesco.footballdata.initservice.competitions.GetCompetitionInformationsService;
import fr.cyrilcesco.footballdata.initservice.competitions.config.TopicsName;
import fr.cyrilcesco.footballdata.initservice.competitions.model.Competition;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Objects;

import static fr.cyrilcesco.footballdata.initservice.competitions.common.CommonSerdes.COMPETITION_SERDES;
import static fr.cyrilcesco.footballdata.initservice.competitions.common.CommonSerdes.INIT_COMPETITION_SERDES;
import static fr.cyrilcesco.footballdata.initservice.competitions.common.CommonSerdes.STRING_SERDES;

@Configuration
@Slf4j
public class CompetitionsEnrichmentStream {

    public static final String BRANCHES = "branches-";
    private final GetCompetitionInformationsService getCompetitionInformationsService;

    public CompetitionsEnrichmentStream(GetCompetitionInformationsService getCompetitionInformationsService) {
        this.getCompetitionInformationsService = getCompetitionInformationsService;
    }

    @Bean
    public Map<String, KStream<String, Competition>> createCompetitionsEnrichmentStream(StreamsBuilder streamsBuilder) {
        Map<String, KStream<String, Competition>> streamEnriched = streamsBuilder.stream(TopicsName.INIT_COMPETITION, Consumed.with(Serdes.String(), INIT_COMPETITION_SERDES))
                .mapValues((readOnlyKey, request) -> getCompetitionInformationsService.callClient(request))
                .split(Named.as(BRANCHES)) // split the stream
                .branch((key, competition) -> Objects.nonNull(competition), Branched.as(TopicsName.COMPETITION_ENRICHED))
                .defaultBranch(Branched.as(TopicsName.COMPETITION_ENRICHED_ERROR));

        streamEnriched.get(BRANCHES + TopicsName.COMPETITION_ENRICHED_ERROR)
                .to(TopicsName.COMPETITION_ENRICHED_ERROR, Produced.with(STRING_SERDES, COMPETITION_SERDES));

        streamEnriched.get(BRANCHES + TopicsName.COMPETITION_ENRICHED)
                .to(TopicsName.COMPETITION_ENRICHED, Produced.with(STRING_SERDES, COMPETITION_SERDES));

        return streamEnriched;
    }
}
