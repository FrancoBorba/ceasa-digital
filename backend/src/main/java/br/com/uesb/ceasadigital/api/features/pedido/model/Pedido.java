package br.com.uesb.ceasadigital.api.features.pedido.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.uesb.ceasadigital.api.features.endereco.model.Endereco;
import br.com.uesb.ceasadigital.api.features.item_pedido.model.ItemPedido;
import br.com.uesb.ceasadigital.api.features.pedido.model.enums.PedidoStatus;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_pedidos")
public class Pedido {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private User usuario;

  @ManyToOne
  @JoinColumn(name = "endereco_id")
  private Endereco endereco;

  @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ItemPedido> itens = new ArrayList<>();

  @Column(name = "valor_total", columnDefinition = "DECIMAL(10, 2)")
  private BigDecimal valorTotal;

  @Enumerated(EnumType.STRING)
  private PedidoStatus status;

  @Column(name = "data_pedido", columnDefinition = "TIMESTAMP")
  private Instant dataPedido;

  @CreationTimestamp
  @Column(name = "criado_em", updatable = false, columnDefinition = "TIMESTAMP")
  private Instant criadoEm;

  @UpdateTimestamp
  @Column(name = "atualizado_em", columnDefinition = "TIMESTAMP")
  private Instant atualizadoEm;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUsuario() {
    return usuario;
  }

  public void setUsuario(User usuario) {
    this.usuario = usuario;
  }

  public BigDecimal getValorTotal() {
    return valorTotal;
  }

  public void setValorTotal(BigDecimal valorTotal) {
    this.valorTotal = valorTotal;
  }

  public PedidoStatus getStatus() {
    return status;
  }

  public void setStatus(PedidoStatus status) {
    this.status = status;
  }

  public Instant getDataPedido() {
    return dataPedido;
  }

  public void setDataPedido(Instant dataPedido) {
    this.dataPedido = dataPedido;
  }

  public Endereco getEndereco() {
    return endereco;
  }

  public void setEndereco(Endereco endereco) {
    this.endereco = endereco;
  }

  public List<ItemPedido> getItens() {
    return itens;
  }

  public void setItens(List<ItemPedido> itens) {
    this.itens = itens;
  }

  public Instant getCriadoEm() {
    return criadoEm;
  }

  public void setCriadoEm(Instant criadoEm) {
    this.criadoEm = criadoEm;
  }

  public Instant getAtualizadoEm() {
    return atualizadoEm;
  }

  public void setAtualizadoEm(Instant atualizadoEm) {
    this.atualizadoEm = atualizadoEm;
  }
  
}
