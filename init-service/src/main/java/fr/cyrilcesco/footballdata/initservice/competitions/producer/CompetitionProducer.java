package fr.cyrilcesco.footballdata.initservice.competitions.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cyrilcesco.footballdata.initservice.competitions.config.TopicsName;
import fr.cyrilcesco.footballdata.initservice.competitions.error.SendMessageError;
import fr.cyrilcesco.footballdata.initservice.competitions.model.InitCompetitionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class CompetitionProducer {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public CompetitionProducer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean sendMessage(InitCompetitionRequest competitionRequest) {
        String dataToSend = getDataToSend(competitionRequest);

        String recordId = competitionRequest.getCompetitionId() + "-" + competitionRequest.getYear();
        try {
            SendResult<String, String> result = kafkaTemplate
                    .send(TopicsName.INIT_COMPETITION, recordId, dataToSend)
                    .get(5, TimeUnit.SECONDS);
            onSuccess(result);
            return true;
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            onFailure(recordId, e);
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private String getDataToSend(InitCompetitionRequest competitionRequest) throws SendMessageError {
        try {
            return objectMapper.writeValueAsString(competitionRequest);
        } catch (JsonProcessingException e) {
            log.error("Cannot write as String {}", competitionRequest);
            throw new SendMessageError(competitionRequest.toString());
        }

    }

    private void onSuccess(final SendResult<String, String> result) {
        log.info("Competition '{}' has been written to topic-partition {}-{} with ingestion timestamp {}.",
                result.getProducerRecord().key(),
                result.getRecordMetadata().topic(),
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().timestamp());
    }

    private void onFailure(String recordId, final Throwable t) {
        log.warn("Unable to write Competition {} to topic {}.", recordId, TopicsName.INIT_COMPETITION, t);
    }

}