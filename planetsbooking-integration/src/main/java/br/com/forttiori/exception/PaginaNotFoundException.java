package br.com.forttiori.exception;

public class PaginaNotFound extends RuntimeException{
    public PaginaNotFound(String message) {
        super(message);
    }
}
