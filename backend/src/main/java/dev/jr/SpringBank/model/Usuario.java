package dev.jr.SpringBank.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Document(collection = "usuarios")
public class Usuario {
    
    @Id  // Define o identificador no MongoDB
    private String id;

    @NotBlank(message = "O nome é obrigatório!")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório!")
    @Email(message = "Formato de e-mail inválido!")
    @Indexed(unique = true) // Garante unicidade no MongoDB
    private String email;

    @NotBlank(message = "O CPF é obrigatório!")
    @Indexed(unique = true) // Garante unicidade no MongoDB
    private String CPF;

    @NotBlank(message = "A senha é obrigatória!")
    private String senha;

    private double saldo = 0.0; // Inicializa com saldo zero

    // Construtor sem parâmetros
    public Usuario() {
    }

    // Construtor completo
    public Usuario(String id, String nome, String email, String CPF, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.CPF = CPF;
        this.senha = senha;
    }
    
    //Construtor parcial sem id
    public Usuario(String nome, String email, String CPF, String senha) {
        this.nome = nome;
        this.email = email;
        this.CPF = CPF;
        this.senha = senha;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", CPF=" + CPF + ", saldo=" + saldo
                + ", senha=" + senha + "]";
    }
}