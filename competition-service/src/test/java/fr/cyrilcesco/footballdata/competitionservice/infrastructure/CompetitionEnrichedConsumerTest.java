package fr.cyrilcesco.footballdata.competitionservice.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.cyrilcesco.footballdata.competitionservice.config.TopicsName;
import fr.cyrilcesco.footballdata.competitionservice.domain.api.AddCompetitionUseCase;
import fr.cyrilcesco.footballdata.competitionservice.domain.model.Competition;
import fr.cyrilcesco.footballdata.competitionservice.domain.model.Team;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(topics = TopicsName.COMPETITION_ENRICHED, bootstrapServersProperty = "spring.kafka.consumer.bootstrap-servers")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CompetitionEnrichedConsumerTest {

    private Producer<String, Object> producer;

    @SpyBean
    private CompetitionEnrichedConsumer competitionEnrichedConsumer;

    @SpyBean
    private AddCompetitionUseCase addCompetitionUseCase;

    @Captor
    ArgumentCaptor<Competition> competitionArgumentCaptor;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @BeforeAll
    void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new JsonSerializer<>()).createProducer();
    }

    @Test
    void testLogKafkaMessages() throws JsonProcessingException {
        Team team = Team.builder().id("583").name("Paris Saint Germain").build();
        Competition competitionToSend = Competition.builder().id("FR1").name("Ligue 1").teams(List.of(team)).seasonYear("2023").build();
        producer.send(new ProducerRecord<>(TopicsName.COMPETITION_ENRICHED, 0, "FR1-2023", competitionToSend));
        producer.flush();

        // Read the message and assert its properties
        verify(competitionEnrichedConsumer, timeout(5000).times(1)).listener(competitionArgumentCaptor.capture());

        Competition competition = competitionArgumentCaptor.getValue();
        assertEquals("FR1", competition.getId());
        assertEquals("2023", competition.getSeasonYear());
        assertEquals("Ligue 1", competition.getName());
        assertEquals(List.of(team), competition.getTeams());
        verify(addCompetitionUseCase).addCompetition(competition);
    }

    @AfterAll
    void shutdown() {
        producer.close();
    }
}