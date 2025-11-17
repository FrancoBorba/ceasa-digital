package br.com.uesb.ceasadigital.api.features.entregador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget; // Import necessário para update
import br.com.uesb.ceasadigital.api.features.entregador.dto.EntregadorRequestDTO;
import br.com.uesb.ceasadigital.api.features.entregador.dto.EntregadorResponseDTO;
import br.com.uesb.ceasadigital.api.features.entregador.model.Entregador;
import br.com.uesb.ceasadigital.api.features.user.mapper.UserMapper; // Import do UserMapper

@Mapper(componentModel = "spring", uses = {UserMapper.class}) // Usa o UserMapper para mapear o UserMinDTO
public interface EntregadorMapper {

    @Mapping(source = "usuario", target = "usuario") // Mapeia o objeto User para UserMinDTO
    EntregadorResponseDTO toResponseDTO(Entregador entity);

    // Mapeamento de DTO para Entidade (ignora campos que não vêm do DTO)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    Entregador toEntity(EntregadorRequestDTO dto);

    // Método para atualizar uma entidade existente com dados do DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true) // atualizadoEm é gerenciado pelo @UpdateTimestamp
    void updateEntityFromDto(EntregadorRequestDTO dto, @MappingTarget Entregador entity);
}