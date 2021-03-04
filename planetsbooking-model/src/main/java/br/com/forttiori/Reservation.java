package br.com.forttiori;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Data
@Document(collection = "reservations")
@NoArgsConstructor
public class Reservation {

    @Id
    private String id;

    private String planet;

    private String starship;

    private Double price;

    private LocalDate date;


    @Builder
    public Reservation(String id, String planet, String starship, Double price, LocalDate date) {
        this.id = id;
        this.planet = planet;
        this.starship = starship;
        this.price = price;
        this.date = date;
    }
}
