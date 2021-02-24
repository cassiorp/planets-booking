package br.com.forttiori.Exceptionhandlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Campo {
    private String campo;
    private String mensagem;
}
