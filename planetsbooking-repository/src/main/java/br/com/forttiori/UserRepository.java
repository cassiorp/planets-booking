package br.com.forttiori;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAllByReservations_PriceBetween(Double min, Double max);
    List<User> findByNomeIgnoreCaseStartingWith(String nome);
}
