package fr.cyrilcesco.footballdata.initservice.team;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cyrilcesco.footballdata.initservice.config.TopicsName;
import fr.cyrilcesco.footballdata.initservice.domain.error.SendMessageError;
import fr.cyrilcesco.footballdata.initservice.domain.model.Player;
import fr.cyrilcesco.footballdata.initservice.domain.model.Team;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamInitProducerTest {

    @Mock
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private TeamInitProducer producer;

    private Team team;

    @BeforeEach
    void init() {
        Player player = Player.builder().id("1111").name("TOTO").build();
        team = Team.builder().id("583").seasonYear("2023").players(List.of(player)).name("Paris Saint Germain").build();
    }

    @Test
    void should_send_message_with_good_record_id() {
        when(kafkaTemplate.send(eq(TopicsName.INIT_TEAM), eq("583-2023"), any())).thenReturn(CompletableFuture.completedFuture(new SendResult<>(new ProducerRecord<>("", ""), new RecordMetadata(new TopicPartition("", 0), 0, 0, 0, 0, 0))));
        producer = new TeamInitProducer(objectMapper, kafkaTemplate);

        boolean result = producer.sendMessage(team);
        assertTrue(result);
    }

    @Test
    void should_get_error_if_not_serializable() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenThrow(new JsonMappingException("ERROR", new Throwable()));
        producer = new TeamInitProducer(objectMapper, kafkaTemplate);

        assertThrows(SendMessageError.class, () -> producer.sendMessage(team));
    }

    @Test
    void should_get_error_if_not_sended() {
        when(kafkaTemplate.send(eq(TopicsName.INIT_TEAM), eq("583-2023"), any())).thenReturn(CompletableFuture.failedFuture(new RuntimeException("Oops")));
        producer = new TeamInitProducer(objectMapper, kafkaTemplate);

        boolean result = producer.sendMessage(team);
        assertFalse(result);
    }

    @Test
    void should_get_error_timeout() {
        when(kafkaTemplate.send(eq(TopicsName.INIT_TEAM), eq("583-2023"), any())).thenReturn(new CompletableFuture<>());
        producer = new TeamInitProducer(objectMapper, kafkaTemplate);

        boolean result = producer.sendMessage(team);
        assertFalse(result);
    }
}