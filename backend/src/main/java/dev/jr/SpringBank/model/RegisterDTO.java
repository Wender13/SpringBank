package dev.jr.SpringBank.model;

public record RegisterDTO(String login, String password, UserRole role, String name, String email, String cpf) {

}   