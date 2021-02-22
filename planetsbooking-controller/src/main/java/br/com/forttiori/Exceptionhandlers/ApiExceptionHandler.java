package br.com.forttiori.Exceptionhandlers;

import br.com.forttiori.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@ControllerAdvice
public class ApiExceptionHandler{

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandardError> paginaNaoEcontrada(RuntimeException e) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now().now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandardError> userNaoEncontrado(UserNotFoundException e) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now().now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

}
