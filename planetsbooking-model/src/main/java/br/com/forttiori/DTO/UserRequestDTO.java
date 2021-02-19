package br.com.forttiori.DTO;

import br.com.forttiori.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequestDTO {

    @NotNull
    @NotEmpty
    private String nome;
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
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
