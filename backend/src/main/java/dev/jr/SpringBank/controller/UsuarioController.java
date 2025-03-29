package dev.jr.SpringBank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.jr.SpringBank.model.User;
import dev.jr.SpringBank.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/users")
public class UsuarioController {
    
    @Autowired
    private UserService userService;

    // Atualizar login do usuário
    @PutMapping("/changeUserLogin")
    public ResponseEntity<?> changeUserLogin(@RequestBody User user, @RequestParam String newUserLogin) {
        return updateUserField(user.getLogin(), () -> userService.updateUserName(user, newUserLogin), "Login atualizado com sucesso.");
    }

    // Atualizar e-mail do usuário
    @PutMapping("/changeUserEmail")
    public ResponseEntity<?> changeUserEmail(@RequestBody User user, @RequestParam String newUserEmail) {
        return updateUserField(user.getLogin(), () -> userService.updateUserEmail(user, newUserEmail), "E-mail atualizado com sucesso.");
    }

    // Atualizar senha do usuário
    @PutMapping("/changeUserPassword")
    public ResponseEntity<?> changeUserPassword(@RequestBody User user, @RequestParam String newUserPassword) {
        return updateUserField(user.getLogin(), () -> userService.updatePassword(user, newUserPassword), "Senha atualizada com sucesso.");
    }

    // Atualizar nome completo do usuário
    @PutMapping("/changeUserName")
    public ResponseEntity<?> changeUserName(@RequestBody User user, @RequestParam String newUserName) {
        return updateUserField(user.getLogin(), () -> userService.updateName(user, newUserName), "Nome atualizado com sucesso.");
    }

    // Depositar saldo
    @PutMapping("/deposit/{login}/{value}")
    public ResponseEntity<?> deposit(@PathVariable String login, @PathVariable double value) {
        return updateUserField(login, () -> userService.deposit(login, value), 
                            "Depósito de R$ " + value + " realizado com sucesso.");
    }

    // Sacar saldo
    @PutMapping("/withdraw/{login}/{value}")
    public ResponseEntity<?> withdraw(@PathVariable String login, @PathVariable double value) {
        return updateUserField(login, () -> userService.withdraw(login, value), "R$ " + value + " sacado com sucesso.");
    }

    // Transferir saldo entre usuários
    @PostMapping("/transfer/{originLogin}/{destinationLogin}/{value}")
    public ResponseEntity<?> transferir(@PathVariable String originLogin, @PathVariable String destinationLogin, @PathVariable double value) {
        return updateUserField(originLogin, () -> userService.transfer(originLogin, destinationLogin, value), 
                               "R$ " + value + " transferido com sucesso.");
    }

    // Excluir usuário por login
    @DeleteMapping("/delete/{login}")
    public ResponseEntity<?> deleteUser(@PathVariable String login) {
        User userFromDb = userService.findUserByLogin(login);
        if (userFromDb == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String authenticatedLogin = userDetails.getUsername();

            // Se o usuário autenticado for "ADMIN" e não estiver tentando deletar a própria conta, ele pode excluir
            boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            if (!authenticatedLogin.equals(login) && !isAdmin) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você só pode excluir sua própria conta.");
            }

            // Se for ADMIN, mas está tentando excluir a própria conta, bloqueamos a ação
            if (authenticatedLogin.equals(login) && isAdmin) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admins não podem excluir a própria conta.");
            }

            userService.deleteUser(login);
            return ResponseEntity.ok("Usuário deletado com sucesso.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro de autenticação.");
    }


    // Método auxiliar para verificar e atualizar dados do próprio usuário
    private ResponseEntity<?> updateUserField(String login, Runnable updateAction, String successMessage) {
        User userFromDb = userService.findUserByLogin(login);
        if (userFromDb == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        if (!isAuthenticatedUser(userFromDb.getLogin())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para modificar essa conta.");
        }

        updateAction.run();
        return ResponseEntity.ok(successMessage);
    }

    // Método auxiliar para verificar se o usuário autenticado está tentando modificar sua própria conta
    private boolean isAuthenticatedUser(String login) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            return userDetails.getUsername().equals(login);
        }
        
        return false;
    }
}