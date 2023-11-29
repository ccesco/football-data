package fr.cyrilcesco.footballdata.competitionservice.infrastructure;

import fr.cyrilcesco.footballdata.competitionservice.config.TopicsName;
import fr.cyrilcesco.footballdata.competitionservice.domain.api.AddCompetitionUseCase;
import fr.cyrilcesco.footballdata.competitionservice.domain.model.Competition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CompetitionEnrichedConsumer {

    private final AddCompetitionUseCase addCompetitionUseCase;

    public CompetitionEnrichedConsumer(AddCompetitionUseCase addCompetitionUseCase) {
        this.addCompetitionUseCase = addCompetitionUseCase;
    }

    @KafkaListener(topics = TopicsName.COMPETITION_ENRICHED, containerFactory="competitionKafkaListenerContainerFactory")
    void listener(Competition competition) {
        Competition competitionAdded = addCompetitionUseCase.addCompetition(competition);
        log.info("Competition added to database {}", competitionAdded.toString());
    }
}
