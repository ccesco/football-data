package fr.cyrilcesco.footballdata.initservice.players.enriched;

import fr.cyrilcesco.footballdata.initservice.domain.model.Player;
import fr.cyrilcesco.footballdata.initservice.players.PlayerInitProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PlayerEnrichedErrorReplyConsumerTest {

    @Mock
    private PlayerInitProducer playerInitProducer;


    @Test
    void should_reply_when_message_is_present() {
        Player player = Player.builder().id("4444").name("toto").mainPosition("goalkeeper").build();

        PlayerEnrichedErrorReplyConsumer replyConsumer = new PlayerEnrichedErrorReplyConsumer(playerInitProducer);
        replyConsumer.listener(player);

        verify(playerInitProducer).sendMessage(any());
    }

}