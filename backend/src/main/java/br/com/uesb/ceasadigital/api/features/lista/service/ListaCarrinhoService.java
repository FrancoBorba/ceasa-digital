package br.com.uesb.ceasadigital.api.features.lista.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.request.CarrinhoAddItemRequestDTO;
import br.com.uesb.ceasadigital.api.features.carrinho.dto.response.CarrinhoResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.service.ItemCarrinhoService;
import br.com.uesb.ceasadigital.api.features.item_lista.model.ItemLista;
import br.com.uesb.ceasadigital.api.features.item_lista.repository.ItemListaRepository;
import br.com.uesb.ceasadigital.api.features.lista.model.Lista;
import br.com.uesb.ceasadigital.api.features.lista.repository.ListaRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;
import jakarta.transaction.Transactional;

@Service
/**
 * Serviço responsável por enviar itens de uma lista de compras para o carrinho
 * do usuário. Respeita a arquitetura de Vertical Slice mantendo a lógica de
 * domínio no feature "lista" e reutilizando o serviço do feature
 * "item_carrinho" para adicionar itens ao carrinho.
 *
 * <p>
 * Regras principais:
 * </p>
 * - Valida a propriedade/posse da lista pelo usuário autenticado antes de qualquer operação.
 * - Envia itens (todos ou selecionados) preservando a quantidade cadastrada na lista.
 * - Em caso de item não encontrado na lista selecionada, lança {@link ResourceNotFoundException}.
 *
 * <p>
 * Retorno: sempre retorna o último estado do carrinho após os envios.
 * </p>
 */
public class ListaCarrinhoService {

  @Autowired
  private ItemCarrinhoService itemCarrinhoService;

  @Autowired
  private ListaRepository listaRepository;

  @Autowired
  private ItemListaRepository itemListaRepository;

  @Autowired
  private UserService userService;

  /**
   * Envia todos os itens da lista para o carrinho do usuário autenticado.
   *
   * @param listaId identificador da lista a ser processada
   * @return o estado do carrinho após os envios (último retorno)
   * @throws ResourceNotFoundException quando a lista não pertence ao usuário ou não existe
   */
  @Transactional
  public CarrinhoResponseDTO sendAllItemsToCarrinho(Long listaId) {
    Lista lista = ensureOwnership(listaId);
    List<ItemLista> itens = itemListaRepository.findAllByLista(lista);

    CarrinhoResponseDTO lastCarrinho = null;
    for (ItemLista item : itens) {
      CarrinhoAddItemRequestDTO req = new CarrinhoAddItemRequestDTO();
      req.setProdutoID(item.getProduto().getId());
      req.setQuantidade(item.getQuantidade());
      lastCarrinho = itemCarrinhoService.addItemInCarrinho(req);
    }
    return lastCarrinho;
  }

  /**
   * Envia itens selecionados da lista para o carrinho do usuário autenticado.
   *
   * @param listaId identificador da lista
   * @param itemIds lista de IDs de itens da lista a serem enviados
   * @return o estado do carrinho após os envios (último retorno)
   * @throws ResourceNotFoundException quando a lista não pertence ao usuário ou algum item não é encontrado
   */
  @Transactional
  public CarrinhoResponseDTO sendSelectedItemsToCarrinho(Long listaId, List<Long> itemIds) {
    Lista lista = ensureOwnership(listaId);
    CarrinhoResponseDTO lastCarrinho = null;
    for (Long itemId : itemIds) {
      ItemLista item = itemListaRepository.findByIdAndLista(itemId, lista)
          .orElseThrow(() -> new ResourceNotFoundException("Item da lista não encontrado"));
      CarrinhoAddItemRequestDTO req = new CarrinhoAddItemRequestDTO();
      req.setProdutoID(item.getProduto().getId());
      req.setQuantidade(item.getQuantidade());
      lastCarrinho = itemCarrinhoService.addItemInCarrinho(req);
    }
    return lastCarrinho;
  }

  /**
   * Garante que a lista pertence ao usuário autenticado.
   *
   * @param listaId identificador da lista
   * @return a entidade {@link Lista} quando pertence ao usuário
   * @throws ResourceNotFoundException quando a lista não pertence ao usuário ou não existe
   */
  private Lista ensureOwnership(Long listaId) {
    User usuario = userService.getCurrentUser();
    return listaRepository.findByIdAndUsuarioId(listaId, usuario.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));
  }
}