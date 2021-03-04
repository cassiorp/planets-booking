package br.com.forttiori.endpoints;

import br.com.forttiori.ReservationController;
import br.com.forttiori.ReservationService;
import br.com.forttiori.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static br.com.forttiori.endpoints.stubs.ReservationControllerStubs.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
public class ReservationControllerEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ReservationService reservationService;

    @Test
    @DisplayName("Deve salvar reserva a retornar e status 201")
    void deveSalvarReservaCREATED() throws Exception {

        var json = new ObjectMapper().writeValueAsString(reservationRequestDTO());

        when(reservationService.save("id1", reservationRequestDTO())).thenReturn(reservation());

        this.mockMvc.perform(post("/api/reservations/id1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is("id1")))
                .andExpect(jsonPath("planet", is("Terra")))
                .andExpect(jsonPath("starship", is("nave")))
                .andExpect(jsonPath("price", is(100.0)))
                .andExpect(jsonPath("date", is(LocalDate.now().toString())))
                .andDo(print());

    }

    @Test
    @DisplayName("Deve deve voltar Exception UserNotFound ao tentar salvar reserva e status 404")
    void deveVoltarNotFoundAoSalvarNOTFOUND() throws Exception {

        var json = new ObjectMapper().writeValueAsString(reservationRequestDTO());

        when(reservationService.save("id1", reservationRequestDTO()))
                .thenThrow(new UserNotFoundException("Usuario não encontrado"));

        this.mockMvc.perform(post("/api/reservations/id1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

    }

    @Test
    @DisplayName("Deve editar data da reserva e status 200")
    void deveEditarDataReservaOK() throws Exception {

        var json = new ObjectMapper().writeValueAsString(newDateDTO());

        when(reservationService.updateDate("id1", newDateDTO())).thenReturn(reservation());

        this.mockMvc.perform(patch("/api/reservations/id1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is("id1")))
                .andExpect(jsonPath("planet", is("Terra")))
                .andExpect(jsonPath("starship", is("nave")))
                .andExpect(jsonPath("price", is(100.0)))
                .andExpect(jsonPath("date", is(LocalDate.now().toString())))
                .andDo(print());

    }

    @Test
    @DisplayName("Deve deve voltar Exception UserNotFound ao tentar editar data de reserva e status 404")
    void deveVoltarNotFoundAoEditarDataNOTFOUND() throws Exception {

        var json = new ObjectMapper().writeValueAsString(newDateDTO());

        when(reservationService.updateDate("id1", newDateDTO()))
                .thenThrow(new UserNotFoundException("Usuario não encontrado"));

        this.mockMvc.perform(patch("/api/reservations/id1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

    }


    @Test
    @DisplayName("Deve deletar reserva com id de usuario e id de reserva")
    void deveDeletarReservaComIdUsuarioEIdReservaOK() throws Exception {

        this.mockMvc.perform(delete("/api/idUser/deletaReserva/idReserva")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

    }

}
