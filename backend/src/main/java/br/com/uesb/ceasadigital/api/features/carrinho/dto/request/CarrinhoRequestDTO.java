package br.com.uesb.ceasadigital.api.features.carrinho.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class CarrinhoRequestDTO {

    @NotNull(message = "ID do usuário é obrigatório")
    @Schema(
        description = "ID do usuário proprietário do carrinho",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long usuarioId;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}