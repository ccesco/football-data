package fr.cyrilcesco.footballdata.playersservice.domain.exception;

import fr.cyrilcesco.footballdata.playersservice.domain.exception.PlayerNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerNotFoundExceptionTest {

    @Test
    void should_return_message_error_when_created() {
        PlayerNotFoundException playerNotFoundException = new PlayerNotFoundException("123456");
        assertEquals("Player with identifiant 123456 not found", playerNotFoundException.getMessage());
    }

}