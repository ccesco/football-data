package fr.cyrilcesco.footballdata.initservice.team.config;

import fr.cyrilcesco.footballdata.initservice.domain.model.Team;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.NUM_STREAM_THREADS_CONFIG;

@Configuration
public class TeamEnrichedConfig {

    public static final String STREAM_TEAM_NAME = "STREAM_TEAM";

    private final KafkaProperties kafkaProperties;


    public TeamEnrichedConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    private ConsumerFactory<String, Team> createTeamConsumerFactory() {
        Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
        return new DefaultKafkaConsumerFactory<>(consumerProperties, new StringDeserializer(), new JsonDeserializer<>(Team.class, false));
    }

    @Bean("teamKafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Team>> teamKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Team> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createTeamConsumerFactory());
        return factory;
    }

    public KafkaStreamsConfiguration kafkaTeamEnrichedStreamConfig(final String bootstrapServers,
                                                                   final String applicationId) {
        Map<String, Object> props = new HashMap<>();
        props.put(APPLICATION_ID_CONFIG, applicationId);
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(NUM_STREAM_THREADS_CONFIG, 5);
        return new KafkaStreamsConfiguration(props);
    }

    @Bean(name = STREAM_TEAM_NAME)
    public StreamsBuilderFactoryBean teamKafkaStreamsBuilder(@Value("${spring.kafka.streams.bootstrap-servers}") final String bootstrapServers,
                                                             @Value("${spring.kafka.streams.team-application-id}") final String applicationId) {
        return new StreamsBuilderFactoryBean(kafkaTeamEnrichedStreamConfig(bootstrapServers, applicationId));
    }
}
