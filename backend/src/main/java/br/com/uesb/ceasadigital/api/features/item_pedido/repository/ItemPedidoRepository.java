package br.com.uesb.ceasadigital.api.features.item_pedido.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.uesb.ceasadigital.api.features.item_pedido.model.ItemPedido;

/**
 * @author: Caíque Santos Santana
 * @date: 14/10/2023
 * @description: Repositório para a entidade ItemPedido.
 */
@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

  Page<ItemPedido> findAllByPedidoUsuarioId(Long usuarioId, Pageable pageable);

  Page<ItemPedido> findAllByPedidoId(Long pedidoId, Pageable pageable);

}
