package com.example.SpringBank.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.SpringBank.model.Usuario;
import com.example.SpringBank.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Criar um novo usuário
    @PostMapping("/criar")
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.criarUsuario(usuario);
    }

    // Listar todos os usuários
    @GetMapping("/listar")
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public Usuario buscarUsuarioPorId(@PathVariable String id) {
        return usuarioService.buscarUsuarioPorId(id);
    }

    // Atualizar usuário por ID
    @PutMapping("/atualizar/{id}")
    public Usuario atualizarUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        return usuarioService.atualizarUsuario(id, usuario);
    }

    // Depositar saldo
    @PutMapping("/depositar/{id}/{valor}")
    public Usuario depositar(@PathVariable String id, @PathVariable double valor) {
        return usuarioService.depositar(id, valor);
    }

    // Sacar saldo
    @PutMapping("/sacar/{id}/{valor}")
    public Usuario sacar(@PathVariable String id, @PathVariable double valor) {
        return usuarioService.sacar(id, valor);
    }

    // Transferir saldo entre usuários
    @PostMapping("/transferir/{origemId}/{destinoId}/{valor}")
    public Usuario transferir(
            @PathVariable String origemId,
            @PathVariable String destinoId,
            @PathVariable double valor) {
        return usuarioService.transferir(origemId, destinoId, valor);
    }

    // Excluir usuário por ID
    @DeleteMapping("/excluir/{id}")
    public void excluirUsuario(@PathVariable String id) {
        usuarioService.excluirUsuario(id);
    }

}