package dev.jr.SpringBank.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Document(collection = "users") // Define a coleção no MongoDB
public class User implements UserDetails {
    
    @Id  // Define o identificador no MongoDB
    private String id;

    @NotBlank(message = "A identificação de usuário é obrigatória!")
    @Indexed(unique = true) // Garante unicidade no MongoDB
    private String login;

    @NotBlank(message = "O nome é obrigatório!")
    private String name;

    @NotBlank(message = "O e-mail é obrigatório!")
    @Email(message = "Formato de e-mail inválido!")
    @Indexed(unique = true) // Garante unicidade no MongoDB
    private String email;

    @NotBlank(message = "O CPF é obrigatório!")
    @Indexed(unique = true) // Garante unicidade no MongoDB
    private String cpf;

    @NotBlank(message = "A senha é obrigatória!")
    private String password;

    private double balance = 0.0; // Inicializa com balance zero

    private UserRole role; // Inicializa com role de usuário

    // Construtor sem parâmetros
    public User() {
    }
    
    public User(String login, String password, String name, String email, String cpf) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.role = UserRole.USER;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCPF() {
        return cpf;
    }

    public void setCPF(String cpf) {
        this.cpf = cpf;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", name=" + name + ", email=" + email + ", CPF=" + cpf + ", balance=" + balance
                + ", password=" + password + "]";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.login;
    }
}