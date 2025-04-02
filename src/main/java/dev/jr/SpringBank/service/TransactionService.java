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

    public List<Transaction> listTransactionsUser(String userLogin) {
        // Buscando as transações do usuário como remetente ou destinatário
        List<Transaction> transactionOrigin = transactionRepository.findByUser(userLogin);
        List<Transaction> transactionDestination = transactionRepository.findByBeneficiary(userLogin);

        // Combinando as transações (remetente + destinatário)
        transactionOrigin.addAll(transactionDestination);
        return transactionOrigin;
    }

    public void updateUserTransactions(String oldLogin, String newLogin) {
        // Busca as transações onde o usuário é remetente ou destinatário
        List<Transaction> transactionsAsUser = transactionRepository.findByUser(oldLogin);
        List<Transaction> transactionsAsBeneficiary = transactionRepository.findByBeneficiary(oldLogin);
    
        // Atualiza o login nas transações encontradas
        for (Transaction transaction : transactionsAsUser) {
            transaction.setUser(newLogin);
        }
        for (Transaction transaction : transactionsAsBeneficiary) {
            transaction.setBeneficiary(newLogin);
        }
    
        // Salva as transações atualizadas
        transactionRepository.saveAll(transactionsAsUser);
        transactionRepository.saveAll(transactionsAsBeneficiary);
    }  

}