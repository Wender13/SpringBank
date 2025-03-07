package dev.jr.SpringBank.service;

import org.springframework.stereotype.Service;
import dev.jr.SpringBank.model.Transacao;
import dev.jr.SpringBank.repository.TransacaoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public void registrarTransacao(Transacao transacao) {
        this.transacaoRepository.save(transacao);
    }

    public List<Transacao> listarTransacoesPorUsuario(String usuarioId) {
        // Buscando as transações do usuário como remetente ou destinatário
        List<Transacao> transacoesOrigem = transacaoRepository.findByUsuario_Id(usuarioId);
        List<Transacao> transacoesDestino = transacaoRepository.findByDestinatario_Id(usuarioId);

        // Combinando as transações (remetente + destinatário)
        transacoesOrigem.addAll(transacoesDestino);
        return transacoesOrigem;
    }

}