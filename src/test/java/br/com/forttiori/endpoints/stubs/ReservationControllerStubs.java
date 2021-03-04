package br.com.forttiori.endpoints.stubs;

import br.com.forttiori.DTO.NewDateDTO;
import br.com.forttiori.DTO.ReservationRequestDTO;
import br.com.forttiori.Reservation;
import br.com.forttiori.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ReservationControllerStubs {

    public static ReservationRequestDTO reservationRequestDTO() {
        return ReservationRequestDTO.builder()
                .planet("Terra")
                .starship("nave")
                .price(100.0)
                .date(LocalDate.now())
                .build();
    }

    public static Reservation reservationIdNull() {
        return Reservation.builder()
                .id(null)
                .planet("Terra")
                .starship("nave")
                .price(100.0)
                .date(LocalDate.now())
                .build();
    }

    public static Reservation reservation() {
        return Reservation.builder()
                .id("id1")
                .planet("Terra")
                .starship("nave")
                .price(100.0)
                .date(LocalDate.now())
                .build();
    }

    public static Reservation reservationComDataEditada() {
        return Reservation.builder()
                .id("id1")
                .planet("Terra")
                .starship("nave")
                .price(100.0)
                .date(LocalDate.of(2021,11,11))
                .build();
    }

    public static User user() {
        return User.builder()
                .id("id1")
                .nome("Cassio")
                .email("cassio@mail.com")
                .senha("senha")
                .build();
    }

    public static NewDateDTO newDateDTO() {
        return NewDateDTO.builder()
                .id("id1")
                .date(LocalDate.now())
                .build();
    }

}
