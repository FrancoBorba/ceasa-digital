package br.com.uesb.ceasadigital.api.features.pedido.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.uesb.ceasadigital.api.features.pedido.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findByIdAndUsuarioId(Long id, Long usuarioId);
    
    List<Pedido> findByUsuarioId(Long usuarioId);

    /**
     * Busca pedidos de um usuário específico com paginação
     * Permite ordenação por campos de tempo (dataPedido, criadoEm, atualizadoEm)
     */
    Page<Pedido> findByUsuarioId(Long usuarioId, Pageable pageable);

    /*
     * Busca o pedido pelo ID do pagamento
     */
    Optional<Pedido> findByTxidPagamento(String txidPagamento);

    /**
     * Busca todos os pedidos com paginação (para administradores)
     * Permite ordenação por campos de tempo (dataPedido, criadoEm, atualizadoEm)
     */
    Page<Pedido> findAll(Pageable pageable);

}
