package br.com.uesb.ceasadigital.api.features.produtor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.uesb.ceasadigital.api.features.produtor.dto.request.ProdutorRequestDTO;
import br.com.uesb.ceasadigital.api.features.produtor.dto.response.ProdutorResponseDTO;
import br.com.uesb.ceasadigital.api.features.produtor.model.Produtor;

@Mapper(componentModel = "spring")
public interface ProdutorMapper {
    ProdutorResponseDTO toResponseDTO(Produtor entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true) 
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Mapping(source = "numeroDeIdentificacao", target = "numeroDeIdentificacao")
    @Mapping(source = "tipoDeIdentificacao", target = "tipoDeIdentificacao")
    Produtor toEntity(ProdutorRequestDTO dto);
}
