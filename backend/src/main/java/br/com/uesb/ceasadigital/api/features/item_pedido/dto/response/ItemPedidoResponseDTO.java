package br.com.uesb.ceasadigital.api.features.item_pedido.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.uesb.ceasadigital.api.features.item_pedido.model.ItemPedido;

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

  @JsonProperty("preco_unitario")
  private BigDecimal preco_unitario;

  @JsonProperty("criado_em")
  private Instant criado_em;

  @JsonProperty("atualizado_em")
  private Instant atualizado_em;

  public ItemPedidoResponseDTO(ItemPedido item) {
    this.id = item.getId();
    this.pedido_id = item.getPedido().getId();
    this.oferta_id = item.getOferta().getId();
    this.quantidade = item.getQuantidade();
    this.preco_unitario = item.getPrecoUnitario();
    this.criado_em = item.getCriadoEm();
    this.atualizado_em = item.getAtualizadoEm();
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

  public BigDecimal getPreco_unitario() {
    return preco_unitario;
  }

  public void setPreco_unitario(BigDecimal preco_unitario) {
    this.preco_unitario = preco_unitario;
  }

  public Instant getCriado_em() {
    return criado_em;
  }

  public void setCriado_em(Instant criado_em) {
    this.criado_em = criado_em;
  }

  public Instant getAtualizado_em() {
    return atualizado_em;
  }

  public void setAtualizado_em(Instant atualizado_em) {
    this.atualizado_em = atualizado_em;
  }
}
