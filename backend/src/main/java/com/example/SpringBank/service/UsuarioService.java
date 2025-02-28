package com.example.SpringBank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.SpringBank.model.Usuario;
import com.example.SpringBank.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario depositar(String id, double valor) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setSaldo(usuario.getSaldo() + valor);
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuário não encontrado!");
    }

    public Usuario sacar(String id, double valor) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.getSaldo() >= valor) {
                usuario.setSaldo(usuario.getSaldo() - valor);
                return usuarioRepository.save(usuario);
            }
            throw new RuntimeException("Saldo insuficiente!");
        }
        throw new RuntimeException("Usuário não encontrado!");
    }

    public Usuario transferir(String idOrigem, String idDestino, double valor) {
        Optional<Usuario> origemOptional = usuarioRepository.findById(idOrigem);
        Optional<Usuario> destinoOptional = usuarioRepository.findById(idDestino);

        if (origemOptional.isPresent() && destinoOptional.isPresent()) {
            Usuario usuarioOrigem = origemOptional.get();
            Usuario usuarioDestino = destinoOptional.get();

            if (usuarioOrigem.getSaldo() >= valor) {
                usuarioOrigem.setSaldo(usuarioOrigem.getSaldo() - valor);
                usuarioDestino.setSaldo(usuarioDestino.getSaldo() + valor);

                usuarioRepository.save(usuarioOrigem);
                return usuarioRepository.save(usuarioDestino);
            }
            throw new RuntimeException("Saldo insuficiente para a transferência!");
        }
        throw new RuntimeException("Um ou ambos os usuários não foram encontrados!");
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(String id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public Usuario atualizarUsuario(String id, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setCPF(usuarioAtualizado.getCPF());
            usuario.setSaldo(usuarioAtualizado.getSaldo());
            usuario.setSenha(usuarioAtualizado.getSenha());

            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuário não encontrado!");
    }

    public void excluirUsuario(String id) {
        usuarioRepository.deleteById(id);
    }
}