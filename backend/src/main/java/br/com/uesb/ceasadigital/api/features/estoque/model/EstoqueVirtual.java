package br.com.uesb.ceasadigital.api.features.estoque.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.uesb.ceasadigital.api.features.estoque.model.enums.MetaEstoqueStatus;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor;
import br.com.uesb.ceasadigital.api.features.product.model.Product;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_metas_estoque")
public class EstoqueVirtual {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "produto_id", nullable = false)
  private Product produto;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "admin_criador_id", nullable = false)
  private User adminCriador;
  
  @Column(name = "quantidade_meta", nullable = false, precision = 10, scale = 3)
  private BigDecimal quantidadeMeta;
  
  @Enumerated(EnumType.STRING)
  @Column(length = 50, nullable = false)
  private MetaEstoqueStatus status;
  
  @CreationTimestamp
  @Column(name = "criado_em", updatable = false)
  private LocalDateTime criadoEm;
  
  @UpdateTimestamp
  @Column(name = "atualizado_em")
  private LocalDateTime atualizadoEm;

  @OneToMany(mappedBy = "metaEstoque", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OfertaProdutor> ofertas;
  
  // Getters e Setters
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Product getProduto() {
    return produto;
  }
  
  public void setProduto(Product produto) {
    this.produto = produto;
  }
  
  public User getAdminCriador() {
    return adminCriador;
  }
  
  public void setAdminCriador(User adminCriador) {
    this.adminCriador = adminCriador;
  }
  
  public BigDecimal getQuantidadeMeta() {
    return quantidadeMeta;
  }
  
  public void setQuantidadeMeta(BigDecimal quantidadeMeta) {
    this.quantidadeMeta = quantidadeMeta;
  }
  
  public MetaEstoqueStatus getStatus() {
    return status;
  }
  
  public void setStatus(MetaEstoqueStatus status) {
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