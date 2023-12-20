package fr.cyrilcesco.footballdata.initservice.players;

import fr.cyrilcesco.footballdata.initservice.config.TopicsName;
import fr.cyrilcesco.footballdata.initservice.domain.model.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlayerService {

    private final PlayerInitProducer playerProducer;

    public PlayerService(PlayerInitProducer playerProducer) {
        this.playerProducer = playerProducer;
    }

    @KafkaListener(topics = TopicsName.TEAM_ENRICHED, containerFactory = "teamKafkaListenerContainerFactory")
    void listener(Team team) {
        team.getPlayers().forEach(playerProducer::sendMessage);
        log.info("players added to topic init player for team{}", team);
    }
}
