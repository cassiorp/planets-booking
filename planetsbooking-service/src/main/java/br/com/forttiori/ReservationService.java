package br.com.forttiori;

import br.com.forttiori.DTO.NewDateDTO;
import br.com.forttiori.DTO.ReservationRequestDTO;
import br.com.forttiori.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.forttiori.DTO.ReservationRequestDTO.mapToReservationEntity;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final PlanetService planetService;


    public Reservation save(String idUser, ReservationRequestDTO reservationRequestDTO) {
        User user = this.userService.findByIdEntity(idUser);
        if(!planetService.verifyPlanet(reservationRequestDTO.getPlanet())){
            throw new PlanetNotFoundException("Planeta não encontrado");
        }

        Reservation reservation = mapToReservationEntity(reservationRequestDTO);
        reservation = this.reservationRepository.save(reservation);

        user.setReservations(reservation);
        user = this.userService.save(user);

        return reservation;
    }

    public Reservation findById(String id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reserva não encontrada"));
    }

    public Reservation updateDate(String idUser, NewDateDTO newDateDTO) {
        User user = this.userService.findByIdEntity(idUser);
        Reservation reservation = this.findById(newDateDTO.getId());

        int index = user.getReservations().indexOf(reservation);
        user.getReservations().remove(index);

        reservation.setDate(newDateDTO.getDate());
        reservation = this.reservationRepository.save(reservation);

        user.getReservations().add(index, reservation);
        user = userService.save(user);

        return reservation;
    }


    public void deleteById(String idUser, String idReservation) {
        User user = this.userService.findByIdEntity(idUser);
        Reservation reservation = this.findById(idReservation);

        user.getReservations().remove(reservation);
        user = this.userService.save(user);

        this.reservationRepository.deleteById(idReservation);

    }





}
