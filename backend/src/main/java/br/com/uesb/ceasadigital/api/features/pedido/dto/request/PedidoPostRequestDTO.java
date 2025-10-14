package br.com.uesb.ceasadigital.api.features.pedido.dto.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public class PedidoPostRequestDTO {
  
  @NotNull(message = "Total Value is required")
  @DecimalMin(value = "0.01", message = "Total Value must be greater than 0")
  @DecimalMax(value = "9999999999.99", message = "Total Value must be less than 10 billion")
  @Digits(integer = 10, fraction = 2, message = "Total Value must have a maximum of 10 integer digits and 2 decimal digits")
  @JsonProperty("valor_total")
  private BigDecimal valorTotal;

  public PedidoPostRequestDTO() {
  }

  public PedidoPostRequestDTO(BigDecimal valorTotal) {
    this.valorTotal = valorTotal;
  }

  public BigDecimal getValorTotal() {
    return valorTotal;
  }

  public void setValorTotal(BigDecimal valorTotal) {
    this.valorTotal = valorTotal;
  }
}
