package br.com.uesb.ceasadigital.api.features.pedido.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class FinalizarCarrinhoRequestDTO {

  @NotNull(message = "O ID do endereço é obrigatório")
  @Schema(description = "ID do endereço de entrega", example = "1", required = true)
  @JsonProperty("endereco_id")
  private Long enderecoId;

  @NotNull(message = "O ID do carrinho é obrigatório")
  @Schema(description = "ID do carrinho a ser finalizado", example = "1", required = true)
  @JsonProperty("carrinho_id")
  private Long carrinhoId;

  public FinalizarCarrinhoRequestDTO() {
  }

  public Long getEnderecoId() {
    return enderecoId;
  }

  public void setEnderecoId(Long enderecoId) {
    this.enderecoId = enderecoId;
  }

  public Long getCarrinhoId() {
    return carrinhoId;
  }

  public void setCarrinhoId(Long carrinhoId) {
    this.carrinhoId = carrinhoId;
  }
}

