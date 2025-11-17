package br.com.uesb.ceasadigital.api.features.item_lista.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.uesb.ceasadigital.api.features.item_lista.dto.response.ItemListaResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_lista.model.ItemLista;

@Mapper(componentModel = "spring")
public interface ItemListaMapper {

  @Mapping(source = "produto.id", target = "produtoId")
  @Mapping(source = "produto.nome", target = "nomeDoProduto")
  ItemListaResponseDTO toResponseDTO(ItemLista itemLista);
}