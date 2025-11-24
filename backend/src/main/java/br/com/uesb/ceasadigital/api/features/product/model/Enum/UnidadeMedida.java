package br.com.uesb.ceasadigital.api.features.product.model.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UnidadeMedida {
  KG("Quilograma(s)"),
  UN("Unidade(s)"),
  CX("Caixa(s)");

  private final String label;

  UnidadeMedida(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static UnidadeMedida fromValue(String value) {
    return UnidadeMedida.valueOf(value.toUpperCase());
  }
}
