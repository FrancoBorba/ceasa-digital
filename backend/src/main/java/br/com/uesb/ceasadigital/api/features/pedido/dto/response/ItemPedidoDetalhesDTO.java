package br.com.uesb.ceasadigital.api.features.pedido.dto.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.uesb.ceasadigital.api.features.item_pedido.model.ItemPedido;

public class ItemPedidoDetalhesDTO {

  @JsonProperty("productId")
  private Long productId;

  @JsonProperty("name")
  private String name;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("quantity")
  private BigDecimal quantity;

  @JsonProperty("subTotal")
  private BigDecimal subTotal;

  public ItemPedidoDetalhesDTO() {
  }

  public ItemPedidoDetalhesDTO(ItemPedido item) {
    if (item != null) {
      if (item.getProduto() != null) {
        this.productId = item.getProduto().getId();
        this.name = item.getProduto().getNome();
      } else {
        this.productId = null;
        this.name = "Produto sem informação";
      }
      this.price = item.getPrecoUnitario();
      this.quantity = item.getQuantidade();
      this.subTotal = item.getPrecoTotal();
    }
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public void setQuantidade(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getSubTotal() {
    return subTotal;
  }

  public void setSubTotal(BigDecimal subTotal) {
    this.subTotal = subTotal;
  }
}

