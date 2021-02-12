package com.forttiori.Optional;

import java.util.Optional;

public class MainExemple {

    public static void main(String[] args) {

        Pessoa pessoa = new Pessoa(null, Optional.of("00100200365"));

        Optional<Pessoa> optionalPessoa = Optional.ofNullable(pessoa);

        String nome = optionalPessoa.map(Pessoa::getNome).orElse("Nome Padrao");

        System.out.println(nome);

        nome = optionalPessoa.map(Pessoa::getNome).orElseGet(() -> "Nome padrao get");
        System.out.println(nome);

        String cpf = optionalPessoa.flatMap(Pessoa::getCpf).orElse("Cpf padrao");

        Optional<Pessoa> pessoa2 = optionalPessoa.filter(p -> p.getNome().equals("Cássio"));
        
        pessoa2.ifPresent(p -> System.out.println(p.getNome()));

    }
}
