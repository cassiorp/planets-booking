package br.com.forttiori.Exceptionhandlers;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class StandardError {

    private Integer status;
    private String message;
    private LocalDateTime dataHora;

}
