package br.com.forttiori.integrationTests;

import br.com.forttiori.DTO.UserResponseDTO;
import br.com.forttiori.UserNotFoundException;
import br.com.forttiori.UserRepository;
import br.com.forttiori.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static br.com.forttiori.DTO.UserResponseDTO.mapEntityToResponse;
import static br.com.forttiori.DTO.UserResponseDTO.mapListUserToListResponse;
import static br.com.forttiori.integrationTests.stubs.UserControllerIntegrationStubs.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @AfterEach
    @BeforeEach
    void setUp() {
        //CUIDADO
        this.userRepository.deleteAll();
    }

    @Test
    void deveCriarUsuario() throws Exception {

        var json = objectMapper.writeValueAsString(userRequestDTO());

        var result = this.mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        var salvo = objectMapper.readValue(result.getResponse().getContentAsString(),
                UserResponseDTO.class);

        var esperado = userResponseDTO();
        var idUser = objectMapper.readValue(result.getResponse().getContentAsString(),
                UserResponseDTO.class).getId();
        esperado.setId(idUser);

        assertEquals(esperado, salvo);

    }

    @Test
    void deveBuscarTodosUsuarios() throws Exception {

        this.userRepository.saveAll(usersList());

        var result = this.mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andReturn();

        var buscados = Arrays.asList(objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UserResponseDTO[].class
        ));

        var esperados = usersResponseList();

        assertEquals(esperados, buscados);

    }

    @Test
    void deveBuscarUsuariosComPaginacao() throws Exception {

        this.userRepository.saveAll(usersList());
        this.userRepository.saveAll(usersList2());

        var result = this.mockMvc.perform(get("/api/users?page=1"))
                .andExpect(status().isOk())
                .andReturn();

        var buscados = Arrays.asList(objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UserResponseDTO[].class
        ));

        var esperados = usersResponseList();
        var naoEsperados = mapListUserToListResponse(usersList2());
        assertEquals(esperados, buscados);
        assertNotEquals(naoEsperados, buscados);

    }


    @Test
    void deveBuscarUsuariosPorNome() throws Exception {

        this.userRepository.saveAll(usersList());

        var result = this.mockMvc.perform(get("/api/users?nome=ca"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        var buscados = Arrays.asList(objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UserResponseDTO[].class
        ));

        var esperados = Arrays.asList(
                usersResponseList().get(0),
                usersResponseList().get(4)
        );

        assertEquals(esperados, buscados);

    }


    @Test
    void deveBuscarUsuaioPorId() throws Exception {

        this.userRepository.saveAll(usersList());

        var result = this.mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();


        var buscado = objectMapper.readValue(result.getResponse().getContentAsString(),
                UserResponseDTO.class);

        var esperado = mapEntityToResponse(usersList().get(4));

        assertEquals(esperado, buscado);

    }

    @Test
    void deveLancarUserNotFoundException() throws Exception {

        this.userRepository.saveAll(usersList());

        var result = this.mockMvc.perform(get("/api/users/100")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();


        var exception = objectMapper.readValue(result.getResponse().getContentAsString(),
                UserNotFoundException.class);

        var errorMessage = "Usuario não encontrado";

        assertEquals(errorMessage, exception.getMessage());

    }



    @Test
    void deveDeletarUsuariosPorListaDeIds() throws Exception {

        this.userRepository.saveAll(usersList());

        var result = this.mockMvc.perform(delete("/api/users?ids=1,2,3,4,5"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();


        var excluidos = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                UserResponseDTO[].class
        );
        var excluidosList = Arrays.asList(excluidos);


        var esperado = mapListUserToListResponse(usersList());
        Collections.reverse(esperado);

        assertEquals(esperado, excluidosList);

    }
}
