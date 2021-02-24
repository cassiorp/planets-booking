package br.com.forttiori.Exceptionhandlers;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ErrorResponse {

    private StandardError error;

    private List<Campo> campos;

}
