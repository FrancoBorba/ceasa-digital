package br.com.uesb.ceasadigital.api.features.carrinho.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.uesb.ceasadigital.api.features.carrinho.dto.response.CarrinhoResponseDTO;
import br.com.uesb.ceasadigital.api.features.carrinho.model.Carrinho;
import br.com.uesb.ceasadigital.api.features.item_carrinho.mapper.ItemCarrinhoMapper;

@Mapper(componentModel = "spring", uses = {ItemCarrinhoMapper.class})
public interface CarrinhoMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.name", target = "usuarioNome")
    @Mapping(source = "itens", target = "itens") // Mapeia a lista de itens diretamente
    CarrinhoResponseDTO toResponseDTO(Carrinho carrinho);
}
