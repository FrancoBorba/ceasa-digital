package br.com.uesb.ceasadigital.api.features.item_lista.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uesb.ceasadigital.api.common.exceptions.BadRequestException;
import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.item_lista.dto.request.ItemListaAddRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_lista.dto.request.ItemListaUpdateRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_lista.dto.response.ItemListaResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_lista.mapper.ItemListaMapper;
import br.com.uesb.ceasadigital.api.features.item_lista.model.ItemLista;
import br.com.uesb.ceasadigital.api.features.item_lista.repository.ItemListaRepository;
import br.com.uesb.ceasadigital.api.features.lista.model.Lista;
import br.com.uesb.ceasadigital.api.features.lista.repository.ListaRepository;
import br.com.uesb.ceasadigital.api.features.product.model.Product;
import br.com.uesb.ceasadigital.api.features.product.repository.ProductRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;
import jakarta.transaction.Transactional;

@Service
public class ItemListaService {

  @Autowired
  private ItemListaRepository itemListaRepository;

  @Autowired
  private ListaRepository listaRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ItemListaMapper itemListaMapper;

  @Autowired
  private UserService userService;

  @Transactional
  public ItemListaResponseDTO addItem(Long listaId, ItemListaAddRequestDTO request) {
    if (request.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
      throw new BadRequestException("Quantidade deve ser maior que zero");
    }

    Lista lista = findListaOwnedByCurrentUser(listaId);

    Product produto = productRepository.findById(request.getProdutoId())
        .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

    ItemLista item = itemListaRepository.findByListaAndProdutoId(lista, produto.getId())
        .map(existing -> {
          existing.setQuantidade(existing.getQuantidade().add(request.getQuantidade()));
          return existing;
        })
        .orElseGet(() -> new ItemLista(lista, produto, request.getQuantidade()));

    itemListaRepository.save(item);
    return itemListaMapper.toResponseDTO(item);
  }

  @Transactional
  public ItemListaResponseDTO updateItem(Long listaId, Long itemId, ItemListaUpdateRequestDTO request) {
    if (request.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
      throw new BadRequestException("Quantidade deve ser maior que zero");
    }

    Lista lista = findListaOwnedByCurrentUser(listaId);
    ItemLista item = itemListaRepository.findByIdAndLista(itemId, lista)
        .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado na lista"));

    item.setQuantidade(request.getQuantidade());
    itemListaRepository.save(item);
    return itemListaMapper.toResponseDTO(item);
  }

  @Transactional
  public void deleteItem(Long listaId, Long itemId) {
    Lista lista = findListaOwnedByCurrentUser(listaId);
    ItemLista item = itemListaRepository.findByIdAndLista(itemId, lista)
        .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado na lista"));
    itemListaRepository.delete(item);
  }

  private Lista findListaOwnedByCurrentUser(Long listaId) {
    User usuario = userService.getCurrentUser();
    return listaRepository.findByIdAndUsuarioId(listaId, usuario.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));
  }
}