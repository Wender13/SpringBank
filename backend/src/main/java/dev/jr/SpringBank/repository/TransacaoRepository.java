package dev.jr.SpringBank.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import dev.jr.SpringBank.model.Transacao;
import java.util.List;

public interface TransacaoRepository extends MongoRepository<Transacao, String> {
    // Consultar transações onde o usuário remetente (usuario.id) seja igual ao usuárioId fornecido
    List<Transacao> findByUsuario_Id(String usuarioId);

    // Consultar transações onde o usuário destinatário (destino.id) seja igual ao usuarioId fornecido
    List<Transacao> findByDestinatario_Id(String destinoId);
}
