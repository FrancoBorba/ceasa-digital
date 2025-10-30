package br.com.uesb.ceasadigital.api.features.oferta_produtor.model;

import br.com.uesb.ceasadigital.api.features.estoque.model.EstoqueVirtual;
import br.com.uesb.ceasadigital.api.features.produtor.model.Produtor;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meta_estoque_id")
    private EstoqueVirtual metaEstoque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produtor_id") // Nome da coluna no V04
    private Produtor produtor;

    public Produtor getProdutor() {
        return this.produtor;
    }

    public void setProdutor(Produtor produtor) {
        this.produtor = produtor;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstoqueVirtual getMetaEstoque() {
        return metaEstoque;
    }

    public void setMetaEstoque(EstoqueVirtual metaEstoque) {
        this.metaEstoque = metaEstoque;
    }
}


