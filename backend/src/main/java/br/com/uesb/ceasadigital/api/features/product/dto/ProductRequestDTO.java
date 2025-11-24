package br.com.uesb.ceasadigital.api.features.product.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductRequestDTO {

  @NotBlank(message = "Nome é obrigatório")
  @Schema(description = "Nome do Produto ", example = "Batata ", requiredMode = Schema.RequiredMode.REQUIRED)
  private String nome;

  @NotNull(message = "Preço não pode ser nulo")
  @Positive(message = "Preço deve ser positivo")
  @Schema(description = "Preço do produto na sua unidade de medida", example = "100.00", requiredMode = Schema.RequiredMode.REQUIRED)
  private BigDecimal preco;

  @NotBlank(message = "Unidade de Medida é obrigatório")
  @Schema(description = "Unidade de medida a qual vai ser vendido o produto", example = "Kg", requiredMode = Schema.RequiredMode.REQUIRED)
  private String unidadeDeMedida;

  @Size(max = 500, message = "Descrição pode ter até 500 caracteres")
  @Schema(description = "Descrição do produto", example = "Batata inglêsa vinda da chapada diamantina ótima para batata frita e purê", requiredMode = Schema.RequiredMode.REQUIRED)
  private String descricao;

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

}
