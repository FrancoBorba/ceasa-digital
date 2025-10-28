package br.com.uesb.ceasadigital.api.features.user.mapper;

import org.mapstruct.Mapper;

import br.com.uesb.ceasadigital.api.features.user.dto.request.UserRegisterDTO;
import br.com.uesb.ceasadigital.api.features.user.dto.response.UserResponseDTO;
import br.com.uesb.ceasadigital.api.features.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    public User toEntity(UserRegisterDTO user);
}
