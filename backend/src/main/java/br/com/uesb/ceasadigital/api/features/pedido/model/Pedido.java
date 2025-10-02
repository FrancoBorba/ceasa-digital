package br.com.uesb.ceasadigital.api.features.pedido.model;

import java.math.BigDecimal;
import java.time.Instant;

import br.com.uesb.ceasadigital.api.features.pedido.model.enums.PedidoStatus;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_pedidos")
public class Pedido {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User usuario;

  @ManyToOne
  @JoinColumn(name = "entregador_id")
  private User entregador;

  /*
  @ManyToOne
  @JoinColumn(name = "endereco_id")
  private Endereco endereco;

  @ManyToOne
  @JoinColumn(name = "cupom_id")
  private Cupom cupom;
  */

  private BigDecimal valorTotal;

  private PedidoStatus status;

  @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
  private Instant dataPedido;

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

  public User getEntregador() {
    return entregador;
  }

  public void setEntregador(User entregador) {
    this.entregador = entregador;
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
  
}
