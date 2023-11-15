package fr.cyrilcesco.footballdata.initservice.competitions.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic createCompetitionTopic() {
        return TopicBuilder.name(TopicsName.INIT_COMPETITION)
                .partitions(5)
                .compact()
                .build();
    }

    @Bean
    public NewTopic createCompetitionEnrichedTopic() {
        return TopicBuilder.name(TopicsName.COMPETITION_ENRICHED)
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic createCompetitionEnrichedErrorTopic() {
        return TopicBuilder.name(TopicsName.COMPETITION_ENRICHED_ERROR)
                .partitions(1)
                .compact()
                .build();
    }

}