package br.com.uesb.ceasadigital.api.features.item_lista.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import br.com.uesb.ceasadigital.api.features.lista.model.Lista;
import br.com.uesb.ceasadigital.api.features.product.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_lista_de_compras_itens")
public class ItemLista {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "lista_de_compras_id", nullable = false)
  private Lista lista;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "produto_id", nullable = false)
  private Product produto;

  @Column(name = "quantidade_desejada", nullable = false, precision = 10, scale = 3)
  private BigDecimal quantidade;

  @Column(name = "observacao", length = 255)
  private String observacao;

  @CreationTimestamp
  @Column(name = "criado_em", updatable = false)
  private LocalDateTime criadoEm;

  public ItemLista() {}

  public ItemLista(Lista lista, Product produto, BigDecimal quantidade) {
    this.lista = lista;
    this.produto = produto;
    this.quantidade = quantidade;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Lista getLista() {
    return lista;
  }

  public void setLista(Lista lista) {
    this.lista = lista;
  }

  public Product getProduto() {
    return produto;
  }

  public void setProduto(Product produto) {
    this.produto = produto;
  }

  public BigDecimal getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(BigDecimal quantidade) {
    this.quantidade = quantidade;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }

  public LocalDateTime getCriadoEm() {
    return criadoEm;
  }

  public void setCriadoEm(LocalDateTime criadoEm) {
    this.criadoEm = criadoEm;
  }
}