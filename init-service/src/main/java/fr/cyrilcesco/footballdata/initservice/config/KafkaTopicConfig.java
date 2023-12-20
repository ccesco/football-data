package fr.cyrilcesco.footballdata.initservice.config;

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

    @Bean
    public NewTopic createTeamTopic() {
        return TopicBuilder.name(TopicsName.INIT_TEAM)
                .partitions(5)
                .compact()
                .build();
    }

    @Bean
    public NewTopic createTeamEnrichedTopic() {
        return TopicBuilder.name(TopicsName.TEAM_ENRICHED)
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic createTeamEnrichedErrorTopic() {
        return TopicBuilder.name(TopicsName.TEAM_ENRICHED_ERROR)
                .partitions(1)
                .compact()
                .build();
    }

    @Bean
    public NewTopic createPlayerTopic() {
        return TopicBuilder.name(TopicsName.INIT_PLAYER)
                .partitions(5)
                .compact()
                .build();
    }

    @Bean
    public NewTopic createPlayerEnrichedTopic() {
        return TopicBuilder.name(TopicsName.PLAYER_ENRICHED)
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic createPlayerEnrichedErrorTopic() {
        return TopicBuilder.name(TopicsName.PLAYER_ENRICHED_ERROR)
                .partitions(1)
                .compact()
                .build();
    }
}