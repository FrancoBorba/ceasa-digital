package br.com.uesb.ceasadigital.api.features.pedido.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.uesb.ceasadigital.api.features.endereco.dto.EnderecoDTO;
import br.com.uesb.ceasadigital.api.features.pedido.model.Pedido;
import br.com.uesb.ceasadigital.api.features.pedido.model.enums.PedidoStatus;

public class PedidoResponseDTO {
  
  private Long id;

  @JsonProperty("moment")
  private Instant moment;

  @JsonProperty("status")
  private PedidoStatus status;

  @JsonProperty("client")
  private ClienteDTO client;

  @JsonProperty("endereco")
  private EnderecoDTO endereco;

  @JsonProperty("payment")
  private PaymentDTO payment;

  @JsonProperty("items")
  private List<ItemPedidoDetalhesDTO> items = new ArrayList<>();

  @JsonProperty("total")
  private BigDecimal total;

  public PedidoResponseDTO() {
  }

  public PedidoResponseDTO(Pedido pedido) {
    this.id = pedido.getId();
    this.moment = pedido.getDataPedido();
    this.status = pedido.getStatus();
    this.total = pedido.getValorTotal();
    
    if (pedido.getUsuario() != null) {
      this.client = new ClienteDTO(pedido.getUsuario().getId(), pedido.getUsuario().getName());
    }
    
    if (pedido.getEndereco() != null) {
      this.endereco = new EnderecoDTO(pedido.getEndereco());
    }

    this.payment = new PaymentDTO(null, null);
    
    if (pedido.getItens() != null) {
      this.items = pedido.getItens().stream()
          .map(ItemPedidoDetalhesDTO::new)
          .collect(Collectors.toList());
    }
  }

  public static class ClienteDTO {
    private Long id;
    private String name;

    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String name) {
      this.id = id;
      this.name = name;
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  public static class PaymentDTO {
    private Long id;
    private Instant moment;

    public PaymentDTO() {
    }

    public PaymentDTO(Long id, Instant moment) {
      this.id = id;
      this.moment = moment;
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public Instant getMoment() {
      return moment;
    }

    public void setMoment(Instant moment) {
      this.moment = moment;
    }
  }
  
  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public Instant getMoment() {
    return moment;
  }

  public void setMoment(Instant moment) {
    this.moment = moment;
  }

  public PedidoStatus getStatus() {
    return status;
  }

  public void setStatus(PedidoStatus status) {
    this.status = status;
  }

  public ClienteDTO getClient() {
    return client;
  }

  public void setClient(ClienteDTO client) {
    this.client = client;
  }

  public EnderecoDTO getEndereco() {
    return endereco;
  }

  public void setEndereco(EnderecoDTO endereco) {
    this.endereco = endereco;
  }

  public List<ItemPedidoDetalhesDTO> getItems() {
    return items;
  }

  public void setItems(List<ItemPedidoDetalhesDTO> items) {
    this.items = items;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public PaymentDTO getPayment() {
    return payment;
  }

  public void setPayment(PaymentDTO payment) {
    this.payment = payment;
  }
}
