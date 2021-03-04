import br.com.forttiori.DTO.NewDateDTO;
import br.com.forttiori.ReservationController;
import br.com.forttiori.ReservationService;
import br.com.forttiori.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static stubs.ReservationControllerStubs.*;

@ExtendWith(MockitoExtension.class)
public class ReservationContollerTest {

    @Mock
    ReservationService reservationService;

    @InjectMocks
    ReservationController reservationController;

    @Test
    @DisplayName("Deve salvar reserva")
    void deveSalvarReserva() {
        var reservationStubRequest = reservationRequestDTO();
        var retorno = reservation();

        when(reservationService.save("id1", reservationStubRequest)).thenReturn(retorno);

        var salva = reservationController.save("id1", reservationStubRequest);

        assertEquals(retorno, salva);
    }

    @Test
    @DisplayName("Deve lançar exception UserNotFound, id inexistente")
    void deveLancarUserNotFoundException() {
        String messageError = "Usuario não encontrado";

        var reservationStubRequest = reservationRequestDTO();

        when(reservationService.save("id1", reservationStubRequest))
                .thenThrow(new UserNotFoundException(messageError));

        var exception = assertThrows(UserNotFoundException.class,
                () -> reservationController.save("id1", reservationStubRequest) );

        assertEquals(messageError, exception.getMessage());
    }

    @Test
    @DisplayName("Deve editar data da reserva")
    void deveEditarData() {

        var retorno = reservation();

        var novaData = NewDateDTO.builder().id("id1").date(LocalDate.of(2021,11,11)).build();

        when(reservationService.updateDate("id1", novaData)).thenReturn(retorno);

        var salva = reservationController.updateDate("id1", novaData);

        assertEquals(retorno, salva);
    }


    @Test
    @DisplayName("Deve deletar reserva")
    void deveDeletarReserva() {

        this.reservationController.delete("idUser", "idReservation");

        verify(reservationService, times(1)).deleteById("idUser", "idReservation");
    }

}
