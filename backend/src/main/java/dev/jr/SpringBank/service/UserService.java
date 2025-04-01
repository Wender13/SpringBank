package dev.jr.SpringBank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.jr.SpringBank.model.User;
import dev.jr.SpringBank.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import dev.jr.SpringBank.model.Transaction;
import dev.jr.SpringBank.model.TransactionType;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionService transactionService;

    // Atualizar dados do usuário
    public void updateUserLogin(String currentLogin, String newUserLogin) {
        if (newUserLogin.isEmpty()) {
            throw new IllegalArgumentException("O novo nome de usuário é obrigatório!");
        }
    
        // Verifica se o novo login já existe
        UserDetails existingUserDetails = userRepository.findByLogin(newUserLogin);
        if (existingUserDetails instanceof User) {
            throw new IllegalArgumentException("O nome de usuário já está em uso!");
        }
    
        // Busca o usuário pelo login atual
        UserDetails currentUserDetails = userRepository.findByLogin(currentLogin);
        if (!(currentUserDetails instanceof User currentUser)) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }
    
        // Atualiza o login e salva no banco
        currentUser.setLogin(newUserLogin);
        userRepository.save(currentUser);

        // Atualiza o login nas transações do usuário
        transactionService.updateUserTransactions(currentLogin, newUserLogin);
    }    

    // Atualizar e-mail do usuário
    public void updateUserEmail(String login, String newEmail) {
        if (newEmail.isEmpty()) {
            throw new IllegalArgumentException("O novo e-mail é obrigatório!");
        }
    
        User user = (User) userRepository.findByLogin(login);
        if (user == null) {
            throw new IllegalArgumentException("Usuário não encontrado!");
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

    // Alterar senha do usuário
    public void updatePassword(String login, String newPassword) {
        if (newPassword.isEmpty()) {
            throw new IllegalArgumentException("A nova senha é obrigatória!");
        }
    
        UserDetails userDetails = userRepository.findByLogin(login);
        if (!(userDetails instanceof User)) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }
        User user = (User) userDetails;
    
        // Verificar se a senha já foi usada
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("Esta já é sua senha!");
        }
    
        // Atualizar a senha com criptografia
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    

    // Alterar nome completo do usuário
    public void updateName(String login, String newName) {
        if (newName.isEmpty()) {
            throw new IllegalArgumentException("O novo nome é obrigatório!");
        }

        UserDetails userDetails = userRepository.findByLogin(login);
        if (!(userDetails instanceof User)) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }
        User user = (User) userDetails;
    
        // Verificar se o nome já foi usado
        if (newName.matches(user.getName())){
            throw new IllegalArgumentException("Esta já é seu nome!");
        }

        user.setName(newName);
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
            transactionService.registerTransaction(new Transaction(login, null, TransactionType.DEPOSITO, value, LocalDateTime.now().toInstant(ZoneOffset.of("-03:00"))));
            
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
            transactionService.registerTransaction(new Transaction(login, null, TransactionType.SAQUE, value, LocalDateTime.now().toInstant(ZoneOffset.of("-03:00"))));
            
            return userRepository.save(user);  // Salva o usuário após o depósito
        }
    
        throw new RuntimeException("Usuário não encontrado!");
    }

    // Transferir saldo entre usuários
    public void transfer(String originLogin, String destinationLogin, double value) {
        // Impedir valores negativos ou zero
        if (value <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero!");
        }
    
        // Impedir transferência para a própria conta
        if (originLogin.equals(destinationLogin)) {
            throw new IllegalArgumentException("Não é possível transferir para sua própria conta!");
        }
    
        // Buscar usuário de origem
        User originUser = (User) userRepository.findByLogin(originLogin);
        if (originUser == null) {
            throw new IllegalArgumentException("Usuário de origem não encontrado!");
        }
    
        // Buscar usuário de destino
        User destinationUser = (User) userRepository.findByLogin(destinationLogin);
        if (destinationUser == null) {
            throw new IllegalArgumentException("Usuário de destino não encontrado!");
        }
    
        // Verificar saldo suficiente
        if (originUser.getBalance() < value) {
            throw new IllegalArgumentException("Saldo insuficiente!");
        }
    
        // Realizar transferência
        originUser.setBalance(originUser.getBalance() - value);
        destinationUser.setBalance(destinationUser.getBalance() + value);

        // Registro da transação
        transactionService.registerTransaction(new Transaction(originLogin, destinationLogin, TransactionType.TRANSFERENCIA, value, LocalDateTime.now().toInstant(ZoneOffset.of("-03:00"))));
    
        // Salvar alterações
        userRepository.save(originUser);
        userRepository.save(destinationUser);
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