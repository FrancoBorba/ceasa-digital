package br.com.uesb.ceasadigital.api.features.estoque.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class EstoqueVirtualRequestDTO {
  
  @NotNull(message = "O ID do produto é obrigatório.")
  private Long produtoId;
  
  @NotNull(message = "A quantidade da meta é obrigatória.")
  @Positive(message = "A quantidade da meta deve ser um valor positivo.")
  private BigDecimal quantidadeMeta;
  
  // Getters e Setters
  
  public Long getProdutoId() {
    return produtoId;
  }
  
  public void setProdutoId(Long produtoId) {
    this.produtoId = produtoId;
  }
  
  public BigDecimal getQuantidadeMeta() {
    return quantidadeMeta;
  }
  
  public void setQuantidadeMeta(BigDecimal quantidadeMeta) {
    this.quantidadeMeta = quantidadeMeta;
  }
}