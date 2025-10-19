package br.com.uesb.ceasadigital.api.features.item_pedido.dto.request;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

/**
 * @author: Caíque Santos Santana
 * @date: 14/10/2023
 * @description: DTO para requisições de criação de ItemPedido.
 *               A data de criação é gerenciada automaticamente na
 *               entidade @ItemPedido.
 */
public class ItemPedidoPostRequestDTO {

  @NotNull(message = "Order ID is required")
  @JsonProperty("pedido_id")
  private Long pedidoId;

  @NotNull(message = "Offer ID is required")
  @JsonProperty("oferta_id")
  private Long ofertaId;

  @NotNull(message = "Quantity is required")
  @Digits(integer = 10, fraction = 3, message = "Quantity must have a maximum of 10 integer digits and 3 decimal digits")
  private BigDecimal quantidade;

  @NotNull(message = "Unit Price is required")
  @DecimalMin(value = "0.01", inclusive = false, message = "Unit Price must be greater than zero")
  @DecimalMax(value = "9999999999.99", message = "Unit Price must be less than 10 billion")
  @Digits(integer = 10, fraction = 2, message = "Unit Price must have a maximum of 10 integer digits and 2 decimal digits")
  @JsonProperty("preco_unitario")
  private BigDecimal precoUnitario;

  public ItemPedidoPostRequestDTO() {
  }

  public ItemPedidoPostRequestDTO(Long pedidoId, Long ofertaId, BigDecimal quantidade, BigDecimal precoUnitario) {
    this.pedidoId = pedidoId;
    this.ofertaId = ofertaId;
    this.quantidade = quantidade;
    this.precoUnitario = precoUnitario;
  }

  public Long getPedidoId() {
    return pedidoId;
  }

  public void setPedidoId(Long pedidoId) {
    this.pedidoId = pedidoId;
  }

  public Long getOfertaId() {
    return ofertaId;
  }

  public void setOfertaId(Long ofertaId) {
    this.ofertaId = ofertaId;
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
