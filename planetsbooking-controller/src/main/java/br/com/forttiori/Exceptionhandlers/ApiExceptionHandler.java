package br.com.forttiori.Exceptionhandlers;

import br.com.forttiori.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@RequiredArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public StandardError userNaoEncontrado(UserNotFoundException e) {
        return StandardError.builder()
                .dataHora(LocalDateTime.now())
                .error(NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .status(NOT_FOUND.value())
                .build();
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ObjectError> errors = getErrors(ex);
        ErrorResponse errorResponse = getErrorResponse(status, errors);
        return new ResponseEntity<>(errorResponse, status);
    }


    private List<ObjectError> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ObjectError(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
                .collect(Collectors.toList());
    }

    private ErrorResponse getErrorResponse(HttpStatus status, List<ObjectError> errors) {
        StandardError erro = StandardError.builder()
                .dataHora(LocalDateTime.now())
                .status(status.value())
                .message("Campos Invalidos, Verifique e Tente Novamente")
                .error(status.getReasonPhrase())
                .build();

        return ErrorResponse.builder().error(erro).objectErrors(errors).build();
    }


}
