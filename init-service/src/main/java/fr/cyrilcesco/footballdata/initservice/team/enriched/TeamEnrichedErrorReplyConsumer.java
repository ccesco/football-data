package fr.cyrilcesco.footballdata.initservice.team.enriched;

import fr.cyrilcesco.footballdata.initservice.config.TopicsName;
import fr.cyrilcesco.footballdata.initservice.domain.model.Team;
import fr.cyrilcesco.footballdata.initservice.team.TeamInitProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TeamEnrichedErrorReplyConsumer {

    private final TeamInitProducer teamProducer;

    public TeamEnrichedErrorReplyConsumer(TeamInitProducer teamProducer) {
        this.teamProducer = teamProducer;
    }

    @KafkaListener(topics = TopicsName.TEAM_ENRICHED_ERROR, containerFactory= "teamKafkaListenerContainerFactory")
    public void listener(Team teamInError) {
        log.info("team in error reply in init {}", teamInError);
        teamProducer.sendMessage(teamInError);
    }
}
