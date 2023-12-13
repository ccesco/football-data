package fr.cyrilcesco.footballdata.initservice.team.enriched;

import fr.cyrilcesco.footballdata.initservice.domain.model.Team;
import fr.cyrilcesco.footballdata.initservice.team.TeamInitProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeamEnrichedErrorReplyConsumerTest {

    @Mock
    private TeamInitProducer teamInitProducer;


    @Test
    void should_reply_when_message_is_present() {
        Team team = Team.builder().id("581").seasonYear("2023").build();

        TeamEnrichedErrorReplyConsumer replyConsumer = new TeamEnrichedErrorReplyConsumer(teamInitProducer);
        replyConsumer.listener(team);

        verify(teamInitProducer).sendMessage(any());
    }
}