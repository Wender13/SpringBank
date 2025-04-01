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
public class UserController {
    
    @Autowired
    private UserService userService;

    // Atualizar login do usuário
    @PutMapping("/changeUserLogin")
    public ResponseEntity<?> changeUserLogin(@RequestParam String currentLogin, @RequestParam String newUserLogin) {
        userService.updateUserLogin(currentLogin, newUserLogin);
        return ResponseEntity.ok("Login atualizado com sucesso.");
    }

    // Atualizar e-mail do usuário
    @PutMapping("/changeUserEmail")
    public ResponseEntity<?> changeUserEmail(@RequestParam String login, @RequestParam String newUserEmail) {
        return updateUserField(login, () -> userService.updateUserEmail(login, newUserEmail), "E-mail atualizado com sucesso.");
    }


    // Atualizar senha do usuário
    @PutMapping("/changeUserPassword")
    public ResponseEntity<?> changeUserPassword(@RequestParam String login, @RequestParam String newUserPassword) {
        return updateUserField(login, () -> userService.updatePassword(login, newUserPassword), "Senha atualizada com sucesso.");
    }

    // Atualizar nome completo do usuário
    @PutMapping("/changeUserName")
    public ResponseEntity<?> changeUserName(@RequestParam String login, @RequestParam String newUserName) {
        return updateUserField(login, () -> userService.updateName(login, newUserName), "Nome atualizado com sucesso.");
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
        System.out.println("Tentando excluir o usuário: " + login);

        User userFromDb = userService.findUserByLogin(login);
        if (userFromDb == null) {
            System.out.println("Usuário não encontrado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String authenticatedLogin = userDetails.getUsername();

            boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            if (authenticatedLogin.equals(login)) {
                System.out.println("Usuário autorizado a se excluir.");
                userService.deleteUser(login);
                return ResponseEntity.ok("Usuário deletado com sucesso.");
            }

            if (!isAdmin) {
                System.out.println("Usuário sem permissão para excluir outra conta.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você só pode excluir sua própria conta.");
            }

            if (authenticatedLogin.equals(login) && isAdmin) {
                System.out.println("Admin tentando se excluir. Ação bloqueada.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admins não podem excluir a própria conta.");
            }

            System.out.println("Admin excluiu outro usuário.");
            userService.deleteUser(login);
            return ResponseEntity.ok("Usuário deletado com sucesso.");
        }

        System.out.println("Erro de autenticação.");
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