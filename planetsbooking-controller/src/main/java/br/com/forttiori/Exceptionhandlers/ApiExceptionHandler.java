package br.com.forttiori.Exceptionhandlers;

import br.com.forttiori.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Campo> campos = new ArrayList<>();
        List<ObjectError> resultsErrors = ex.getBindingResult().getAllErrors();
        resultsErrors.forEach(error ->
                campos.add(
                        new Campo(((FieldError) error).getField(), messageSource.getMessage(error, LocaleContextHolder.getLocale()))
                )
        );

        StandardError erro = StandardError.builder()
                .dataHora(LocalDateTime.now())
                .status(status.value())
                .message("Campos Invalidos, Verifique e Tente Novamente")
                .error(status.getReasonPhrase())
                .build();

        ErrorResponse errorResponse = ErrorResponse.builder().error(erro).campos(campos).build();
        return ResponseEntity.status(status).body(errorResponse);
    }


    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public StandardError userNaoEncontrado(UserNotFoundException e) {
        return StandardError.builder()
                .dataHora(LocalDateTime.now())
                .message(e.getMessage())
                .status(NOT_FOUND.value())
                .error(NOT_FOUND.getReasonPhrase())
                .build();
    }

}
