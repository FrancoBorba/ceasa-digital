package br.com.uesb.ceasadigital.api.features.item_pedido.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.com.uesb.ceasadigital.api.features.item_pedido.dto.response.ItemPedidoResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_pedido.model.ItemPedido;

/**
 * @author: Caíque Santos Santana
 * @date: 14/10/2023
 * @description: Mapper para converter entre ItemPedido e seus DTOs.
 */
@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

  @Mapping(source = "pedido.id", target = "pedido_id")
  @Mapping(source = "precoUnitario", target = "preco_unitario")
  @Mapping(source = "quantidade", target = "quantidade")
  @Mapping(source = "criadoEm", target = "criado_em")
  @Mapping(source = "atualizadoEm", target = "atualizado_em")
  @Mapping(source = "oferta.id", target = "oferta_id")
  ItemPedidoResponseDTO toResponseDTO(ItemPedido item);

}