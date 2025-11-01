package br.com.uesb.ceasadigital.api.features.estoque.mapper;

import br.com.uesb.ceasadigital.api.features.estoque.dto.response.EstoqueVirtualResponseDTO;
import br.com.uesb.ceasadigital.api.features.estoque.model.EstoqueVirtual;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EstoqueVirtualMapper {
  
  @Mapping(source = "produto.id", target = "produtoId")
  @Mapping(source = "produto.nome", target = "nomeProduto")
  @Mapping(source = "adminCriador.id", target = "adminCriadorId")
  @Mapping(source = "adminCriador.name", target = "nomeAdminCriador")
  EstoqueVirtualResponseDTO toResponseDTO(EstoqueVirtual estoqueVirtual);
  
  List<EstoqueVirtualResponseDTO> toResponseDTOList(List<EstoqueVirtual> estoques);
}