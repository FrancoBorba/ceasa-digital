package br.com.uesb.ceasadigital.api.features.pedido.dto.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.uesb.ceasadigital.api.features.pedido.model.enums.PedidoStatus;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

public class PedidoPutRequestDTO {

  private Long id;

  @DecimalMin(value = "0.01", message = "Valor Total must be greater than 0")
  @DecimalMax(value = "9999999999.99", message = "Valor Total must be less than 10 billion")
  @Digits(integer = 10, fraction = 2, message = "Valor Total must have a maximum of 10 integer digits and 2 decimal digits")
  @JsonProperty("valor_total")
  private BigDecimal valorTotal;

  private PedidoStatus status;

  public PedidoPutRequestDTO() {
  }

  public PedidoPutRequestDTO(Long id, BigDecimal valorTotal, PedidoStatus status) {
    this.id = id;
    this.valorTotal = valorTotal;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public BigDecimal getValorTotal() {
    return valorTotal;
  }

  public void setValorTotal(BigDecimal valorTotal) {
    this.valorTotal = valorTotal;
  }

  public PedidoStatus getStatus() {
    return status;
  }

  public void setStatus(PedidoStatus status) {
    this.status = status;
  }


}
