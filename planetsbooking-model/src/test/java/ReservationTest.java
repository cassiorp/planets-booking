import br.com.forttiori.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {


    @Test
    @DisplayName("Deve Criar Reserva!")
    public void deveCriarReservation() {
        Reservation reservation = Reservation.builder()
                .id("id1")
                .planet("Alderaan")
                .starship("Nave1")
                .price(100.0)
                .date(LocalDate.now())
                .build();
        assertNotNull(reservation);
    }

    @Test
    @DisplayName("Deve Settar e buscar com Getters!")
    public void deveSettarEBuscarComGetters() {
        String id = "id1";
        String planet = "Alderaan";
        String starship = "Nave1";
        Double price = 100.0;
        LocalDate date = LocalDate.of(2021,1, 31);

        Reservation reservation = Reservation.builder().build();
        reservation.setId(id);
        reservation.setPlanet(planet);
        reservation.setStarship(starship);
        reservation.setPrice(price);
        reservation.setDate(date);

        assertEquals(id, reservation.getId());
        assertEquals(planet, reservation.getPlanet());
        assertEquals(starship, reservation.getStarship());
        assertEquals(price, reservation.getPrice());
        assertEquals(date, reservation.getDate());
    }


}


