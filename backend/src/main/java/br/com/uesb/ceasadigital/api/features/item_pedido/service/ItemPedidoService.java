package br.com.uesb.ceasadigital.api.features.item_pedido.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public Page<ItemPedidoResponseDTO> getItensByUsuario(Long usuarioId, Pageable pageable) {
    Page<ItemPedido> itens = itemPedidoRepository.findAllByPedidoUsuarioId(usuarioId, pageable);
    if (itens.isEmpty()) {
      throw new ResourceNotFoundException("Itens não encontrados para esse usuário.");
    }
    return itens.map(this::toResponseDTOWithProdutor);
  }

  // Buscar item por ID
  @Transactional(readOnly = true)
  public ItemPedidoResponseDTO getItemById(Long id) {
    ItemPedido item = itemPedidoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Item do pedido não encontrado."));
    return toResponseDTOWithProdutor(item);
  }

  // Buscar itens de um pedido específico (paginado)
  @Transactional(readOnly = true)
  public Page<ItemPedidoResponseDTO> getItensByPedidoId(Long pedidoId, Pageable pageable) {
    Page<ItemPedido> itens = itemPedidoRepository.findAllByPedidoId(pedidoId, pageable);
    if (itens.isEmpty()) {
      throw new ResourceNotFoundException("Itens não encontrados para esse pedido.");
    }
    return itens.map(this::toResponseDTOWithProdutor);
  }

  /**
   * Auxiliar: transforma ItemPedido em DTO e adiciona nome do produtor
   */
  private ItemPedidoResponseDTO toResponseDTOWithProdutor(ItemPedido item) {
    ItemPedidoResponseDTO dto = mapper.toResponseDTO(item);

    String nomeProdutor = null;
    if (item.getOferta() != null && item.getOferta().getId() != null) {
      Object result = entityManager.createNativeQuery("""
              SELECT u.name
              FROM tb_user u
              JOIN tb_perfis_produtor p ON p.usuario_id = u.id
              JOIN tb_ofertas_produtor o ON o.produtor_id = p.id
              WHERE o.id = :ofertaId
          """)
          .setParameter("ofertaId", item.getOferta().getId())
          .getSingleResult();

      if (result != null) {
        nomeProdutor = result.toString();
      }
    }

    dto.setNomeProdutor(nomeProdutor);
    return dto;
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

  // Verifica se usuário atual é dono de um item
  public boolean userIsOwner(Long itemId) {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null)
      return false;

    return itemPedidoRepository.findById(itemId)
        .map(item -> item.getPedido().getUsuario().getId().equals(currentUser.getId()))
        .orElse(false);
  }
}
