package com.example.SpringBank.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {
    private String Id;
    private String Nome;
    private String Email;
    private String CPF;
    private double Saldo;
    private String Senha;
    
    public Usuario() {
    }

    public Usuario(String id, String nome, String email, String CPF, double saldo, String senha) {
        Id = id;
        Nome = nome;
        Email = email;
        CPF = CPF;
        Saldo = saldo;
        Senha = senha;
    }
    

    /**
     * @return String return the Id
     */
    public String getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * @return String return the Nome
     */
    public String getNome() {
        return Nome;
    }

    /**
     * @param Nome the Nome to set
     */
    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    /**
     * @return String return the Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * @param Email the Email to set
     */
    public void setEmail(String Email) {
        this.Email = Email;
    }

    /**
     * @return String return the CPF
     */
    public String getCPF() {
        return CPF;
    }

    /**
     * @param CPF the CPF to set
     */
    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    /**
     * @return double return the Saldo
     */
    public double getSaldo() {
        return Saldo;
    }

    /**
     * @param Saldo the Saldo to set
     */
    public void setSaldo(double Saldo) {
        this.Saldo = Saldo;
    }

    /**
     * @return String return the Senha
     */
    public String getSenha() {
        return Senha;
    }

    /**
     * @param Senha the Senha to set
     */
    public void setSenha(String Senha) {
        this.Senha = Senha;
    }
}