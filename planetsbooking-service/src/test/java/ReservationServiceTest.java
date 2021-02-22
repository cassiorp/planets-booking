import br.com.forttiori.*;
import br.com.forttiori.DTO.ReservationRequestDTO;
import br.com.forttiori.service.PlanetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

//    @Mock
//    ReservationRepository reservationRepository;
//    @Mock
//    UserService userService;
//    @Mock
//    PlanetService planetService;
//
//    @InjectMocks
//    ReservationService reservationService;
//
//    @Test
//    @DisplayName("Deve Salvar Reserva")
//    public void deveSalvarReserva() {
//        String idCliente = "id1";
//
//        when(reservationService.verifyPlanet("planeta")).thenReturn(true);
//
//        User user = user();
//        when( userService.findByIdEntity(idCliente) ).thenReturn(user);
//
//        Reservation reservation = reservation();
//        when( reservationRepository.save(reservation) ).thenReturn(reservation());
//
//        user.setReservations(reservation);
//        when( userService.save(user) ).thenReturn(user);
//
//        Reservation salva = this.reservationService.save(idCliente, reservationRequestDTO());
//
//        Reservation esperada = reservation();
//
//        assertEquals(esperada, salva);
//
//    }
//
//    @Test
//    @DisplayName("Deve Lançar exception para user não encontrado")
//    public void deveLancarExceptionUserNotFound() {
//        String messageError = "User not found";
//        PlanetNotFoundException exception = assertThrows(PlanetNotFoundException.class, () -> reservationService.save("id", reservationRequestDTO()));
//        assertEquals(messageError, exception.getMessage());
//    }
//
//
//    public ReservationRequestDTO reservationRequestDTO() {
//        return ReservationRequestDTO.builder()
//                .planet("planeta")
//                .starship("nave")
//                .price(100.0)
//                .date(LocalDate.now())
//                .build();
//    }
//
//    public Reservation reservation() {
//        return Reservation.builder()
//                .id(null)
//                .planet("planeta")
//                .starship("nave")
//                .price(100.0)
//                .date(LocalDate.now())
//                .build();
//    }
//
//    public User user() {
//        return User.builder()
//                .id("id1")
//                .nome("Cássio")
//                .email("cassio@mail.com")
//                .senha("senha")
//                .build();
//    }
}
