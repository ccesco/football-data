package fr.cyrilcesco.footballdata.client.transfermarkt.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransfermarktSocketTimeOutTest {

    @Test
    void should_return_message_when_timeout_error(){
        assertEquals("Socket Time Out for id Ligue1", new TransfermarktSocketTimeOut("Ligue1").getMessage());
    }
}