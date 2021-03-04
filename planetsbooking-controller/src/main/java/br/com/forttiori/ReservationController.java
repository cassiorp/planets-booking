package br.com.forttiori;

import br.com.forttiori.DTO.NewDateDTO;
import br.com.forttiori.DTO.ReservationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/{idUser}")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation save(@PathVariable String idUser, @RequestBody ReservationRequestDTO reservationRequestDTO) {
        return this.reservationService.save(idUser, reservationRequestDTO);
    }

    @PatchMapping("/{idUser}")
    public Reservation updateDate(@PathVariable String idUser, @RequestBody NewDateDTO newDateDTO) {
        return this.reservationService.updateDate(idUser, newDateDTO);
    }

    @DeleteMapping("/{idUser}/deletaReserva/{idReservation}")
    public void delete(@PathVariable String idUser, @PathVariable String idReservation) {
        this.reservationService.deleteById(idUser, idReservation);
    }
}
