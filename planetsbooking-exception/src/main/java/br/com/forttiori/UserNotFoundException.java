package br.com.forttiori;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

}
