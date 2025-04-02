package dev.jr.SpringBank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(csrf -> csrf.disable())  // Desabilitando a proteção CSRF
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Configurando para não usar sessão
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()  // Permitir login sem autenticação
                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()  // Permitir registro sem autenticação
                .requestMatchers(HttpMethod.DELETE, "/users/delete/{login}").authenticated()  // Apenas admins podem deletar usuários
                .requestMatchers(HttpMethod.PUT, "/users").hasAnyRole("ADMIN", "USER")  // Administradores podem modificar qualquer conta, usuários podem modificar seus próprios dados
                .anyRequest().authenticated()  // Requer autenticação para todas as outras requisições
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)  // Adicionando o filtro de segurança antes do filtro padrão
            .build();  // Construindo a configuração de segurança
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}