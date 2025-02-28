package com.example.SpringBank.repository;

import com.example.SpringBank.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Usuario findByEmail(String email);
    Usuario findByCPF(String cpf);
    Usuario findByEmailAndSenha(String email, String senha);
    Usuario findByCPFAndSenha(String cpf, String senha);
    Usuario findByEmailAndCPF(String email, String cpf);
    Usuario findByEmailAndSenhaAndCPF(String email, String senha, String cpf);   
}