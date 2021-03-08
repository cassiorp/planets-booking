package br.com.forttiori.DTO;

import br.com.forttiori.Reservation;
import br.com.forttiori.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponseDTO {

    private String id;
    private String nome;
    private String email;
    @Indexed(direction = IndexDirection.ASCENDING)
    private List<Reservation> reservations;


    public static UserResponseDTO mapEntityToResponse(User user) {
        return Optional.ofNullable(user)
                .map(userEntity -> UserResponseDTO.builder()
                        .id(userEntity.getId())
                        .nome(userEntity.getNome())
                        .email(userEntity.getEmail())
                        .reservations(userEntity.getReservations())
                        .build())
                .orElseThrow(() -> new IllegalArgumentException());
    }

    public static List<UserResponseDTO> mapListUserToListResponse(List<User> users) {
        return users.stream()
                .map(user -> mapEntityToResponse(user))
                .collect(Collectors.toList());
    }

}
