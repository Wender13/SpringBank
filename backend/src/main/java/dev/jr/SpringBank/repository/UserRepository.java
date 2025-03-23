package dev.jr.SpringBank.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import dev.jr.SpringBank.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByCPF(String cpf); 
    UserDetails findByLogin(String login);
    User findByEmailAndPassword(String email, String password);
    User findByCPFAndPassword(String cpf, String password);
    User findByEmailAndCPF(String email, String cpf);  
    Optional<User> findByEmail(String email);
    void deleteByLogin(String login);
}