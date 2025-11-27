package br.com.uesb.ceasadigital.api.features.lista.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uesb.ceasadigital.api.features.lista.model.Lista;

public interface ListaRepository extends JpaRepository<Lista, Long> {
  List<Lista> findAllByUsuarioId(Long usuarioId);
  Optional<Lista> findByIdAndUsuarioId(Long id, Long usuarioId);
}