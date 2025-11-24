package br.com.uesb.ceasadigital.api.features.product.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.uesb.ceasadigital.api.features.categoria.model.Category;
import br.com.uesb.ceasadigital.api.features.product.model.Enum.UnidadeMedida;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_produtos")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 255)
  private String nome;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal preco;

  @Enumerated(EnumType.STRING)
  @Column(name = "unidade_de_medida", nullable = false, length = 20)
  private UnidadeMedida unidadeDeMedida;

  @Column(columnDefinition = "TEXT")
  private String descricao;

  @Column(name = "foto_url")
  private String fotoUrl;

  @ManyToMany
  @JoinTable(name = "tb_produto_categoria", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
  private Set<Category> categories;

  @CreationTimestamp // Define a data e a hora de criação
  @Column(name = "criado_em", updatable = false)
  private LocalDateTime criadoEm;

  @UpdateTimestamp
  @Column(name = "atualizado_em")
  private LocalDateTime atualizadoEm;

  // TODO: Relação Um-para-Muitos: Um Produto pode ter muitas Avaliações

  // Relação Um-para-Muitos: Um Produto pode estar em muitos Itens de Pedido

  // TODO: Mapear os outros relacionamentos (OfertaProdutor, EstoqueVirtual)
  // quando as entidades existirem.

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

  public UnidadeMedida getUnidadeDeMedida() {
    return unidadeDeMedida;
  }

  public void setUnidadeDeMedida(UnidadeMedida unidadeDeMedida) {
    this.unidadeDeMedida = unidadeDeMedida;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getFotoUrl() {
    return fotoUrl;
  }

  public void setFotoUrl(String fotoUrl) {
    this.fotoUrl = fotoUrl;
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

  public Set<Category> getCategories() {
    return categories;
  }

}
