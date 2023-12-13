package fr.cyrilcesco.footballdata.initservice.competitions.enriched;

import fr.cyrilcesco.footballdata.initservice.competitions.CompetitionProducer;
import fr.cyrilcesco.footballdata.initservice.domain.model.Competition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompetitionEnrichedErrorReplyConsumerTest {

    @Mock
    private CompetitionProducer competitionProducer;


    @Test
    void should_reply_when_message_is_present() {
        Competition competitionToReply = Competition.builder().id("FR1").seasonYear("2023").build();

        CompetitionEnrichedErrorReplyConsumer replyConsumer = new CompetitionEnrichedErrorReplyConsumer(competitionProducer);
        replyConsumer.listener(competitionToReply);

        verify(competitionProducer).sendMessage(any());
    }
}