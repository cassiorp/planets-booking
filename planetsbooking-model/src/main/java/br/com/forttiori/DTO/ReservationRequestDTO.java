package br.com.forttiori.DTO;

import br.com.forttiori.Reservation;
import br.com.forttiori.User;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private LocalDate date;

    public static Reservation mapToReservationEntity(ReservationRequestDTO reservationRequestDTO) {
        return Optional.ofNullable(reservationRequestDTO)
                .map(reservation -> Reservation.builder()
                        .planet(reservationRequestDTO.getPlanet())
                        .starship(reservationRequestDTO.getStarship())
                        .price(reservation.getPrice())
                        .date(reservationRequestDTO.getDate())
                        .build())
                .orElseThrow(() -> new IllegalArgumentException());
    }



}
