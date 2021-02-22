package br.com.forttiori;

import br.com.forttiori.DTO.ReservationRequestDTO;
import br.com.forttiori.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.forttiori.DTO.ReservationRequestDTO.mapToReservationEntity;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final PlanetService planetService;


    public Reservation save(String idUser, ReservationRequestDTO reservationRequestDTO) {

        if(!verifyPlanet(reservationRequestDTO.getPlanet())) {
            throw new PlanetNotFoundException("Planeta não encontrado");
        }

        User user = this.userService.findByIdEntity(idUser);

        Reservation reservation = mapToReservationEntity(reservationRequestDTO);
        this.reservationRepository.save(reservation);

        user.setReservations(reservation);
        this.userService.save(user);

        return reservation;

    }

    public Boolean verifyPlanet(String planet) {
         return this.planetService.getAllPlanetsWithoutPagination()
                .getResults().stream()
                .anyMatch(r -> r.getName().equals(planet));
    }


}
