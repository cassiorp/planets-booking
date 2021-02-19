package br.com.forttiori;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String nome;

    private String email;

    private String senha;

    private List<Reservation> reservations;

    @Builder
    public User(String id,String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.reservations = new ArrayList<>();
    }

    public void setReservations(Reservation reservation) {
        this.reservations.add(reservation);
    }
}
