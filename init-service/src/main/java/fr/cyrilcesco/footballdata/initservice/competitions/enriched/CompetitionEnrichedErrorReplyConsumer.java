package fr.cyrilcesco.footballdata.initservice.competitions.enriched;

import fr.cyrilcesco.footballdata.initservice.config.TopicsName;
import fr.cyrilcesco.footballdata.initservice.domain.model.Competition;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitCompetitionRequest;
import fr.cyrilcesco.footballdata.initservice.competitions.CompetitionProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CompetitionEnrichedErrorReplyConsumer {

    private final CompetitionProducer competitionProducer;

    public CompetitionEnrichedErrorReplyConsumer(CompetitionProducer competitionProducer) {
        this.competitionProducer = competitionProducer;
    }

    @KafkaListener(topics = TopicsName.COMPETITION_ENRICHED_ERROR, containerFactory= "competitionErrorReplyKafkaListenerContainerFactory")
    public void listener(Competition competitionInError) {
        log.info("competition in error reply in init {}", competitionInError);
        competitionProducer.sendMessage(InitCompetitionRequest.builder().competitionId(competitionInError.getId()).year(competitionInError.getSeasonYear()).build());
    }
}
