import br.com.forttiori.User;
import br.com.forttiori.UserController;
import br.com.forttiori.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static br.com.forttiori.DTO.UserResponseDTO.mapListUserToListResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static stubs.UserControllerStubs.*;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Test
    @DisplayName("Deve salvar usuario")
    public void deveSalvarUsuario() {
        var request = userRequestDTO();
        var response = userResponseDTO();

        when(userService.save(request)).thenReturn(response);

        var salvo = this.userController.save(request);

        assertEquals(response, salvo);
    }

    @Test
    @DisplayName("Deve buscar todos usuarios - findAll")
    void deveBuscarTodosUsuarios() {
        var response = mapListUserToListResponse(usersList());

        when(userService.findAll(null, null)).thenReturn(response);

        var buscados = this.userController.findAll(null, null);

        assertEquals(response, buscados);
    }

    @Test
    @DisplayName("Deve buscar com paginação - findAll")
    void deveBuscarComPaginacao() {
        var response = mapListUserToListResponse(usersList());

        when(userService.findAll(1, null)).thenReturn(response);

        var buscados = this.userController.findAll(1, null);

        assertEquals(response, buscados);
    }

    @Test
    @DisplayName("Deve buscar por nome - findAll")
    void deveBuscarPorNome() {
        var response = mapListUserToListResponse(
                Arrays.asList(usersList().get(0), usersList().get(4)));

        when(userService.findAll(null, "ca")).thenReturn(response);

        var buscados = this.userController.findAll(null, "ca");

        assertEquals(response, buscados);
    }

    @Test
    @DisplayName("Deve buscar por ID")
    void deveBuscarPorID() {
        var response = userResponseDTO();

        when(userService.findByIdResponse("id")).thenReturn(response);

        var buscado = this.userController.find("id");

        assertEquals(response, buscado);
    }

    @Test
    @DisplayName("Deve deletar usuarios por id")
    void deveDeletarUsuariosPorID() {
        List<User> users = (usersList());
        List<String> ids = Arrays.asList("1", "2", "3", "4", "5");

        when(userService.deleteMany(ids)).thenReturn(users);

        var deletados = this.userController.deleteMany(ids);
        var esperados = users;

        assertEquals(esperados, deletados);
        verify(userService, times(1)).deleteMany(ids);
    }

}
