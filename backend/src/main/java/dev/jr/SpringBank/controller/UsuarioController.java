package dev.jr.SpringBank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dev.jr.SpringBank.model.User;
import dev.jr.SpringBank.service.UserService;

@RestController
@RequestMapping("/users")
public class UsuarioController {
    
    //Injeção de dependência do serviço
    @Autowired
    private UserService usuarioService;

    // Criar um novo usuário
    @PostMapping("/create")
    public User criarUsuario(@RequestBody User usuario) {
        return usuarioService.createUser(usuario);
    }

    // Listar todos os usuários
    @GetMapping("/list")
    public List<User> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public User buscarUsuarioPorId(@PathVariable String id) {
        return usuarioService.buscarUsuarioPorId(id);
    }

    // Atualizar usuário por ID
    @PutMapping("/change/{id}")
    public User atualizarUsuario(@PathVariable String id, @RequestBody User usuario) {
        return usuarioService.atualizarUsuario(id, usuario);
    }

    // Depositar saldo
    @PutMapping("/deposit/{id}/{value}")
    public User depositar(@PathVariable String id, @PathVariable double valor) {
        return usuarioService.depositar(id, valor);
    }

    // Sacar saldo
    @PutMapping("/withdraw/{id}/{value}")
    public User sacar(@PathVariable String id, @PathVariable double valor) {
        return usuarioService.sacar(id, valor);
    }

    // Transferir saldo entre usuários
    @PostMapping("/transfer/{originId}/{destinationId}/{value}")
    public User transferir(
            @PathVariable String origemId,
            @PathVariable String destinoId,
            @PathVariable double valor) {
        return usuarioService.transferir(origemId, destinoId, valor);
    }

    // Excluir usuário por ID
    @DeleteMapping("/delete/{id}")
    public void excluirUsuario(@PathVariable String id) {
        usuarioService.excluirUsuario(id);
    }

}