package br.com.uesb.ceasadigital.api.features.item_pedido.dto.response;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author: Caíque Santos Santana
 * @date: 14/10/2023
 * @description: DTO para respostas que representam um ItemPedido.
 */
public class ItemPedidoResponseDTO {

  private Long id;

  @JsonProperty("pedido_id")
  private Long pedido_id;

  @JsonProperty("oferta_id")
  private Long oferta_id;

  @JsonProperty("quantidade")
  private BigDecimal quantidade;

  @JsonProperty("preco_total_do_item")
  private BigDecimal precoTotal;

  public ItemPedidoResponseDTO() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPedido_id() {
    return pedido_id;
  }

  public void setPedido_id(Long pedido_id) {
    this.pedido_id = pedido_id;
  }

  public Long getOferta_id() {
    return oferta_id;
  }

  public void setOferta_id(Long oferta_id) {
    this.oferta_id = oferta_id;
  }

  public BigDecimal getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(BigDecimal quantidade) {
    this.quantidade = quantidade;
  }

  public BigDecimal getPrecoTotal() {
    return precoTotal;
  }

  public void setPrecoTotal(BigDecimal precoTotal) {
    this.precoTotal = precoTotal;
  }
}
