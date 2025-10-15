package br.com.uesb.ceasadigital.api.features.oferta_produtor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_ofertas_produtor")
public class OfertaProdutor {
  
  /*
  Mensagem de Franco: 
  Precisei criar esse Stub já que para implementar o item do carrinho ele tem como
  chave estrangeira a oferta do produto 
  boa implementação
    */
    @Id
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
