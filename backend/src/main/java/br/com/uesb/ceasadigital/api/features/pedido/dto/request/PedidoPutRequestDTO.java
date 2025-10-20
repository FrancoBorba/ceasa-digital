package br.com.uesb.ceasadigital.api.features.pedido.dto.request;

import br.com.uesb.ceasadigital.api.features.pedido.model.enums.PedidoStatus;

public class PedidoPutRequestDTO {

  private Long id;

  private PedidoStatus status;

  public PedidoPutRequestDTO() {
  }

  public PedidoPutRequestDTO(Long id, PedidoStatus status) {
    this.id = id;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PedidoStatus getStatus() {
    return status;
  }

  public void setStatus(PedidoStatus status) {
    this.status = status;
  }
}
