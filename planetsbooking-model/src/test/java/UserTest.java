import br.com.forttiori.Reservation;
import br.com.forttiori.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserTest {

    @Test
    @DisplayName("Deve Criar Usuario")
    public void deveCriarUsuario() {
        User user = User.builder()
                .id("id1")
                .nome("Ze user")
                .email("mail@mail.com")
                .build();
        assertNotNull(user);
    }

    @Test
    @DisplayName("Deve Settar Reserva")
    public void deveSettarReserva() {
        Reservation reservation = Mockito.mock(Reservation.class);
        User user = User.builder()
                .id("id1")
                .nome("Ze user")
                .email("mail@mail.com")
                .build();

        user.setReservations(reservation);

        when(reservation.getId()).thenReturn("id1");

        assertEquals("id1", user.getReservations().get(0).getId());
    }

    @Test
    @DisplayName("Deve Settar e buscar com Getters")
    public void deveSettarEBuscarComGetters() {
        String id = "id1";
        String nome = "Cassio";
        String email = "cassio@mail.com";
        Reservation reservation = Reservation.builder().build();

        User user = User.builder().build();
        user.setId(id);
        user.setNome(nome);
        user.setEmail(email);
        user.setReservations(reservation);

        assertEquals(id, user.getId());
        assertEquals(nome, user.getNome());
        assertEquals(email, user.getEmail());
        assertEquals(reservation, user.getReservations().get(0));
    }




}
