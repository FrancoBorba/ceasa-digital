package br.com.uesb.ceasadigital.api.features.lista.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class ListaCreateRequestDTO {

  @NotBlank
  @Schema(description = "Nome da lista", example = "Compras da semana")
  private String nome;

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }
}