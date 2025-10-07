package br.com.uesb.ceasadigital.api.features.carrinho.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uesb.ceasadigital.api.features.carrinho.model.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    
    Optional<Carrinho> findByUsuarioId(Long usuarioId);
    
    List<Carrinho> findAllByUsuarioId(Long usuarioId);
}