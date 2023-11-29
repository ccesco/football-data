package fr.cyrilcesco.footballdata.competitionservice.config;

import fr.cyrilcesco.footballdata.competitionservice.domain.DomainCompetitionService;
import fr.cyrilcesco.footballdata.competitionservice.domain.model.Competition;
import fr.cyrilcesco.footballdata.competitionservice.domain.spi.CompetitionRepositoryPort;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
public class CompetitionServiceConfig {

    private final KafkaProperties kafkaProperties;

    private final CompetitionRepositoryPort competitionRepositoryPort;

    public CompetitionServiceConfig(KafkaProperties kafkaProperties, CompetitionRepositoryPort competitionRepositoryPort) {
        this.kafkaProperties = kafkaProperties;
        this.competitionRepositoryPort = competitionRepositoryPort;
    }

    @Bean
    public DomainCompetitionService domainTeamService() {
        return new DomainCompetitionService(competitionRepositoryPort);
    }

    @Bean
    public ConsumerFactory<String, Competition> createCompetitionConsumerFactory() {
        Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
        JsonDeserializer<Competition> deserializer = new JsonDeserializer<>(Competition.class, false);
        return new DefaultKafkaConsumerFactory<>(consumerProperties, new StringDeserializer(), deserializer);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Competition>> competitionKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Competition> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createCompetitionConsumerFactory());
        return factory;
    }
}
