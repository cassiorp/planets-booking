package br.com.forttiori;

import br.com.forttiori.DTO.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.forttiori.DTO.UserRequestDTO.mapToUserEntity;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(UserRequestDTO userRequestDTO) {
        return this.userRepository.save(mapToUserEntity(userRequestDTO));
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public List<User> find(Integer page, String nome) {
        if(page == null && nome == null)
            return this.userRepository.findAll();

        if(nome != null)
            return this.userRepository.findByNomeIgnoreCaseStartingWith(nome);

        return this.findAll(page - 1).toList();
    }

    public Page<User> findAll(Integer page) {
        Pageable pageable =  PageRequest.of(page, 5);
        Page<User> pages = userRepository.findAll(pageable);
        return pages;
    }

    public List<User> findAll() {
      return this.userRepository.findAll();
    }

    public User findById(String id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> findAllByReservations_PriceBetween(Double min, Double max) {
        return this.userRepository.findAllByReservations_PriceBetween(min, max);
    }

    public List<User> findByNomeStartingWith(String nome) {
        return this.userRepository.findByNomeIgnoreCaseStartingWith(nome);
    }

    public List<User> deleteMany(List<String> ids) {
        Optional<Iterable<User>> users = Optional.ofNullable(this.userRepository.findAllById(ids));
        if(users.isPresent()){
            this.userRepository.deleteAll(users.get());
        }
        return (List<User>) users.orElseGet(() -> Collections.emptyList());
    }
}
