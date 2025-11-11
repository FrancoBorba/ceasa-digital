package br.com.uesb.ceasadigital.api.features.lista.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.uesb.ceasadigital.api.features.item_lista.dto.response.ItemListaResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_lista.model.ItemLista;
import br.com.uesb.ceasadigital.api.features.item_lista.mapper.ItemListaMapper;
import br.com.uesb.ceasadigital.api.features.lista.dto.response.ListaResponseDTO;
import br.com.uesb.ceasadigital.api.features.lista.model.Lista;

@Mapper(componentModel = "spring", uses = { ItemListaMapper.class })
public interface ListaMapper {

  @Mapping(source = "usuario.id", target = "usuarioId")
  @Mapping(source = "usuario.name", target = "usuarioNome")
  @Mapping(source = "descricao", target = "descricao")
  @Mapping(source = "itens", target = "itens")
  ListaResponseDTO toResponseDTO(Lista lista);

  List<ListaResponseDTO> toResponseDTOList(List<Lista> listas);
}