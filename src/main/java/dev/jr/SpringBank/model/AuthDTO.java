package dev.jr.SpringBank.model;

public record AuthDTO(String login, String password) {
    public AuthDTO {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login não pode ser vazio ou nulo!");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Senha não pode ser vazia ou nula!");
        }
    }

} 