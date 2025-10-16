package br.com.uesb.ceasadigital.api.features.item_pedido.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.item_pedido.dto.response.ItemPedidoResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_pedido.mapper.ItemPedidoMapper;
import br.com.uesb.ceasadigital.api.features.item_pedido.model.ItemPedido;
import br.com.uesb.ceasadigital.api.features.item_pedido.repository.ItemPedidoRepository;
import br.com.uesb.ceasadigital.api.features.pedido.model.Pedido;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
@Service
public class ItemPedidoService {

  @Autowired
  private ItemPedidoRepository itemPedidoRepository;

  @Autowired
  private ItemPedidoMapper mapper;

  @Autowired
  private UserService userService;

  @PersistenceContext
  private EntityManager entityManager;

  @Transactional(readOnly = true)
  public List<ItemPedidoResponseDTO> getAllItensByCurrentUser() {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      throw new RuntimeException("User not authenticated");
    }

    List<ItemPedido> itens = itemPedidoRepository.findAllByPedidoUsuarioId(currentUser.getId());
    if (itens.isEmpty()) {
      throw new ResourceNotFoundException("Itens não encontrados.");
    }

    return itens.stream()
        .map(mapper::toResponseDTO)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public ItemPedidoResponseDTO getItemById(Long id) {
    ItemPedido item = itemPedidoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Item do pedido não encontrado."));
    // checkOwnership(item);
    return mapper.toResponseDTO(item);
  }

  @Transactional(readOnly = true)
  public Object getAllItensByPedidoId(Long pedidoId) {
    List<ItemPedido> itens = itemPedidoRepository.findAllByPedidoId(pedidoId);
    if (itens.isEmpty()) {
      throw new ResourceNotFoundException("Itens não encontrados para esse pedido.");
    }
    return itens.stream()
        .map(mapper::toResponseDTO)
        .collect(Collectors.toList());
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
    return itemPedidoRepository.findById(itemId)
        .map(item -> item.getPedido().getUsuario().getId().equals(currentUser.getId()))
        .orElse(false);
  }
}
