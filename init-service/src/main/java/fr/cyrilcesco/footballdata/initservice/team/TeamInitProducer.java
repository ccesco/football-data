package fr.cyrilcesco.footballdata.initservice.team;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cyrilcesco.footballdata.initservice.config.TopicsName;
import fr.cyrilcesco.footballdata.initservice.domain.error.SendMessageError;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitTeamRequest;
import fr.cyrilcesco.footballdata.initservice.domain.model.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class TeamInitProducer {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public TeamInitProducer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean sendMessage(Team team) {
        InitTeamRequest request = InitTeamRequest.builder().teamId(team.getId()).year(team.getSeasonYear()).build();

        String dataToSend = getDataToSend(request);

        String recordId = request.getTeamId() +"-"+ request.getYear();
        try {
            SendResult<String, String> result = kafkaTemplate
                    .send(TopicsName.INIT_TEAM, recordId, dataToSend)
                    .get(5, TimeUnit.SECONDS);
            onSuccess(result);
            return true;
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            onFailure(recordId, e);
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private String getDataToSend(InitTeamRequest teamRequest) throws SendMessageError {
        try {
            return objectMapper.writeValueAsString(teamRequest);
        } catch (JsonProcessingException e) {
            log.error("Cannot write team as String {}", teamRequest);
            throw new SendMessageError(teamRequest.toString());
        }

    }

    private void onSuccess(final SendResult<String, String> result) {
        log.info("Team '{}' has been written to topic-partition {}-{} with ingestion timestamp {}.",
                result.getProducerRecord().key(),
                result.getRecordMetadata().topic(),
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().timestamp());
    }

    private void onFailure(String recordId, final Throwable t) {
        log.warn("Unable to write Team {} to topic {}.", recordId, TopicsName.INIT_TEAM, t);
    }

}