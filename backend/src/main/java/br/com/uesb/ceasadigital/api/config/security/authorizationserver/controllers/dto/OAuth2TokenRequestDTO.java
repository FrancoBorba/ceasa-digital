package br.com.uesb.ceasadigital.api.config.security.authorizationserver.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request para autenticação com username e password")
public class OAuth2TokenRequestDTO {

    @Schema(description = "Nome de usuário (email)", example = "usuario@email.com", required = true)
    private String username;

    @Schema(description = "Senha do usuário", example = "senha123", required = true)
    private String password;

    @Schema(description = "Tipo de grant do OAuth2", example = "password", required = true, allowableValues = {"password"})
    private String grant_type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}

