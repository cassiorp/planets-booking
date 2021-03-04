package br.com.forttiori;

import java.util.function.Supplier;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String message) {
        super(message);
    }

}
