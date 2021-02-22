import br.com.forttiori.DTO.UserRequestDTO;
import br.com.forttiori.DTO.UserResponseDTO;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;


    @Test
    @DisplayName("Deve salvar usuario e retornar Response DTO")
    public void deveSalvarUsuarioERetornaResponse() {
        var stub = userStubIdNull();
        var retorno = userRetorno();

        when( this.userRepository.save( stub ) ).thenReturn(retorno);

        var salvo = this.userService.save( userRequestDTO() );
        var esperado = UserResponseDTO.mapEntityToResponse( retorno );

        assertEquals( esperado, salvo );
    }


    @Test
    @DisplayName("Deve salvar usuario e retornar entidade")
    public void deveSalvarUsuario() {
        var stub = userStubIdNull();
        var retorno = userRetorno();

        when( this.userRepository.save( stub ) ).thenReturn( retorno );

        var salvo = this.userService.save( stub );

        assertEquals( retorno, salvo );
    }

    @Test
    @DisplayName("Deve chamar findAll do reposiotio, porque page e nome são nulos")
    public void deveChamarFindAllRepositorio() {
        var list = this.usersList();

        when( this.userRepository.findAll() ).thenReturn( list );

        var buscados = this.userService.findAll(null, null );
        var esperados = UserResponseDTO.mapListUserToListResponse( list );

        assertEquals( esperados, buscados );
    }

    @Test
    @DisplayName("Deve chamar findByNomeIgnoreCaseStartingWith, nome não nulo")
    public void deveChamarFindByNomeIgnoreCaseStartingWith() {
        var param = "Ca";
        var list = this.usersList();

        when( this.userRepository.findByNomeIgnoreCaseStartingWith( param ) )
                .thenReturn( Arrays.asList( list.get(0), list.get(4) ) );

        var buscados = this.userService.findAll(null, param );
        var esperados = UserResponseDTO
                .mapListUserToListResponse( Arrays.asList( list.get(0), list.get(4) ) );

        assertEquals( esperados, buscados );
    }


    @Test
    @DisplayName("Deve chamar findWithPagination")
    public void deveChamarfindWithPagination() {

        Page page1 = new PageImpl( usersList() );
        when( this.userRepository.findAll( PageRequest.of(1 - 1, 5 ) ) ).thenReturn( page1 );

        var buscados = this.userService.findAll(1, null );
        var esperados = UserResponseDTO.mapListUserToListResponse( usersList() );

        assertEquals( esperados, buscados );
    }

    @Test
    @DisplayName("Deve buscar usuario entidade por id")
    public void deveBuscarUsuarioPorID() {
        var retorno = userRetorno();

        when( this.userRepository.findById("id") ).thenReturn( Optional.ofNullable( retorno ) );

        var buscado = this.userService.findByIdEntity( "id" );

        assertEquals( retorno, buscado );
    }

    @Test
    @DisplayName("Deve buscar usuario response por id")
    public void deveBuscarUsuarioResponsePorID() {
        var retorno = userRetorno();

        when( this.userRepository.findById("id") ).thenReturn( Optional.ofNullable(retorno) );

        var buscado = this.userService.findByIdResponse("id");
        var esperando = UserResponseDTO.mapEntityToResponse( retorno );

        assertEquals( esperando, buscado );
    }

    @Test
    @DisplayName("Deve lançar exception e usuario/entidade não encontrado")
    public void deveLancarExceptionUserNotFoundEntidade() {
        String messageError = "User not found";

        UserNotFoundException exception = assertThrows( UserNotFoundException.class,
                () -> userService.findByIdEntity( "id1" ) );

        assertEquals( messageError, exception.getMessage() );
    }

    @Test
    @DisplayName("Deve lançar exception e usuario/Response não encontrado")
    public void deveLancarExceptionUserNotFoundResponse() {
        String messageError = "User not found";

        UserNotFoundException exception = assertThrows( UserNotFoundException.class,
                () -> userService.findByIdResponse( "id1" ) );

        assertEquals( messageError, exception.getMessage() );
    }

    @Test
    @DisplayName("Deve editar usuario")
    public void deveEditarUsuario() {
        var retornoFind = userRetorno();
        var retornoSave = user();

        when( userRepository.findById( "1" ) ).thenReturn( Optional.ofNullable( retornoFind ) );
        when( userRepository.save( retornoFind ) ).thenReturn( retornoSave );

        var editado = this.userService.updateUser("1", userRequestDTO() );
        var esperado = userResponseDTO();

        assertEquals( esperado, editado );
    }

    @Test
    @DisplayName("Deve deletar usuarios por id")
    public void deveDeletarUsuariosPorID() {
        Iterable<User> users = ( usersList() );
        Iterable <String> ids = Arrays.asList("1","2","3","4","5");

        when( userRepository.findAllById( ids ) ).thenReturn( users );

        var deletados = this.userService.deleteMany( (List<String>) ids );
        var esperados = users;

        assertEquals( esperados, deletados );
        verify( userRepository, times(1) ).deleteAll( users );
    }




    public User userRetorno() {
        return User.builder()
                .id("1")
                .nome("Cássio")
                .email("cassio@mail.com")
                .senha("senha")
                .build();
    }

    public User user() {
        return User.builder()
                .id("1")
                .nome("Cássio")
                .email("cassio@mail.com")
                .senha("senha")
                .build();
    }

    public User userStubIdNull() {
        return User.builder()
                .id(null)
                .nome("Cássio")
                .email("cassio@mail.com")
                .senha("senha")
                .build();
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
                User.builder().nome("Cassio").email("mail").senha("senha").build(),
                User.builder().nome("Eduardo").email("mail").senha("senha").build(),
                User.builder().nome("Rodishow").email("mail").senha("senha").build(),
                User.builder().nome("SuperEdi").email("mail").senha("senha").build(),
                User.builder().nome("Caio").email("mail").senha("senha").build()
        );
    }

    public List<User> usersList2() {
        return Arrays.asList(
                User.builder().nome("Caio").email("mail").senha("senha").build(),
                User.builder().nome("Eduardo").email("mail").senha("senha").build(),
                User.builder().nome("SuperEdi").email("mail").senha("senha").build(),
                User.builder().nome("Rodishow").email("mail").senha("senha").build(),
                User.builder().nome("Cassio").email("mail").senha("senha").build()
                );
    }

    public List<User> usersListToPaginarionTest() {
        var list = usersList();
        list.addAll(usersList2());
        return list;
    }

}
