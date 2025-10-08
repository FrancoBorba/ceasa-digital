package br.com.uesb.ceasadigital.api.config.security.authorizationserver.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request para renovação de token usando refresh_token")
public class OAuth2RefreshTokenRequestDTO {

    @Schema(description = "Token de renovação obtido no login", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", required = true)
    private String refresh_token;

    @Schema(description = "Tipo de grant do OAuth2", example = "refresh_token", required = true, allowableValues = {"refresh_token"})
    private String grant_type;

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}

