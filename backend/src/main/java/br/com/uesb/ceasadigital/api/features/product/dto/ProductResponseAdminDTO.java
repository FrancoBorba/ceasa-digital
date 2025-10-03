package br.com.uesb.ceasadigital.api.features.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;


public class ProductResponseAdminDTO{
 
   @Schema(description = "ID único do produto", example = "1")
  private Long id;

  @Schema(
  description = "Nome do Produto " , 
  example = "Batata " , 
  requiredMode = Schema.RequiredMode.REQUIRED)
  private String nome;


  @Schema(
    description = "Preço do produto na sua unidade de medida" ,
    example = "100.00",
   requiredMode = Schema.RequiredMode.REQUIRED )
  private BigDecimal preco;

    @Schema(
    description = "Unidade de medida a qual vai ser vendido o produto" ,
    example = "Kg",
   requiredMode = Schema.RequiredMode.REQUIRED )
  private String unidadeDeMedida;

  @Schema(
    description = "Descrição do produto",
    example = "Batata inglêsa vinda da chapada diamantina ótima para batata frita e purê",
    requiredMode = Schema.RequiredMode.REQUIRED
  )
  private String descricao;

  private LocalDateTime criadoEm;

  private LocalDateTime atualizadoEm;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public BigDecimal getPreco() {
    return preco;
  }

  public void setPreco(BigDecimal preco) {
    this.preco = preco;
  }

  public String getUnidadeDeMedida() {
    return unidadeDeMedida;
  }

  public void setUnidadeDeMedida(String unidadeDeMedida) {
    this.unidadeDeMedida = unidadeDeMedida;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public LocalDateTime getCriadoEm() {
    return criadoEm;
  }

  public void setCriadoEm(LocalDateTime criado_em) {
    this.criadoEm = criado_em;
  }

  public LocalDateTime getAtualizadoEm() {
    return atualizadoEm;
  }

  public void setAtualizadoEm(LocalDateTime atualizado_em) {
    this.atualizadoEm = atualizado_em;
  }
}
