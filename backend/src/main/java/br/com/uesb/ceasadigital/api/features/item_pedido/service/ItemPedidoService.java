package br.com.uesb.ceasadigital.api.features.item_pedido.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.item_pedido.dto.request.ItemPedidoPostRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_pedido.dto.request.ItemPedidoPutRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_pedido.dto.response.ItemPedidoResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_pedido.mapper.ItemPedidoMapper;
import br.com.uesb.ceasadigital.api.features.item_pedido.model.ItemPedido;
import br.com.uesb.ceasadigital.api.features.item_pedido.repository.ItemPedidoRepository;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor;
import br.com.uesb.ceasadigital.api.features.pedido.model.Pedido;
import br.com.uesb.ceasadigital.api.features.pedido.repository.PedidoRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
@Service
public class ItemPedidoService {

  @Autowired
  private ItemPedidoRepository repository;

  @Autowired
  private PedidoRepository pedidoRepository;

  @Autowired
  private ItemPedidoMapper mapper;

  @Autowired
  private UserService userService;

  @PersistenceContext
  private EntityManager entityManager;

  // Fake OfertaProdutor para desenvolvimento
  private static final Long FAKE_OFERTA_ID = 101L;

  @Transactional(readOnly = true)
  public List<ItemPedidoResponseDTO> getAllItensByCurrentUser() {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      throw new RuntimeException("User not authenticated");
    }

    try {
      List<ItemPedido> itens = repository.findByPedidoUsuarioId(currentUser.getId());
      return itens.stream()
          .map(mapper::toResponseDTO)
          .collect(Collectors.toList());
    } catch (ResourceNotFoundException e) {
      throw new ResourceNotFoundException("Itens não encontrados.");
    }
  }

  @Transactional(readOnly = true)
  public ItemPedidoResponseDTO getItemById(Long id) {
    ItemPedido item = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Item do pedido não encontrado"));
    checkOwnership(item);
    return mapper.toResponseDTO(item);
  }

  public ItemPedidoResponseDTO insertItem(ItemPedidoPostRequestDTO dto) {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      throw new RuntimeException("User not authenticated");
    }

    Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
        .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

    checkOwnership(pedido);

    ItemPedido item = new ItemPedido();
    item.setPedido(pedido);
    item.setQuantidade(dto.getQuantidade());
    item.setPrecoUnitario(dto.getPrecoUnitario());

    // Referência fake de Oferta
    OfertaProdutor ofertaRef = entityManager.getReference(OfertaProdutor.class, FAKE_OFERTA_ID);
    item.setOferta(ofertaRef);

    ItemPedido savedItem = repository.save(item);
    return mapper.toResponseDTO(savedItem);
  }

  public ItemPedidoResponseDTO updateItem(Long id, ItemPedidoPutRequestDTO dto) {
    ItemPedido item = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("ItemPedido não encontrado"));
    checkOwnership(item);

    item.setQuantidade(dto.getQuantidade());
    item.setPrecoUnitario(dto.getPrecoUnitario());

    ItemPedido savedItem = repository.save(item);
    return mapper.toResponseDTO(savedItem);
  }

  public void deleteItem(Long id) {
    ItemPedido item = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("ItemPedido não encontrado"));
    checkOwnership(item);
    repository.delete(item);
  }

  /**
   * Auxiliares de segurança
   */
  private void checkOwnership(ItemPedido item) {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null || !item.getPedido().getUsuario().getId().equals(currentUser.getId())) {
      throw new RuntimeException("Usuário não é dono deste item");
    }
  }

  private void checkOwnership(Pedido pedido) {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null || !pedido.getUsuario().getId().equals(currentUser.getId())) {
      throw new RuntimeException("Usuário não é dono deste pedido");
    }
  }

  public boolean userIsOwner(Long itemId) {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      return false;
    }
    return repository.findById(itemId)
        .map(item -> item.getPedido().getUsuario().getId().equals(currentUser.getId()))
        .orElse(false);
  }
}
