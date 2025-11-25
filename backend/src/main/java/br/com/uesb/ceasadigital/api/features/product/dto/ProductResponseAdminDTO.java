package br.com.uesb.ceasadigital.api.features.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.uesb.ceasadigital.api.features.categoria.dto.CategoryDTO;
import br.com.uesb.ceasadigital.api.features.product.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;

public class ProductResponseAdminDTO {

  @Schema(description = "ID único do produto", example = "1")
  private Long id;

  @Schema(description = "Nome do Produto ", example = "Batata ", requiredMode = Schema.RequiredMode.REQUIRED)
  private String nome;

  @Schema(description = "Preço do produto na sua unidade de medida", example = "100.00", requiredMode = Schema.RequiredMode.REQUIRED)
  private BigDecimal preco;

  @Schema(description = "Unidade de medida a qual vai ser vendido o produto", example = "Kg", requiredMode = Schema.RequiredMode.REQUIRED)
  private String unidadeDeMedida;

  @Schema(description = "Descrição do produto", example = "Batata inglêsa vinda da chapada diamantina ótima para batata frita e purê", requiredMode = Schema.RequiredMode.REQUIRED)
  private String descricao;

  private LocalDateTime criadoEm;

  private LocalDateTime atualizadoEm;

  private List<CategoryDTO> categories = new ArrayList<>();

  public ProductResponseAdminDTO() {
  }

  public ProductResponseAdminDTO(Long id, String nome, BigDecimal preco, String unidadeDeMedida, String descricao,
      String fotoUrl) {
    this.id = id;
    this.nome = nome;
    this.preco = preco;
    this.unidadeDeMedida = unidadeDeMedida;
    this.descricao = descricao;
  }

  public ProductResponseAdminDTO(Product product) {
    this.id = product.getId();
    this.nome = product.getNome();
    this.preco = product.getPreco();
    this.unidadeDeMedida = product.getUnidadeDeMedida().name();
    this.descricao = product.getDescricao();
  }

  public ProductResponseAdminDTO(Product product, List<CategoryDTO> categories) {
    this.id = product.getId();
    this.nome = product.getNome();
    this.preco = product.getPreco();
    this.unidadeDeMedida = product.getUnidadeDeMedida().name();
    this.descricao = product.getDescricao();
    categories.forEach(category -> this.categories.add(category));
  }

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

  public List<CategoryDTO> getCategories() {
    return categories;
  }

  public void setCategories(List<CategoryDTO> categories) {
    this.categories = categories;
  }
}
