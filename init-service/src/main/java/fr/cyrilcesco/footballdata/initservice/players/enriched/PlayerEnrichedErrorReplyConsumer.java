
package fr.cyrilcesco.footballdata.initservice.players.enriched;

import fr.cyrilcesco.footballdata.initservice.config.TopicsName;
import fr.cyrilcesco.footballdata.initservice.domain.model.Player;
import fr.cyrilcesco.footballdata.initservice.players.PlayerInitProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlayerEnrichedErrorReplyConsumer {

    private final PlayerInitProducer playerProducer;

    public PlayerEnrichedErrorReplyConsumer(PlayerInitProducer playerProducer) {
        this.playerProducer = playerProducer;
    }

    @KafkaListener(topics = TopicsName.PLAYER_ENRICHED_ERROR, containerFactory= "playerKafkaListenerContainerFactory")
    public void listener(Player playerInError) {
        log.info("player in error reply in init {}", playerInError);
        playerProducer.sendMessage(playerInError);
    }
}
