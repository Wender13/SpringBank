package dev.jr.SpringBank.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import dev.jr.SpringBank.model.Transaction;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    // Consultar transações onde o usuário remetente (usuario.id) seja igual ao usuárioId fornecido
    List<Transaction> findByUsuario_Id(String usuarioId);

    // Consultar transações onde o usuário destinatário (destino.id) seja igual ao usuarioId fornecido
    List<Transaction> findByDestinatario_Id(String destinoId);
}
