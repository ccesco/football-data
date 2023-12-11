package fr.cyrilcesco.footballdata.initservice.competitions.config;

import fr.cyrilcesco.footballdata.client.transfermarkt.TransferMarktClientConfiguration;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;

@Import({TransferMarktClientConfiguration.class})
@Configuration
@EnableKafka
public class InitCompetitionsConfig {

    public static final String STREAM_COMPETITION_NAME = "STREAM_COMPETITION";
    private final KafkaProperties kafkaProperties;

    public InitCompetitionsConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    private ProducerFactory<String, String> producerFactory() {
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

    public KafkaStreamsConfiguration kafkaCompetitionEnrichedStreamConfig(final String bootstrapServers,
                                                                          final String applicationId) {
        Map<String, Object> props = new HashMap<>();
        props.put(APPLICATION_ID_CONFIG, applicationId);
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaStreamsConfiguration(props);
    }

    @Bean(name = STREAM_COMPETITION_NAME)
    public StreamsBuilderFactoryBean app1KafkaStreamsBuilder(@Value("${spring.kafka.streams.bootstrap-servers}") final String bootstrapServers,
                                                             @Value("${spring.kafka.streams.competition-application-id}") final String applicationId) {
        return new StreamsBuilderFactoryBean(kafkaCompetitionEnrichedStreamConfig(bootstrapServers, applicationId));
    }
}