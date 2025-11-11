package br.com.uesb.ceasadigital.api.features.lista.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ListaSendToCarrinhoRequestDTO {

  @NotNull
  @Schema(description = "IDs dos itens da lista para enviar ao carrinho", example = "[1,2,3]")
  private List<Long> itemIds;

  public List<Long> getItemIds() {
    return itemIds;
  }

  public void setItemIds(List<Long> itemIds) {
    this.itemIds = itemIds;
  }
}