package br.com.forttiori;

public class PlanetNotFoundException extends RuntimeException {
    public PlanetNotFoundException(String message) {
        super(message);
    }
}
