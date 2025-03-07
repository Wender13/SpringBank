package dev.jr.SpringBank.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import dev.jr.SpringBank.model.Usuario;


public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByCPF(String cpf); 
    Usuario findByEmailAndSenha(String email, String senha);
    Usuario findByCPFAndSenha(String cpf, String senha);
    Usuario findByEmailAndCPF(String email, String cpf);
    Usuario findByEmailAndSenhaAndCPF(String email, String senha, String cpf);   
    Optional<Usuario> findByEmail(String email);
}