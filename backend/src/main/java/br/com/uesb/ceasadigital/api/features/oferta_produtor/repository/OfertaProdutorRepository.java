package br.com.uesb.ceasadigital.api.features.oferta_produtor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.enums.OfertaStatus;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Sort; 

@Repository
public interface OfertaProdutorRepository extends JpaRepository<OfertaProdutor, Long> {
  
  /**
  * Encontra todas as ofertas de um produtor específico.
  */
  List<OfertaProdutor> findByProdutorId(Long produtorId);
  
  /**
  * Encontra todas as ofertas de um produtor com um status específico.
  */
  List<OfertaProdutor> findByProdutorIdAndStatus(Long produtorId, br.com.uesb.ceasadigital.api.features.oferta_produtor.model.enums.OfertaStatus status);

  /*
     * Busca ofertas ativas para um produto, que tenham estoque suficiente,
     * e já retorna ordenado pela regra de prioridade (ex: quem vendeu menos, vende primeiro).
    */
  List<OfertaProdutor> findByMetaEstoqueProdutoIdAndQuantidadeDisponivelGreaterThanEqualAndStatus(
        Long produtoId,
        BigDecimal quantidadeDesejada,
        OfertaStatus status,
        Sort   sort // Usaremos o 'sort' para definir a prioridade
    );
}