package fr.cyrilcesco.footballdata.initservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cyrilcesco.footballdata.initservice.error.SendMessageError;
import fr.cyrilcesco.footballdata.initservice.model.InitCompetitionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class CompetitionProducer {

    private final String competitionTopic;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public CompetitionProducer(@Value("${spring.kafka.topic.name}") String competitionTopic, ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.competitionTopic = competitionTopic;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean sendMessage(InitCompetitionRequest competitionRequest) {
        String dataToSend = getDataToSend(competitionRequest);

        String recordId = competitionRequest.getCompetitionId() + "-" + competitionRequest.getYear();
        try {
            SendResult<String, String> result = kafkaTemplate
                    .send(competitionTopic, recordId, dataToSend)
                    .get(5, TimeUnit.SECONDS);
            onSuccess(result);
            return true;
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            onFailure(recordId, e);
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
        log.warn("Unable to write Competition {} to topic {}.", recordId, competitionTopic, t);
    }

}