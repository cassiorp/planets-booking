package br.com.forttiori;

import br.com.forttiori.DTO.ReservationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.forttiori.DTO.ReservationRequestDTO.mapToReservationEntity;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserService userService;

    public Reservation save(String idUser, ReservationRequestDTO reservationRequestDTO) {


        User user = this.userService.findByIdEntity(idUser);
        System.out.println(user);
        Reservation reservation = mapToReservationEntity(reservationRequestDTO);
        System.out.println(reservation);
        this.reservationRepository.save(reservation);

        user.setReservations(reservation);
        this.userService.save(user);

        return reservation;

    }
}
