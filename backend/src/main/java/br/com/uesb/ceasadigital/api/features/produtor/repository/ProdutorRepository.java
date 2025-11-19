package br.com.uesb.ceasadigital.api.features.produtor.repository;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uesb.ceasadigital.api.features.produtor.model.Produtor;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {
    Optional<Produtor> findByUsuarioId(Long usuarioId);
    Boolean existsByUsuarioId(Long usuarioId);
    Optional<Produtor> findById(Long id);
}
