package br.com.uesb.ceasadigital.api.features.oferta_produtor.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.dto.response.OfertaProdutorResponseDTO;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor;

@Mapper(componentModel = "spring")
public interface OfertaProdutorMapper {
  
  @Mapping(source = "metaEstoque.id", target = "metaEstoqueId")
  @Mapping(source = "metaEstoque.produto.nome", target = "nomeProduto")
  @Mapping(source = "produtor.id", target = "produtorId")
  @Mapping(source = "metaEstoque.produto.fotoUrl", target = "fotoUrl")
  OfertaProdutorResponseDTO toResponseDTO(OfertaProdutor oferta);
  
  List<OfertaProdutorResponseDTO> toResponseDTOList(List<OfertaProdutor> ofertas);
}