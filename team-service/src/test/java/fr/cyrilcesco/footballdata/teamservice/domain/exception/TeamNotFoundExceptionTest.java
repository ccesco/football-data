package fr.cyrilcesco.footballdata.teamservice.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamNotFoundExceptionTest {


    @Test
    void should_return_message_error_when_created() {
        TeamNotFoundException teamNotFoundException = new TeamNotFoundException("123456");
        assertEquals("Team with identifiant 123456 not found", teamNotFoundException.getMessage());
    }
}