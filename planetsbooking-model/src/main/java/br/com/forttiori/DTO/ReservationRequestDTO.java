package br.com.forttiori.DTO;

import br.com.forttiori.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDTO {

    @NotNull
    @NotEmpty
    private String planet;

    @NotNull
    @NotEmpty
    private String starship;

    @NotNull
    private Double price;

    @NotNull
    @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy" )
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    public static Reservation mapToReservationEntity(ReservationRequestDTO reservationRequestDTO) {
        return Optional.ofNullable(reservationRequestDTO)
                .map(reservationDTO -> Reservation.builder()
                        .planet(reservationDTO.getPlanet())
                        .starship(reservationDTO.getStarship())
                        .price(reservationDTO.getPrice())
                        .date(reservationDTO.getDate())
                        .build())
                .orElseThrow(() -> new IllegalArgumentException());
    }



}
