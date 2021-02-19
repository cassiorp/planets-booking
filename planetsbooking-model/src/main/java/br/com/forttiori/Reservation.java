package br.com.forttiori;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Builder
@Data
@Document(collection = "reservations")
public class Reservation {

    @Id
    private String id;

    private String planet;

    private String starship;

    private Double price;

    private LocalDate date;


}
