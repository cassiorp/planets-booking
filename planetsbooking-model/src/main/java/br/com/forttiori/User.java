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

    private List<Reservation> reservations;

    @Builder
    public User(String id,String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.reservations = new ArrayList<>();
    }

    public void setReservations(Reservation reservation) {
        this.reservations.add(reservation);
    }
}
