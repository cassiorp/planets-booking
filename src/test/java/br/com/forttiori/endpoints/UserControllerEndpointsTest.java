package br.com.forttiori.endpoints;

import br.com.forttiori.DTO.UserRequestDTO;
import br.com.forttiori.DTO.UserResponseDTO;
import br.com.forttiori.User;
import br.com.forttiori.UserController;
import br.com.forttiori.UserNotFoundException;
import br.com.forttiori.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("Deve salvar usuario e status 201")
    public void deveSalvarUsuarioCREATED() throws Exception {
        var json = new ObjectMapper().writeValueAsString(userRequestDTO());

        when(this.userService.save(userRequestDTO())).thenReturn(userResponseDTO());

        this.mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is("1")))
                .andExpect(jsonPath("nome", is("Cássio")))
                .andExpect(jsonPath("email", is("cassio@mail.com")))
                .andExpect(jsonPath("reservations", is(new ArrayList())))
                .andDo(print());
    }

    @Test
    @DisplayName("Deve retornar todos Usuarios e status 200")
    void deveBuscarTodosUsuarioOK() throws Exception {

        when(this.userService.findAll(null, null)).thenReturn(usersResponseList());

        this.mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(5)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].nome", is("Caio")))
                .andExpect(jsonPath("$[0].email", is("mail")))
                .andExpect(jsonPath("$[0].reservations", is(new ArrayList())))
                .andDo(print());
    }

    @Test
    @DisplayName("Deve retornar todos usuarios por nome ou começo de nome e status 200")
    void deveBuscarTodosUsuarioPorNomeOK() throws Exception {

        when(this.userService.findAll(null, "Ca")).thenReturn(
                Arrays.asList(usersResponseList().get(0), usersResponseList().get(4))
        );

        this.mockMvc.perform(get("/api/users?nome=Ca"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].nome", is("Caio")))
                .andExpect(jsonPath("$[0].email", is("mail")))
                .andExpect(jsonPath("$[0].reservations", is(new ArrayList())))

                .andExpect(jsonPath("$[1].id", is("4")))
                .andExpect(jsonPath("$[1].nome", is("Cassio")))
                .andExpect(jsonPath("$[1].email", is("mail")))
                .andExpect(jsonPath("$[1].reservations", is(new ArrayList())))
                .andDo(print());
    }

    @Test
    @DisplayName("Deve retornar usuarios com paginação e status 200")
    void deveBuscarUsuariosComPaginacaoOK() throws Exception {

        when(this.userService.findAll(1, null)).thenReturn(usersResponseList());

        this.mockMvc.perform(get("/api/users?page=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(5)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].nome", is("Caio")))
                .andExpect(jsonPath("$[0].email", is("mail")))
                .andExpect(jsonPath("$[0].reservations", is(new ArrayList())))
                .andDo(print());
    }


    @Test
    @DisplayName("Deve buscar usuario por id e status 201")
    public void deveBuscarUsuarioPorIdOK() throws Exception {

        when(this.userService.findByIdResponse("id")).thenReturn(userResponseDTO());

        this.mockMvc.perform(get("/api/users/id"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is("1")))
                .andExpect(jsonPath("nome", is("Cássio")))
                .andExpect(jsonPath("email", is("cassio@mail.com")))
                .andExpect(jsonPath("reservations", is(new ArrayList())))
                .andDo(print());
    }

    @Test
    @DisplayName("Deve retornar status not found, usuario nao encontrado e status 404")
    public void deveRetornarUserNotFoundNOT_FOUND() throws Exception {

        given(userService.findByIdResponse("id")).willThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/api/users/id")).andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Deve deletar usuarios por lista de ids")
    public void deveDeletarUsuariosPorListaIdsOK() throws Exception {
        List<String> ids = Arrays.asList("1","2","3","4","5");

        when(this.userService.deleteMany(ids)).thenReturn(usersList());

        this.mockMvc.perform(delete("/api/users?ids=1,2,3,4,5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(5)))
                .andExpect(jsonPath("$[0].id", is("5")))
                .andExpect(jsonPath("$[0].nome", is("Cassio")))
                .andExpect(jsonPath("$[0].email", is("mail")))
                .andExpect(jsonPath("$[0].reservations", is(new ArrayList())))
                .andDo(print());
    }



    public UserRequestDTO userRequestDTO() {
        return UserRequestDTO.builder()
                .nome("Cássio")
                .email("cassio@mail.com")
                .senha("senha")
                .build();
    }

    public UserResponseDTO userResponseDTO() {
        return UserResponseDTO.builder()
                .id("1")
                .nome("Cássio")
                .email("cassio@mail.com")
                .reservations(new ArrayList<>())
                .build();
    }

    public List<User> usersList() {
        return Arrays.asList(
                User.builder().id("5").nome("Cassio").email("mail").senha("senha").build(),
                User.builder().id("4").nome("Eduardo").email("mail").senha("senha").build(),
                User.builder().id("3").nome("Rodishow").email("mail").senha("senha").build(),
                User.builder().id("2").nome("SuperEdi").email("mail").senha("senha").build(),
                User.builder().id("1").nome("Caio").email("mail").senha("senha").build()
        );
    }

    public List<UserResponseDTO> usersResponseList() {
        return Arrays.asList(
                UserResponseDTO.builder().id("1").nome("Caio").email("mail").reservations(new ArrayList<>()).build(),
                UserResponseDTO.builder().id("2").nome("Eduardo").email("mail").reservations(new ArrayList<>()).build(),
                UserResponseDTO.builder().id("2").nome("SuperEdi").email("mail").reservations(new ArrayList<>()).build(),
                UserResponseDTO.builder().id("3").nome("Rodishow").email("mail").reservations(new ArrayList<>()).build(),
                UserResponseDTO.builder().id("4").nome("Cassio").email("mail").reservations(new ArrayList<>()).build()
        );
    }

}
