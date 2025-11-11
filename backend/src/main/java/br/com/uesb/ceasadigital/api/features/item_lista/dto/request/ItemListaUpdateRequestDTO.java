package br.com.uesb.ceasadigital.api.features.item_lista.dto.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public class ItemListaUpdateRequestDTO {

  @NotNull
  @Digits(integer = 10, fraction = 3, message = "Quantidade deve ter no máximo 10 dígitos inteiros e 3 decimais")
  @Schema(description = "Quantidade desejada", example = "3.000")
  private BigDecimal quantidade;

  public BigDecimal getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(BigDecimal quantidade) {
    this.quantidade = quantidade;
  }
}