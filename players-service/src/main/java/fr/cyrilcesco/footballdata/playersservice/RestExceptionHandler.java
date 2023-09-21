package fr.cyrilcesco.footballdata.playersservice;

import fr.cyrilcesco.footballdata.playersservice.domain.exception.PlayerNotFoundException;
import fr.cyrilcesco.footballdata.playersservice.model.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ErrorDto> handleApplicationServiceException(PlayerNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto().title("NOT_FOUND").status(HttpStatus.NOT_FOUND.value()).detail(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

}