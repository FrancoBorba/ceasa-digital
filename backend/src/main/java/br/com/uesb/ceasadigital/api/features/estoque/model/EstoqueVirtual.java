package br.com.uesb.ceasadigital.api.features.estoque.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "tb_metas_estoque")
public class EstoqueVirtual {
  

  @Id
  private Long id;
}
