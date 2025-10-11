package br.com.uesb.ceasadigital.api.features.item_carrinho.mapper;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.response.CarrinhoItemResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.model.ItemCarrinho;

@Mapper(componentModel = "spring")
public interface ItemCarrinhoMapper {
  


    @Mapping(source = "precoUnitarioArmazenado", target = "precoUnitario")
    @Mapping(target = "subTotal", ignore = true)
    CarrinhoItemResponseDTO toResponseDTO(ItemCarrinho itemCarrinho);
    
  
  
}
