package br.com.uesb.ceasadigital.api.features.produtor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uesb.ceasadigital.api.features.produtor.model.Produtor;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {
    Optional<Produtor> findByUsuarioId(Long usuarioId);
}
