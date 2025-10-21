package br.com.uesb.ceasadigital.api.features.estoque.repository;

import br.com.uesb.ceasadigital.api.features.estoque.model.EstoqueVirtual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueVirtualRepository extends JpaRepository<EstoqueVirtual, Long> {
}