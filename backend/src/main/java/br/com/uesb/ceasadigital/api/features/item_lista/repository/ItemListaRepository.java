package br.com.uesb.ceasadigital.api.features.item_lista.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uesb.ceasadigital.api.features.item_lista.model.ItemLista;
import br.com.uesb.ceasadigital.api.features.lista.model.Lista;

public interface ItemListaRepository extends JpaRepository<ItemLista, Long> {
  List<ItemLista> findAllByLista(Lista lista);
  Optional<ItemLista> findByIdAndLista(Long id, Lista lista);
  Optional<ItemLista> findByListaAndProdutoId(Lista lista, Long produtoId);
}