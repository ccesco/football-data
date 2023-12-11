package fr.cyrilcesco.footballdata.initservice;

import fr.cyrilcesco.footballdata.initservice.competitions.config.InitCompetitionList;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitCompetitionRequest;
import fr.cyrilcesco.footballdata.initservice.competitions.CompetitionProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InitCompetitionService {

    private static final String SEARCH_YEAR = "2023";

    private final InitCompetitionList competitions;

    private final CompetitionProducer producer;

    public InitCompetitionService(InitCompetitionList competitions, CompetitionProducer producer) {
        this.competitions = competitions;
        this.producer = producer;
    }

    public List<InitCompetitionRequest> launchInitialisationFromCompetitionList() {
        log.info("Competition intitialisation launched");
        return competitions.getCompetitions().stream()
                .map(competitionId -> sendCompetitionIdOrReturnElementError(InitCompetitionRequest.builder().competitionId(competitionId).year(SEARCH_YEAR).build()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<InitCompetitionRequest> sendCompetitionIdOrReturnElementError(InitCompetitionRequest competitionRequest) {
        return producer.sendMessage(competitionRequest) ? Optional.empty() : Optional.of(competitionRequest);
    }
}
