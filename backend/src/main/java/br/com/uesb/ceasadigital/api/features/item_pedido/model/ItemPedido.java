package br.com.uesb.ceasadigital.api.features.item_pedido.model;

import java.math.BigDecimal;
import java.time.Instant;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor;
import br.com.uesb.ceasadigital.api.features.pedido.model.Pedido;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;

/**
 * @Author: Caíque Santos Santana
 * @Date: 14/10/2023
 * @Description: Classe que representa um item de pedido no sistema.
 *               Cada item de pedido está associado a um pedido e a uma oferta
 *               específica.
 *               A estratégia Lazy foi escolhida para otimizar o desempenho,
 *               carregando os dados relacionados apenas quando necessário.
 */
@Entity
@Table(name = "tb_itens_pedido")
public class ItemPedido {

  /**
   * Se for preciso acessar item.getPedido() fora de uma transação, vai gerar
   * LazyInitializationException
   * Para resolver, deve-se manter a transação aberta ou usar FetchType.EAGER
   * (mas o fetch EAGER pode impactar o desempenho)
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pedido_id", nullable = false)
  private Pedido pedido;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "oferta_id", nullable = false)
  private OfertaProdutor oferta;

  @Digits(integer = 10, fraction = 0)
  @Column(name = "quantidade", columnDefinition = "DECIMAL(10, 0)")
  private BigDecimal quantidade;

  @Column(name = "preco_unitario", columnDefinition = "DECIMAL(10, 2)")
  private BigDecimal precoUnitario;

  @Column(name = "criado_em", columnDefinition = "TIMESTAMP", updatable = false)
  private Instant criadoEm;

  @Column(name = "atualizado_em", columnDefinition = "TIMESTAMP")
  private Instant atualizadoEm;

  // Getters and Setters
  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Pedido getPedido() {
    return this.pedido;
  }

  public void setPedido(Pedido pedido) {
    this.pedido = pedido;
  }

  public OfertaProdutor getOferta() {
    return this.oferta;
  }

  public void setOferta(OfertaProdutor oferta) {
    this.oferta = oferta;
  }

  public BigDecimal getQuantidade() {
    return this.quantidade;
  }

  public void setQuantidade(BigDecimal quantidade) {
    this.quantidade = quantidade;
  }

  @PrePersist
  protected void onCreate() {
    Instant now = Instant.now();
    this.criadoEm = now;
    this.atualizadoEm = now;
  }

  @PreUpdate
  protected void onUpdate() {
    this.atualizadoEm = Instant.now();
  }

  public BigDecimal getPrecoUnitario() {
    return this.precoUnitario;
  }

  public void setPrecoUnitario(BigDecimal precoUnitario) {
    this.precoUnitario = precoUnitario;
  }

  public Instant getCriadoEm() {
    return this.criadoEm;
  }

  public void setCriadoEm(Instant criadoEm) {
    this.criadoEm = criadoEm;
  }

  public Instant getAtualizadoEm() {
    return this.atualizadoEm;
  }

  public void setAtualizadoEm(Instant atualizadoEm) {
    this.atualizadoEm = atualizadoEm;
  }
}