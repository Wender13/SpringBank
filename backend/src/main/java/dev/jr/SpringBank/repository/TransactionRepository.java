package dev.jr.SpringBank.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import dev.jr.SpringBank.model.Transaction;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    // Consultar transações onde o usuário remetente (user.id) seja igual ao usuárioId fornecido
    List<Transaction> findByUser(String userLogin);

    // Consultar transações onde o usuário destinatário (benefited.id) seja igual ao usuarioId fornecido
    List<Transaction> findByBeneficiary(String beneficiaryLogin);
}
