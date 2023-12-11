package fr.cyrilcesco.footballdata.initservice.competitions.config;

import fr.cyrilcesco.footballdata.client.transfermarkt.TransferMarktClientConfiguration;
import fr.cyrilcesco.footballdata.initservice.domain.model.Competition;
import fr.cyrilcesco.footballdata.initservice.domain.model.InitCompetitionRequest;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Import({TransferMarktClientConfiguration.class})
@Configuration
public class CompetitionsEnrichedReplyConfig {

    private final KafkaProperties kafkaProperties;

    public CompetitionsEnrichedReplyConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    private ConsumerFactory<String, Competition> createCompetitionReplyConsumerFactory() {
        Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
        return new DefaultKafkaConsumerFactory<>(consumerProperties, new StringDeserializer(), new JsonDeserializer<>(Competition.class));
    }

    private ProducerFactory<String, InitCompetitionRequest> producerCompetitionReplyFactory() {
        Map<String, Object> producerProperties = kafkaProperties.buildProducerProperties();
        producerProperties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "all");
        producerProperties.put(ProducerConfig.RETRIES_CONFIG, 3);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(producerProperties);
    }

    @Bean
    public KafkaTemplate<String, InitCompetitionRequest> kafkaCompetitionReplyTemplate() {
        return new KafkaTemplate<>(producerCompetitionReplyFactory());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Competition>> competitionErrorReplyKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Competition> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createCompetitionReplyConsumerFactory());
        factory.setReplyTemplate(kafkaCompetitionReplyTemplate());
        return factory;
    }
}