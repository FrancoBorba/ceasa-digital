package br.com.uesb.ceasadigital.api.features.oferta_produtor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor;
import java.util.List;

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
}