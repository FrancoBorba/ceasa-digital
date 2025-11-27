package br.com.uesb.ceasadigital.api.features.endereco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.uesb.ceasadigital.api.features.endereco.model.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
  
  List<Endereco> findByUsuarioId(Long usuarioId);
}

