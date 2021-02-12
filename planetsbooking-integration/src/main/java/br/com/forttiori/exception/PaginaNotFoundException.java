package br.com.forttiori.exception;

public class PaginaNotFoundException extends RuntimeException{
    public PaginaNotFoundException(String message) {
        super(message);
    }
}
