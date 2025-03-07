package dev.jr.SpringBank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.jr.SpringBank.model.Usuario;
import dev.jr.SpringBank.repository.UsuarioRepository;

import java.time.LocalDateTime;
import dev.jr.SpringBank.model.Transacao;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TransacaoService transacaoService;

    // Criar usuário
    public Usuario criarUsuario(Usuario usuario) {
        if (usuario.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório!");
        }
        if (usuario.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O e-mail é obrigatório!");
        }
        if (usuario.getCPF().isEmpty()) {
            throw new IllegalArgumentException("O CPF é obrigatório!");
        }
        if (usuario.getSenha().isEmpty()) {
            throw new IllegalArgumentException("A senha é obrigatória!");
        }
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado!");
        }
        if (usuarioRepository.findByCPF(usuario.getCPF()).isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado!");
        }

        return usuarioRepository.save(usuario);
    }

    // Atualizar usuário
    public Usuario atualizarUsuario(String id, Usuario usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // Verifica se o novo e-mail já pertence a outro usuário
        usuarioRepository.findByEmail(usuarioAtualizado.getEmail())
                .filter(u -> !u.getId().equals(id))
                .ifPresent(u -> { throw new IllegalArgumentException("E-mail já cadastrado!"); });

        // Verifica se o novo CPF já pertence a outro usuário
        usuarioRepository.findByCPF(usuarioAtualizado.getCPF())
                .filter(u -> !u.getId().equals(id))
                .ifPresent(u -> { throw new IllegalArgumentException("CPF já cadastrado!"); });

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setCPF(usuarioAtualizado.getCPF());
        usuario.setSenha(usuarioAtualizado.getSenha());

        return usuarioRepository.save(usuario);
    }

    // Depositar saldo
    public Usuario depositar(String id, double valor) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setSaldo(usuario.getSaldo() + valor);
            //Registro da transação
            transacaoService.registrarTransacao(new Transacao(usuario, "DEPOSITO", valor, LocalDateTime.now()));

            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuário não encontrado!");
    }
    
    // Sacar saldo
    public Usuario sacar(String id, double valor) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.getSaldo() >= valor) {
                usuario.setSaldo(usuario.getSaldo() - valor);
                //Registro da transação
                transacaoService.registrarTransacao(new Transacao(usuario, "SAQUE", -valor, LocalDateTime.now()));
                return usuarioRepository.save(usuario);
            }
            throw new RuntimeException("Saldo insuficiente!");
        }
        throw new RuntimeException("Usuário não encontrado!");
    }

    // Transferir saldo entre usuários
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

                //Registro da transação
                transacaoService.registrarTransacao(new Transacao(usuarioOrigem, usuarioDestino, valor, LocalDateTime.now()));
                return usuarioRepository.save(usuarioDestino);
            }
            throw new RuntimeException("Saldo insuficiente para a transferência!");
        }
        throw new RuntimeException("Um ou ambos os usuários não foram encontrados!");
    }

    // Listar usuários
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar usuário por ID
    public Usuario buscarUsuarioPorId(String id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }


    // Excluir usuário
    public void excluirUsuario(String id) {
        usuarioRepository.deleteById(id);
    }

    // Remover usuário por email
    public void removerUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o e-mail fornecido!"));
        usuarioRepository.deleteById(usuario.getId());
    }
}