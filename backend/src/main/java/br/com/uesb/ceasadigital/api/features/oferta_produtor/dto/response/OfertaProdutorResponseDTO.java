package br.com.uesb.ceasadigital.api.features.oferta_produtor.dto.response;

import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.enums.OfertaStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "DTO de resposta para uma Oferta do Produtor (Meta de Estoque atribuída)")
public class OfertaProdutorResponseDTO {
  
  @Schema(description = "ID da Oferta/Meta", example = "101")
  private Long id;
  
  @Schema(description = "ID da Meta de Estoque (Estoque Virtual) à qual esta oferta pertence", example = "10")
  private Long metaEstoqueId;
  
  @Schema(description = "Nome do produto desta meta", example = "Tomate Italiano")
  private String nomeProduto;
  
  @Schema(description = "ID do produtor", example = "1")
  private Long produtorId;
  
  @Schema(description = "Quantidade que o produtor deve fornecer (em KG)", example = "2.000")
  private BigDecimal quantidadeOfertada;
  
  @Schema(description = "Quantidade ainda disponível para venda (em KG)", example = "2.000")
  private BigDecimal quantidadeDisponivel;
  
  @Schema(description = "Quantidade já vendida (em KG)", example = "0.000")
  private BigDecimal totalVolumeVendido;
  
  @Schema(description = "Status da oferta/meta", example = "PENDENTE")
  private OfertaStatus status;
  
  @Schema(description = "Data de criação da oferta")
  private LocalDateTime criadoEm;

  @Schema(description = "URL da foto do produto", example = "/produtos/tomate.jpg")
  private String fotoUrl;
  
  // Getters e Setters
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getMetaEstoqueId() {
    return metaEstoqueId;
  }
  
  public void setMetaEstoqueId(Long metaEstoqueId) {
    this.metaEstoqueId = metaEstoqueId;
  }
  
  public String getNomeProduto() {
    return nomeProduto;
  }
  
  public void setNomeProduto(String nomeProduto) {
    this.nomeProduto = nomeProduto;
  }
  
  public Long getProdutorId() {
    return produtorId;
  }
  
  public void setProdutorId(Long produtorId) {
    this.produtorId = produtorId;
  }
  
  public BigDecimal getQuantidadeOfertada() {
    return quantidadeOfertada;
  }
  
  public void setQuantidadeOfertada(BigDecimal quantidadeOfertada) {
    this.quantidadeOfertada = quantidadeOfertada;
  }
  
  public BigDecimal getQuantidadeDisponivel() {
    return quantidadeDisponivel;
  }
  
  public void setQuantidadeDisponivel(BigDecimal quantidadeDisponivel) {
    this.quantidadeDisponivel = quantidadeDisponivel;
  }
  
  public BigDecimal getTotalVolumeVendido() {
    return totalVolumeVendido;
  }
  
  public void setTotalVolumeVendido(BigDecimal totalVolumeVendido) {
    this.totalVolumeVendido = totalVolumeVendido;
  }
  
  public OfertaStatus getStatus() {
    return status;
  }
  
  public void setStatus(OfertaStatus status) {
    this.status = status;
  }
  
  public LocalDateTime getCriadoEm() {
    return criadoEm;
  }
  
  public void setCriadoEm(LocalDateTime criadoEm) {
    this.criadoEm = criadoEm;
  }

  public String getFotoUrl() {
    return fotoUrl;
  }

  public void setFotoUrl(String fotoUrl) {
    this.fotoUrl = fotoUrl;
  }
}