package fr.cyrilcesco.footballdata.initservice.competitions.config;

import fr.cyrilcesco.footballdata.client.transfermarkt.TransferMarktClientConfiguration;
import fr.cyrilcesco.footballdata.initservice.competitions.model.Competition;
import fr.cyrilcesco.footballdata.initservice.competitions.model.InitCompetitionRequest;
import io.swagger.v3.oas.models.OpenAPI;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;

@Import({TransferMarktClientConfiguration.class})
@Configuration
@EnableKafka
@EnableKafkaStreams
public class InitCompetitionsConfig {

    private final KafkaProperties kafkaProperties;

    public InitCompetitionsConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public OpenAPI initServiceOpenAPI() {
        return new OpenAPI();
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> producerProperties = kafkaProperties.buildProducerProperties();
        producerProperties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "all");
        producerProperties.put(ProducerConfig.RETRIES_CONFIG, 3);
        return new DefaultKafkaProducerFactory<>(producerProperties);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfig(@Value("${spring.kafka.streams.bootstrap-servers}") final String bootstrapServers,
                                                        @Value("${spring.kafka.streams.application-id}") final String applicationId) {
        Map<String, Object> props = new HashMap<>();
        props.put(APPLICATION_ID_CONFIG, applicationId);
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public ConsumerFactory<String, Competition> createCompetitionReplyConsumerFactory() {
        Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
        return new DefaultKafkaConsumerFactory<>(consumerProperties, new StringDeserializer(), new JsonDeserializer<>(Competition.class));
    }

    @Bean
    public ProducerFactory<String, InitCompetitionRequest> producerCompetitionReplyFactory() {
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