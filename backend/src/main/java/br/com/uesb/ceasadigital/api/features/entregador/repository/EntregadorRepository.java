package br.com.uesb.ceasadigital.api.features.entregador.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.uesb.ceasadigital.api.features.entregador.model.Entregador;

public interface EntregadorRepository extends JpaRepository<Entregador, Long> {
    Optional<Entregador> findByUsuarioId(Long usuarioId);
}