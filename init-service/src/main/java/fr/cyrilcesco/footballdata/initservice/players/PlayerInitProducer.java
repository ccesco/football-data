package fr.cyrilcesco.footballdata.initservice.players;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cyrilcesco.footballdata.initservice.config.TopicsName;
import fr.cyrilcesco.footballdata.initservice.domain.error.SendMessageError;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitPlayerRequest;
import fr.cyrilcesco.footballdata.initservice.domain.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class PlayerInitProducer {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PlayerInitProducer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean sendMessage(Player player) {
        InitPlayerRequest request = InitPlayerRequest.builder().playerId(player.getId()).name(player.getName()).build();

        String dataToSend = getDataToSend(request);
        String recordId = request.getPlayerId();

        try {
            SendResult<String, String> result = kafkaTemplate
                    .send(TopicsName.INIT_PLAYER, recordId, dataToSend)
                    .get(5, TimeUnit.SECONDS);
            onSuccess(result);
            return true;
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            onFailure(recordId, e);
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private String getDataToSend(InitPlayerRequest playerRequest) throws SendMessageError {
        try {
            return objectMapper.writeValueAsString(playerRequest);
        } catch (JsonProcessingException e) {
            log.error("Cannot write player as String {}", playerRequest);
            throw new SendMessageError(playerRequest.toString());
        }

    }

    private void onSuccess(final SendResult<String, String> result) {
        log.info("Player '{}' has been written to topic-partition {}-{} with ingestion timestamp {}.",
                result.getProducerRecord().key(),
                result.getRecordMetadata().topic(),
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().timestamp());
    }

    private void onFailure(String recordId, final Throwable t) {
        log.warn("Unable to write Player {} to topic {}.", recordId, TopicsName.INIT_PLAYER, t);
    }

}