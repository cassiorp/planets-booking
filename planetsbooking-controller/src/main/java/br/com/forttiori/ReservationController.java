package br.com.forttiori;

import br.com.forttiori.DTO.ReservationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/{idUser}")
    public Reservation save(@PathVariable String idUser, @RequestBody ReservationRequestDTO reservationRequestDTO) {
        return this.reservationService.save(idUser, reservationRequestDTO);
    }
}
