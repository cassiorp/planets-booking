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

    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        User user = mapUserToEntity(userRequestDTO);
        this.userRepository.save(user);
        return mapEntityToResponse(user);
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public List<UserResponseDTO> find(Integer page, String nome) {
        if(page == null && nome == null)
            return mapListUserToListResponse(this.userRepository.findAll());

        if(nome != null)
            return mapListUserToListResponse(this.userRepository.findByNomeIgnoreCaseStartingWith(nome));

        return mapListUserToListResponse(this.findAll(page - 1).toList());
    }

    public Page<User> findAll(Integer page) {
        Pageable pageable =  PageRequest.of(page, 5);
        Page<User> pages = userRepository.findAll(pageable);
        return pages;
    }

    public User findByIdEntity(String id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponseDTO findByIdResponse(String id) {
        Optional<User> user = this.userRepository.findById(id);
        return mapEntityToResponse(user.orElseThrow(() -> new RuntimeException("User not found")));
    }

    public List<UserResponseDTO> findAllByReservations_PriceBetween(Double min, Double max) {
        return mapListUserToListResponse(this.userRepository.findAllByReservations_PriceBetween(min, max));
    }

    public List<User> deleteMany(List<String> ids) {
        Optional<Iterable<User>> users = Optional.ofNullable(this.userRepository.findAllById(ids));
        if(users.isPresent()){
            this.userRepository.deleteAll(users.get());
        }
        return (List<User>) users.orElseGet(() -> Collections.emptyList());
    }
}
