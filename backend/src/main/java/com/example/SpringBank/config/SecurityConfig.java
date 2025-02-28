package com.example.SpringBank.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // Desabilita o CSRF (necessário para facilitar os testes)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/usuario/**").permitAll()  // Permite acesso a /usuario sem autenticação
                .anyRequest().authenticated()  // Exige autenticação para outros endpoints
            );
        return http.build();
    }
}