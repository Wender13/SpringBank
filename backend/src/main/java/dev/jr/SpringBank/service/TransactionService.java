package dev.jr.SpringBank.service;

import org.springframework.stereotype.Service;
import dev.jr.SpringBank.model.Transaction;
import dev.jr.SpringBank.repository.TransactionRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void registerTransaction(Transaction transaction) {
        this.transactionRepository.save(transaction);
    }

    public List<Transaction> listTransactionsUser(String userId) {
        // Buscando as transações do usuário como remetente ou destinatário
        List<Transaction> transactionOrigin = transactionRepository.findByUsuario_Id(userId);
        List<Transaction> transactionDestination = transactionRepository.findByDestinatario_Id(userId);

        // Combinando as transações (remetente + destinatário)
        transactionOrigin.addAll(transactionDestination);
        return transactionOrigin;
    }

}