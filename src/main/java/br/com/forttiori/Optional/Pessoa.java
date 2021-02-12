package com.forttiori.Optional;


import java.util.Optional;

public class Pessoa {

    private String nome;
    private Optional<String> cpf;

    public Pessoa(String nome, Optional<String> cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public Optional<String> getCpf() {
        return cpf;
    }


}
