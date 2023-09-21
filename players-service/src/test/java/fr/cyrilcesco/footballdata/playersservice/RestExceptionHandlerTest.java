package fr.cyrilcesco.footballdata.playersservice;

import fr.cyrilcesco.footballdata.playersservice.domain.exception.PlayerNotFoundException;
import fr.cyrilcesco.footballdata.playersservice.model.ErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class RestExceptionHandlerTest {

    private RestExceptionHandler restExceptionHandler = new RestExceptionHandler();

    @Test
    void should_return_not_found_when_player_not_found() {
        ResponseEntity<ErrorDto> errorDtoResponseEntity = restExceptionHandler.handleApplicationServiceException(new PlayerNotFoundException("123"));

        assertEquals(HttpStatusCode.valueOf(404), errorDtoResponseEntity.getStatusCode());
        assertEquals(new ErrorDto().title("NOT_FOUND").status(404).detail("Player with identifiant 123 not found"), errorDtoResponseEntity.getBody());
    }
}