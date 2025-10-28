package br.com.uesb.ceasadigital.api.features.produtor_produto.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProdutorProdutoRequestVendaDTO {
    @NotNull(message = "A lista de IDs de produtos é obrigatória.")
    @NotEmpty(message = "A lista de IDs de produtos não pode estar vazia.")
    @Schema(description = "Lista de IDs dos produtos que o produtor deseja solicitar permissão para vender.", example = "[1, 5, 9]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> produtosIds;

    public List<Long> getProdutosIds() {
        return produtosIds;
    }

    public void setProdutosIds(List<Long> produtosIds) {
        this.produtosIds = produtosIds;
    }
}
