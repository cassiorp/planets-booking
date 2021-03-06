import br.com.forttiori.User;
import br.com.forttiori.UserNotFoundException;
import br.com.forttiori.UserRepository;
import br.com.forttiori.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static br.com.forttiori.DTO.UserResponseDTO.mapEntityToResponse;
import static br.com.forttiori.DTO.UserResponseDTO.mapListUserToListResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static stubs.UserServiceStubs.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;


    @Test
    @DisplayName("Deve salvar usuario e retornar Response DTO")
    void deveSalvarUsuarioERetornaResponse() {
        var stub = userStubIdNull();
        var retorno = userRetorno();

        when(this.userRepository.save(stub)).thenReturn(retorno);

        var salvo = this.userService.save(userRequestDTO());

        var esperado = mapEntityToResponse(retorno);

        assertEquals(esperado, salvo);
    }


    @Test
    @DisplayName("Deve salvar usuario e retornar entidade")
    void deveSalvarUsuario() {
        var stub = userStubIdNull();
        var retorno = userRetorno();

        when(this.userRepository.save(stub)).thenReturn(retorno);

        var salvo = this.userService.save(stub);

        assertEquals(retorno, salvo);
    }

    @Test
    @DisplayName("Deve chamar findAll do reposiotio, porque page e nome são nulos")
    void deveChamarFindAllRepositorio() {
        var list = usersList();

        when(this.userRepository.findAll()).thenReturn(list);

        var buscados = this.userService.findAll(null, null);
        var esperados = mapListUserToListResponse(list);

        assertEquals(esperados, buscados);
    }

    @Test
    @DisplayName("Deve chamar findByNomeIgnoreCaseStartingWith, nome não nulo")
    void deveChamarFindByNomeIgnoreCaseStartingWith() {
        var param = "Ca";
        var list = usersList();

        when(this.userRepository.findByNomeIgnoreCaseStartingWith(param))
                .thenReturn(Arrays.asList(list.get(0), list.get(4)));

        var buscados = this.userService.findAll(null, param);
        var esperados = mapListUserToListResponse(Arrays.asList(list.get(0), list.get(4)));

        assertEquals(esperados, buscados);
    }


    @Test
    @DisplayName("Deve chamar findWithPagination")
    void deveChamarfindWithPagination() {

        Page page1 = new PageImpl(usersList());

        when(this.userRepository.findAll(PageRequest.of(0, 5))).thenReturn(page1);

        var buscados = this.userService.findAll(1, null);
        var esperados = mapListUserToListResponse(usersList());

        assertEquals(esperados, buscados);
    }

    @Test
    @DisplayName("Deve buscar usuario entidade por id")
    void deveBuscarUsuarioPorID() {
        var retorno = userRetorno();

        when(this.userRepository.findById("id")).thenReturn(Optional.ofNullable(retorno));

        var buscado = this.userService.findByIdEntity("id");

        assertEquals(retorno, buscado);
    }

    @Test
    @DisplayName("Deve buscar usuario response por id")
    void deveBuscarUsuarioResponsePorID() {
        var retorno = userRetorno();

        when(this.userRepository.findById("id")).thenReturn(Optional.ofNullable(retorno));

        var buscado = this.userService.findByIdResponse("id");
        var esperando = mapEntityToResponse(retorno);

        assertEquals(esperando, buscado);
    }

    @Test
    @DisplayName("Deve lançar exception e usuario/entidade não encontrado")
    void deveLancarExceptionUserNotFoundEntidade() {
        String messageError = "Usuario não encontrado";

        var exception = assertThrows(UserNotFoundException.class,
                () -> userService.findByIdEntity("id1"));

        assertEquals(messageError, exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exception e usuario/Response não encontrado")
    void deveLancarExceptionUserNotFoundResponse() {
        String messageError = "Usuario não encontrado";

        var exception = assertThrows(UserNotFoundException.class,
                () -> userService.findByIdResponse("id1"));

        assertEquals(messageError, exception.getMessage());
    }

    @Test
    @DisplayName("Deve editar usuario")
    void deveEditarUsuario() {
        var userBuscado = userRetorno();
        var userSalvo = User.builder().id("1").nome("Cássio").email("cassio.r.pereira@mail.com").senha("senha").build();

        when(userRepository.findById("1")).thenReturn(Optional.ofNullable(userBuscado));
        when(userRepository.save(userBuscado)).thenReturn(userSalvo);

        var editado = this.userService.updateUser("1", userRequestDTOToUpDate());

        assertEquals(userSalvo.getEmail(), editado.getEmail());
    }

    @Test
    @DisplayName("Deve deletar usuarios por id")
    void deveDeletarUsuariosPorID() {
        Iterable<User> users = (usersList());
        Iterable<String> ids = Arrays.asList("1", "2", "3", "4", "5");

        when(userRepository.findAllById(ids)).thenReturn(users);

        var deletados = this.userService.deleteMany((List<String>) ids);
        var esperados = mapListUserToListResponse((List<User>) users);

        assertEquals(esperados, deletados);
        verify(userRepository, times(1)).deleteAll(users);
    }



}
