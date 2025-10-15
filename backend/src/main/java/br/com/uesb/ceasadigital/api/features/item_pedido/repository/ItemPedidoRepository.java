package br.com.uesb.ceasadigital.api.features.item_pedido.repository;

import java.util.List;
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

  List<ItemPedido> findByPedidoUsuarioId(Long usuarioId);

}
