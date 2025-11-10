package br.com.uesb.ceasadigital.api.features.item_lista.dto.response;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public class ItemListaResponseDTO {

  @Schema(description = "ID do item da lista", example = "1")
  private Long id;

  @Schema(description = "ID do produto", example = "10")
  private Long produtoId;

  @Schema(description = "Nome do produto", example = "Tomate")
  private String nomeDoProduto;

  @Schema(description = "Quantidade desejada", example = "2.500")
  private BigDecimal quantidade;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProdutoId() {
    return produtoId;
  }

  public void setProdutoId(Long produtoId) {
    this.produtoId = produtoId;
  }

  public String getNomeDoProduto() {
    return nomeDoProduto;
  }

  public void setNomeDoProduto(String nomeDoProduto) {
    this.nomeDoProduto = nomeDoProduto;
  }

  public BigDecimal getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(BigDecimal quantidade) {
    this.quantidade = quantidade;
  }
}