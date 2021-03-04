import br.com.forttiori.DTO.NewDateDTO;
import br.com.forttiori.*;
import br.com.forttiori.service.PlanetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static stubs.ReservationServiceStubs.*;


@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    UserService userService;

    @Mock
    PlanetService planetService;

    @InjectMocks
    ReservationService reservationService;


    @Test
    @DisplayName("Deve Salvar planeta")
    public void deveSalvarReserva() {
        var reservaStub = reservationIdNull();
        var reservaRetorno = reservation();

        var user = user();

        var reservaRequestDto = reservationRequestDTO();

        when(userService.findByIdEntity("id1")).thenReturn(user);

        when(planetService.verifyPlanet(reservaRequestDto.getPlanet())).thenReturn(true);

        when(userService.save(user)).thenReturn(user);

        when((reservationRepository.save(reservaStub))).thenReturn(reservaRetorno);

        var salva = this.reservationService.save("id1", reservaRequestDto);

        assertEquals(reservaRetorno, salva);
        assertEquals(reservaRetorno.getPlanet(), user.getReservations().get(0).getPlanet());

    }

    @Test
    @DisplayName("Deve lancar UserNotFoundException ao salva com id nao existente")
    void deveLancarExceptionUserNotFound(){
        String messageError = "Usuario não encontrado";

        var reservaRequestDto = reservationRequestDTO();

        when(userService.findByIdEntity("id1")).thenThrow(new UserNotFoundException(messageError));

        var exception = assertThrows(UserNotFoundException.class,
                () -> reservationService.save("id1", reservaRequestDto));

        assertEquals(messageError, exception.getMessage());

    }

    @Test
    @DisplayName("Deve lancar PlaneNotFound com planeta invalido")
    void deveLancarExceptionPlanetNotFound(){
        String messageError = "Planeta não encontrado";

        var reservaRequestDto = reservationRequestDTO();

        when(planetService.verifyPlanet(reservaRequestDto.getPlanet()))
                .thenThrow(new PlanetNotFoundException(messageError));

        var exception = assertThrows(PlanetNotFoundException.class,
                () -> reservationService.save("id1", reservaRequestDto));

        assertEquals(messageError, exception.getMessage());

    }


    @Test
    @DisplayName("Deve Buscar Reserva Por Id")
    void deveBuscarReservaPorId() {
        var reservaStub = reservation();
        var reservaRetorno = Reservation.builder().id("id1").planet("Terra").starship("nave").price(100.0).date(LocalDate.now()).build();

        when(reservationRepository.findById("id1")).thenReturn(Optional.ofNullable(reservaRetorno));

        var buscada = this.reservationService.findById("id1");

        assertEquals(reservaRetorno, buscada);
    }


    @Test
    @DisplayName("Deve editar data da reserva")
    void deveEditarDataReserva() {
        var user = user();
        user.setReservations(reservation());
        user.setReservations(reservation());

        var reservaRetorno = reservation();

        var reservaEditada = reservationComDataEditada();

        var novaData = NewDateDTO.builder().id("id1").date(LocalDate.of(2021,11,11)).build();

        when(userService.findByIdEntity("id1")).thenReturn(user);

        when(reservationRepository.findById("id1")).thenReturn(Optional.ofNullable(reservaRetorno));
        when(reservationRepository.save(reservationComDataEditada())).thenReturn(reservaEditada);

        var editada = this.reservationService.updateDate("id1", novaData);

    }

    @Test
    @DisplayName("Deve lancar UserNotFoundException ao editar com id nao existente")
    void deveLancarExceptionUserNotFoundAoEditar(){
        String messageError = "Usuario não encontrado";

        var novaData = NewDateDTO.builder().id("id1").date(LocalDate.of(2021,11,11)).build();

        when(userService.findByIdEntity("id1")).thenThrow(new UserNotFoundException(messageError));

        var exception = assertThrows(UserNotFoundException.class,
                () -> reservationService.updateDate("id1", novaData));

        assertEquals(messageError, exception.getMessage());

    }

    @Test
    @DisplayName("Deve lancar ReservationNotFound ao editar com id nao existente")
    void deveLancarExceptionReservationNotFoundAoEditar(){
        String messageError = "Planeta não encontrado";
        var userRetorno = user();
        var novaData = NewDateDTO.builder().id("id1").date(LocalDate.of(2021,11,11)).build();

        when(userService.findByIdEntity("id1")).thenReturn(userRetorno);

        when(reservationRepository.findById("id1")).thenThrow(new ReservationNotFoundException(messageError));

        var exception = assertThrows(ReservationNotFoundException.class,
                () -> reservationService.updateDate("id1", novaData));

        assertEquals(messageError, exception.getMessage());

    }



    @Test
    @DisplayName("Deve deletar Reserva por id")
    void deveDeletarReserva() {
        var user = user();
        var reservation = reservation();
        user.setReservations(reservation);

        when(userService.findByIdEntity("id1")).thenReturn(user);
        when(reservationRepository.findById("id1")).thenReturn(Optional.ofNullable(reservation));

        this.reservationService.deleteById("id1", "id1");

        verify(reservationRepository, times(1)).deleteById("id1");

    }


}
