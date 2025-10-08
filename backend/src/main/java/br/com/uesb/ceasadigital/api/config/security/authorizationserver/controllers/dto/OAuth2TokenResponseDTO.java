package br.com.uesb.ceasadigital.api.config.security.authorizationserver.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response contendo os tokens de acesso e renovação")
public class OAuth2TokenResponseDTO {

    @Schema(description = "Token de acesso JWT", example = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String access_token;

    @Schema(description = "Token de renovação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refresh_token;

    @Schema(description = "Tipo do token", example = "Bearer")
    private String token_type;

    @Schema(description = "Tempo de expiração do access_token em segundos", example = "3600")
    private Integer expires_in;

    @Schema(description = "Escopos do token", example = "read write")
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}

