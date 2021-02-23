package br.com.forttiori;

import br.com.forttiori.DTO.UserRequestDTO;
import br.com.forttiori.DTO.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.forttiori.DTO.UserRequestDTO.mapUserToEntity;
import static br.com.forttiori.DTO.UserResponseDTO.mapEntityToResponse;
import static br.com.forttiori.DTO.UserResponseDTO.mapListUserToListResponse;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final String MESSAGE_NOT_FOUND_USER = "User not found";
    private final Integer PAGINATION_SIZE = 5;

    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        var user = this.userRepository.save(mapUserToEntity(userRequestDTO));
        return mapEntityToResponse(user);
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public List<UserResponseDTO> findAll(Integer page, String nome) {
        if(page == null && nome == null)
            return mapListUserToListResponse(this.userRepository.findAll());

        if(nome != null)
            return mapListUserToListResponse(this.userRepository.findByNomeIgnoreCaseStartingWith(nome));

        return mapListUserToListResponse(
                this.userRepository.findAll(PageRequest.of(page - 1, PAGINATION_SIZE)).toList());
    }

    public User findByIdEntity(String id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException(MESSAGE_NOT_FOUND_USER));
    }

    public UserResponseDTO findByIdResponse(String id) {
        return mapEntityToResponse(this.findByIdEntity(id));
    }

    public UserResponseDTO updateUser(String id, UserRequestDTO userRequestDTO) {
        User user = this.findByIdEntity(id);
        user.setNome(userRequestDTO.getNome());
        user.setEmail(userRequestDTO.getEmail());
        user.setSenha(userRequestDTO.getSenha());
        var salvo = this.save(user);
        return mapEntityToResponse(salvo);
    }

    public List<User> deleteMany(List<String> ids) {
        Optional<Iterable<User>> users = Optional.ofNullable(this.userRepository.findAllById(ids));
        if(users.isPresent()){
            this.userRepository.deleteAll(users.get());
        }
        return (List<User>) users.orElseGet(() -> Collections.emptyList());
    }
}
