package br.com.forttiori.endpointsTests.stubs;

import br.com.forttiori.DTO.UserRequestDTO;
import br.com.forttiori.DTO.UserResponseDTO;
import br.com.forttiori.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserControllerStubs {

    public static User userRetorno() {
        return User.builder()
                .id("1")
                .nome("Cássio")
                .email("cassio@mail.com")
                .senha("senha")
                .build();
    }

    public static User user() {
        return User.builder()
                .id("1")
                .nome("Cássio")
                .email("cassio@mail.com")
                .senha("senha")
                .build();
    }

    public static User userStubIdNull() {
        return User.builder()
                .id(null)
                .nome("Cássio")
                .email("cassio@mail.com")
                .senha("senha")
                .build();
    }

    public static UserRequestDTO userRequestDTO() {
        return UserRequestDTO.builder()
                .nome("Cássio")
                .email("cassio@mail.com")
                .senha("senha")
                .build();
    }

    public static UserRequestDTO userRequestDTOToUpDate() {
        return UserRequestDTO.builder()
                .nome("Cássio")
                .email("cassio.r.pereira@mail.com")
                .senha("senha")
                .build();
    }

    public static UserResponseDTO userResponseDTO() {
        return UserResponseDTO.builder()
                .id("1")
                .nome("Cássio")
                .email("cassio@mail.com")
                .reservations(new ArrayList<>())
                .build();
    }


    public static List<User> usersListIdNull() {
        return Arrays.asList(
                User.builder().nome("Cassio").email("mail").senha("senha").build(),
                User.builder().nome("Eduardo").email("mail").senha("senha").build(),
                User.builder().nome("Rodishow").email("mail").senha("senha").build(),
                User.builder().nome("SuperEdi").email("mail").senha("senha").build(),
                User.builder().nome("Caio").email("mail").senha("senha").build()
        );
    }

    public static List<User> usersList2() {
        return Arrays.asList(
                User.builder().nome("Caio").email("mail").senha("senha").build(),
                User.builder().nome("Eduardo").email("mail").senha("senha").build(),
                User.builder().nome("SuperEdi").email("mail").senha("senha").build(),
                User.builder().nome("Rodishow").email("mail").senha("senha").build(),
                User.builder().nome("Cassio").email("mail").senha("senha").build()
        );
    }

    public static List<User> usersListToPaginarionTest() {
        var list = usersList();
        list.addAll(usersList2());
        return list;
    }

    public static List<UserResponseDTO> usersResponseList() {
        return Arrays.asList(
                UserResponseDTO.builder().id("1").nome("Caio").email("mail").reservations(new ArrayList<>()).build(),
                UserResponseDTO.builder().id("2").nome("Eduardo").email("mail").reservations(new ArrayList<>()).build(),
                UserResponseDTO.builder().id("2").nome("SuperEdi").email("mail").reservations(new ArrayList<>()).build(),
                UserResponseDTO.builder().id("3").nome("Rodishow").email("mail").reservations(new ArrayList<>()).build(),
                UserResponseDTO.builder().id("4").nome("Cassio").email("mail").reservations(new ArrayList<>()).build()
        );
    }

    public static List<User> usersList() {
        return Arrays.asList(
                User.builder().id("5").nome("Cassio").email("mail").senha("senha").build(),
                User.builder().id("4").nome("Eduardo").email("mail").senha("senha").build(),
                User.builder().id("3").nome("Rodishow").email("mail").senha("senha").build(),
                User.builder().id("2").nome("SuperEdi").email("mail").senha("senha").build(),
                User.builder().id("1").nome("Caio").email("mail").senha("senha").build()
        );
    }
}
