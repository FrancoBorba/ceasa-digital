package br.com.uesb.ceasadigital.api.features.Carrinho.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_carrinhos")
public class Carrinho {

  /*Lucca precisei criar só esse Stub aqui por que é chave estrangeira do carrinho
    então precisava ter apenas esse esboço para referenciar
   */
   @Id
   private Long id;
}
