package br.com.uesb.ceasadigital.api.features.estoque.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EstoqueVirtualResponseDTO {
  
  private Long id;
  private Long produtoId;
  private String nomeProduto;
  private Long adminCriadorId;
  private String nomeAdminCriador;
  private BigDecimal quantidadeMeta;
  private String status;
  private LocalDateTime criadoEm;
  private LocalDateTime atualizadoEm;
  
  // Getters e Setters
  
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
  
  public String getNomeProduto() {
    return nomeProduto;
  }
  
  public void setNomeProduto(String nomeProduto) {
    this.nomeProduto = nomeProduto;
  }
  
  public Long getAdminCriadorId() {
    return adminCriadorId;
  }
  
  public void setAdminCriadorId(Long adminCriadorId) {
    this.adminCriadorId = adminCriadorId;
  }
  
  public String getNomeAdminCriador() {
    return nomeAdminCriador;
  }
  
  public void setNomeAdminCriador(String nomeAdminCriador) {
    this.nomeAdminCriador = nomeAdminCriador;
  }
  
  public BigDecimal getQuantidadeMeta() {
    return quantidadeMeta;
  }
  
  public void setQuantidadeMeta(BigDecimal quantidadeMeta) {
    this.quantidadeMeta = quantidadeMeta;
  }
  
  public String getStatus() {
    return status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public LocalDateTime getCriadoEm() {
    return criadoEm;
  }
  
  public void setCriadoEm(LocalDateTime criadoEm) {
    this.criadoEm = criadoEm;
  }
  
  public LocalDateTime getAtualizadoEm() {
    return atualizadoEm;
  }
  
  public void setAtualizadoEm(LocalDateTime atualizadoEm) {
    this.atualizadoEm = atualizadoEm;
  }
}