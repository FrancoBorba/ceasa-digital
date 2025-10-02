package br.com.uesb.ceasadigital.api.features.pedido.model.enums;

public enum PedidoStatus {

  AGUARDANDO_PAGAMENTO("AGUARDANDO_PAGAMENTO"),
  PAGO("PAGO"),
  EM_TRANSPORTE("EM_TRANSPORTE"),
  ENTREGUE("ENTREGUE"),
  CANCELADO("CANCELADO");
  
  private final String value;

  PedidoStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
