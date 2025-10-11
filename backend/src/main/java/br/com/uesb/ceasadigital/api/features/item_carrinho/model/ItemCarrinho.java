package br.com.uesb.ceasadigital.api.features.item_carrinho.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.uesb.ceasadigital.api.features.carrinho.model.Carrinho;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor;
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
@Table(name = "tb_carrinho_itens")
public class ItemCarrinho {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne // Muitos itens pertencem a um carrinho
  @JoinColumn(name = "carrinho_id" , nullable = false)
  private Carrinho carrinho;

  @ManyToOne // Muitos itens pertencem a uma oferta de produtor
  @JoinColumn(name = "oferta_produtor_id" , nullable = false)
  private OfertaProdutor ofertaProdutor;

  @Column(nullable = false , precision = 10 , scale = 3)
  private BigDecimal quantidade;

  @Column(name = "preco_unitario_armazenado", nullable = false, precision = 10, scale = 2)
  private BigDecimal precoUnitarioArmazenado;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "produto_id", nullable = false)
  private Product produto;

 

  @CreationTimestamp
  @Column(name = "criado_em", updatable = false)
  private LocalDateTime criadoEm;

  @UpdateTimestamp
  @Column(name = "atualizado_em")
  private LocalDateTime atualizadoEm;



  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Carrinho getCarrinho() {
    return carrinho;
  }

  public void setCarrinho(Carrinho carrinho) {
    this.carrinho = carrinho;
  }

  public OfertaProdutor getOfertaProdutor() {
    return ofertaProdutor;
  }

  public void setOfertaProdutor(OfertaProdutor ofertaProdutor) {
    this.ofertaProdutor = ofertaProdutor;
  }

  public BigDecimal getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(BigDecimal quantidade) {
    this.quantidade = quantidade;
  }

  public BigDecimal getPrecoUnitarioArmazenado() {
    return precoUnitarioArmazenado;
  }

  public void setPrecoUnitarioArmazenado(BigDecimal precoUnitarioArmazenado) {
    this.precoUnitarioArmazenado = precoUnitarioArmazenado;
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
  
   public Product getProduto() {
    return produto;
  }

  public void setProduto(Product produto) {
    this.produto = produto;
  }

  
}
