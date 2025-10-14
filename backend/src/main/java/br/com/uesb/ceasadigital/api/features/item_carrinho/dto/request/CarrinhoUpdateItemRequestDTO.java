package br.com.uesb.ceasadigital.api.features.item_carrinho.dto.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CarrinhoUpdateItemRequestDTO {
  
   @NotNull(message = "A quantidade não pode ser nula.")
  @Positive(message = "A quantidade deve ser maior do que zero")
  @Schema(
    description = "Quantidade do produto númerico em sua unidade de medida",
    example = "2.5",
    requiredMode = Schema.RequiredMode.REQUIRED
  )
  BigDecimal quantidade; // pensei em BigDecimal pq como é kilo pode ser 1,5 kg 

  public BigDecimal getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(BigDecimal quantidade) {
    this.quantidade = quantidade;
  }
}
