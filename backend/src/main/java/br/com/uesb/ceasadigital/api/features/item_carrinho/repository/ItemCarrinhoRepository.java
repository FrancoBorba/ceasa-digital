package br.com.uesb.ceasadigital.api.features.item_carrinho.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uesb.ceasadigital.api.features.carrinho.model.Carrinho;
import br.com.uesb.ceasadigital.api.features.item_carrinho.model.ItemCarrinho;


public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho , Long> {
  
Optional<ItemCarrinho> findByCarrinhoAndOfertaProdutorId(Carrinho carrinho, Long ofertaProdutorId);
} 
