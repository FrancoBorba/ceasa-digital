package br.com.uesb.ceasadigital.api.features.item_pedido.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

/*
 * @author: Caíque Santos Santana
 * @date: 14/10/2023
 * @description: DTO para requisições de atualização de ItemPedido.
 * A data de atualização é gerenciada automaticamente na entidade @ItemPedido.
*/
public class ItemPedidoPutRequestDTO {

  @NotNull(message = "ID is required")
  private Long id;

  @NotNull(message = "Quantity is required")
  @Digits(integer = 10, fraction = 0, message = "Quantity must have a maximum of 10 integer digits and no decimal digits")
  private BigDecimal quantidade;

  @NotNull(message = "Unit Price is required")
  @DecimalMin(value = "0.01", inclusive = false, message = "Unit Price must be greater than zero")
  @DecimalMax(value = "9999999999.99", message = "Unit Price must be less than 10 billion")
  @Digits(integer = 10, fraction = 2, message = "Unit Price must have a maximum of 10 integer digits and 2 decimal digits")
  private BigDecimal precoUnitario;

  public ItemPedidoPutRequestDTO() {
  }

  public ItemPedidoPutRequestDTO(Long id, BigDecimal quantidade, BigDecimal precoUnitario) {
    this.id = id;
    this.quantidade = quantidade;
    this.precoUnitario = precoUnitario;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(BigDecimal quantidade) {
    this.quantidade = quantidade;
  }

  public BigDecimal getPrecoUnitario() {
    return precoUnitario;
  }

  public void setPrecoUnitario(BigDecimal precoUnitario) {
    this.precoUnitario = precoUnitario;
  }
}
