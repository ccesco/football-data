package fr.cyrilcesco.footballdata.client.transfermarkt.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransfermarktErrorParsingTest {

    @Test
    void should_return_message_when_parsing_error(){
        assertEquals("Error parsing ligue1", new TransfermarktErrorParsing("ligue1").getMessage());
    }

}