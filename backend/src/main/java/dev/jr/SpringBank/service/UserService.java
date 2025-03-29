package dev.jr.SpringBank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.jr.SpringBank.model.User;
import dev.jr.SpringBank.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import dev.jr.SpringBank.model.Transaction;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionService transactionService;

    // Atualizar dados do usuário
    public void updateUserName(User user, String newUserName){
        if (newUserName.isEmpty()) {
            throw new IllegalArgumentException("O novo nome de usuário é obrigatório!");
        }

        UserDetails existingUser = userRepository.findByLogin(newUserName);
        if (existingUser != null) {
            if (existingUser.getUsername().equals(user.getName())) {
            throw new IllegalArgumentException("Este já é seu nome de usuário!");
            } else {
            throw new IllegalArgumentException("O nome de usuário já está em uso!");
            }
        }

        user.setName(newUserName);
        userRepository.save(user);
    }

    // Atualizar e-mail do usuário
    public void updateUserEmail(User user, String newEmail) {
        if (newEmail.isEmpty()) {
            throw new IllegalArgumentException("O novo e-mail é obrigatório!");
        }

        Optional<User> existingUser = userRepository.findByEmail(newEmail);
        if (existingUser.isPresent()) {
            if (existingUser.get().getId().equals(user.getId())) {
                throw new IllegalArgumentException("Este já é seu e-mail!");
            } else {
                throw new IllegalArgumentException("O e-mail já está em uso!");
            }
        }

        user.setEmail(newEmail);
        userRepository.save(user);
    }

    // Alterar nome completo do usuário
    public void updateName(User user, String newName) {
        if (newName.isEmpty()) {
            throw new IllegalArgumentException("O novo nome completo é obrigatório!");
        }

        if (newName.equals(user.getName())) {
            throw new IllegalArgumentException("Este já é seu nome completo!");
        }

        user.setName(newName);
        userRepository.save(user);
    }

    // Alterar senha do usuário
    public void updatePassword(User user, String newPassword) {
        if (newPassword.isEmpty()) {
            throw new IllegalArgumentException("A nova senha é obrigatória!");
        }

        if (newPassword.equals(user.getPassword())) {
            throw new IllegalArgumentException("Esta já é sua senha atual!");
        }

        user.setpassword(newPassword);
        userRepository.save(user);
    }

    // Depositar saldo
    public User deposit(String login, double value) {
        // Recupera o UserDetails
        UserDetails userDetails = userRepository.findByLogin(login);
        
        if (userDetails != null && userDetails instanceof User) { // Verifica se é do tipo User
            User user = (User) userDetails;  // Faz o cast para User
    
            if (value <= 0) {
                throw new IllegalArgumentException("O valor do depósito deve ser positivo!");
            }
    
            user.setBalance(user.getBalance() + value);
            
            // Registro da transação
            transactionService.registerTransaction(new Transaction(user, "DEPOSITO", value, LocalDateTime.now().toInstant(ZoneOffset.of("-03:00"))));
            
            return userRepository.save(user);  // Salva o usuário após o depósito
        }
    
        throw new RuntimeException("Usuário não encontrado!");
    }
    
    
    // Sacar saldo
    public User withdraw(String login, double value) {
        UserDetails userDetails = userRepository.findByLogin(login);
        if (userDetails != null && userDetails instanceof User) { // Verifica se é do tipo User
            User user = (User) userDetails;  // Faz o cast para User
    
            if (value <= 0) {
                throw new IllegalArgumentException("O valor do saque deve ser positivo!");
            }
    
            user.setBalance(user.getBalance() - value);
            
            // Registro da transação
            transactionService.registerTransaction(new Transaction(user, "SAQUE", value, LocalDateTime.now().toInstant(ZoneOffset.of("-03:00"))));
            
            return userRepository.save(user);  // Salva o usuário após o depósito
        }
    
        throw new RuntimeException("Usuário não encontrado!");
    }

    // Transferir saldo entre usuários
    public User transfer(String idOrigin, String idDestination, double value) {
        Optional<User> originOptional = userRepository.findById(idOrigin);
        Optional<User> destinationOptional = userRepository.findById(idDestination);

        if (originOptional.isPresent() && destinationOptional.isPresent()) {
            User userOrigin = originOptional.get();
            User userDestination = destinationOptional.get();

            if (userOrigin.getBalance() >= value) {
                userOrigin.setBalance(userOrigin.getBalance() - value);
                userDestination.setBalance(userDestination.getBalance() + value);

                userRepository.save(userOrigin);

                //Registro da transação
                transactionService.registerTransaction(new Transaction(userOrigin, userDestination, value, LocalDateTime.now().toInstant(ZoneOffset.of("-03:00"))));
                return userRepository.save(userDestination);
            }
            throw new RuntimeException("Saldo insuficiente para a transferência!");
        }
        throw new RuntimeException("Um ou ambos os usuários não foram encontrados!");
    }

    // Remover usuário
    public void deleteUser(String login) {
        
        UserDetails user = userRepository.findByLogin(login);

        if(user == null)throw new UsernameNotFoundException("Usuário não encontrado!");
        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) throw new IllegalStateException("Não é possível remover um administrador!");
        
        userRepository.deleteByLogin(login);
    }

    // Buscar usuário por Login
    public User findUserByLogin(String login) {
        return (User) userRepository.findByLogin(login);
    }
}