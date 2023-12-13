package fr.cyrilcesco.footballdata.initservice.team;

import fr.cyrilcesco.footballdata.initservice.config.TopicsName;
import fr.cyrilcesco.footballdata.initservice.domain.model.Competition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TeamService {

    private final TeamInitProducer teamProducer;

    public TeamService(TeamInitProducer teamProducer) {
        this.teamProducer = teamProducer;
    }

    @KafkaListener(topics = TopicsName.COMPETITION_ENRICHED, containerFactory="competitionKafkaListenerContainerFactory")
    void listener(Competition competition) {
        competition.getTeams().forEach(teamProducer::sendMessage);
        log.info("teams added to topic init team for competition{}", competition);
    }
}
