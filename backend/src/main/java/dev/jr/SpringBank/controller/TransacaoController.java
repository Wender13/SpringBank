package dev.jr.SpringBank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dev.jr.SpringBank.service.TransacaoService;
import dev.jr.SpringBank.model.Transacao;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Transacao> listarTransacoesPorUsuario(@PathVariable String usuarioId) {
        return transacaoService.listarTransacoesPorUsuario(usuarioId);
    }
}
