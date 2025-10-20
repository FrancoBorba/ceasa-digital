package br.com.uesb.ceasadigital.api.features.oferta_produtor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor;

@Repository
public interface OfertaProdutorRepository extends JpaRepository<OfertaProdutor, Long> {
}

