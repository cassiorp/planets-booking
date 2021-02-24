package br.com.forttiori.DTO;

import br.com.forttiori.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRequestDTO {
    private final String NOT_NULL = "Não pode ser nulo";
    private final String NOT_EMPTY = "Não pode ser vazio";

    @NotNull(message = NOT_NULL)
    @NotEmpty(message = NOT_EMPTY)
    private String nome;
    @NotNull(message = NOT_NULL)
    @NotEmpty(message = NOT_EMPTY)
    private String email;
    @NotNull(message = NOT_NULL)
    @NotEmpty(message = NOT_EMPTY)
    private String senha;

    public static User mapUserToEntity(UserRequestDTO userRequestDTO) {
        return Optional.ofNullable(userRequestDTO)
                .map(userDTO -> User.builder()
                        .nome(userDTO.getNome())
                        .email(userDTO.getEmail())
                        .senha(userDTO.getSenha())
                        .build())
                .orElseThrow(() -> new IllegalArgumentException());
    }

}
