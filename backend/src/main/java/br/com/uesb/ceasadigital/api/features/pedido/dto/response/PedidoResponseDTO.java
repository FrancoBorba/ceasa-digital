package br.com.uesb.ceasadigital.api.features.pedido.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

import br.com.uesb.ceasadigital.api.features.pedido.model.Pedido;
import br.com.uesb.ceasadigital.api.features.pedido.model.enums.PedidoStatus;

public class PedidoResponseDTO {
  
  private Long id;
  private Long usuario_id;
  private BigDecimal valor_total;
  private PedidoStatus status;
  private Instant data_pedido;

  public PedidoResponseDTO(Pedido pedido) {
    this.id = pedido.getId();
    this.usuario_id = pedido.getUsuario().getId();
    this.valor_total = pedido.getValorTotal();
    this.status = pedido.getStatus();
    this.data_pedido = pedido.getDataPedido();
  }
  
  public void setId(Long id) {
    this.id = id;
  }

  public void setUsuario_id(Long usuario_id) {
    this.usuario_id = usuario_id;
  }

  public void setValor_total(BigDecimal valor_total) {
    this.valor_total = valor_total;
  }

  public void setStatus(PedidoStatus status) {
    this.status = status;
  }

  public void setData_pedido(Instant data_pedido) {
    this.data_pedido = data_pedido;
  }


  public Long getId() {
    return id;
  }

  public Long getUsuario_id() {
    return usuario_id;
  }

  public BigDecimal getValor_total() {
    return valor_total;
  }

  public PedidoStatus getStatus() {
    return status;
  }

  public Instant getData_pedido() {
    return data_pedido;
  }

}
