package br.com.uesb.ceasadigital.api.features.item_carrinho.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.request.CarrinhoAddItemRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.request.CarrinhoUpdateItemRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.response.CarrinhoItemResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.model.ItemCarrinho;

@Mapper(componentModel = "spring")
public interface ItemCarrinhoMapper {
  

    ItemCarrinho toEntity(CarrinhoAddItemRequestDTO requestDTO);

    CarrinhoItemResponseDTO toResponseDTO(ItemCarrinho itemCarrinho);

    void updateEntityFromDto(CarrinhoUpdateItemRequestDTO updateDTO, @MappingTarget ItemCarrinho itemCarrinho);

    List<CarrinhoItemResponseDTO> toResponseDTOList(List<ItemCarrinho> itemList);
  
}
