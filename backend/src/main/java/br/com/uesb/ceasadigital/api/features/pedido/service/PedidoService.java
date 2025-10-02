package br.com.uesb.ceasadigital.api.features.pedido.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.uesb.ceasadigital.api.common.exceptions.DatabaseException;
import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.pedido.dto.request.PedidoPostRequestDTO;
import br.com.uesb.ceasadigital.api.features.pedido.dto.request.PedidoPutRequestDTO;
import br.com.uesb.ceasadigital.api.features.pedido.dto.response.PedidoResponseDTO;
import br.com.uesb.ceasadigital.api.features.pedido.model.Pedido;
import br.com.uesb.ceasadigital.api.features.pedido.model.enums.PedidoStatus;
import br.com.uesb.ceasadigital.api.features.pedido.repository.PedidoRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;

@Service
public class PedidoService {

  @Autowired
  private PedidoRepository pedidoRepository;

  @Autowired
  private UserService userService;

  @Transactional(readOnly = true)
  public PedidoResponseDTO getPedidoById(Long id) {
    Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido not found"));
    return new PedidoResponseDTO(pedido);
  }

  @Transactional
  public PedidoResponseDTO insertPedido(PedidoPostRequestDTO pedidoRequestDTO) {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      throw new RuntimeException("User not authenticated");
    }

    Pedido pedido = new Pedido();
    pedido.setUsuario(currentUser);
    pedido.setValorTotal(pedidoRequestDTO.getValorTotal());
    pedido.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);
    pedido.setDataPedido(Instant.now());
    
    return new PedidoResponseDTO(pedidoRepository.save(pedido));
  }

  @Transactional
  public PedidoResponseDTO updatePedido(Long id, PedidoPutRequestDTO pedidoRequestDTO) {
    try {
      Pedido pedidoEntity = pedidoRepository.getReferenceById(id);
      
      pedidoRequestDTO.setId(id);

      // Only update fields that are not null
      if (pedidoRequestDTO.getValorTotal() != null) {
        pedidoEntity.setValorTotal(pedidoRequestDTO.getValorTotal());
      }
      
      if (pedidoRequestDTO.getStatus() != null) {
        pedidoEntity.setStatus(pedidoRequestDTO.getStatus());
      }

      return new PedidoResponseDTO(pedidoRepository.save(pedidoEntity));
    } catch (Exception e) {
      throw new ResourceNotFoundException("Resource not found");
    }
  }

  @Transactional(readOnly = true)
  public boolean userIsOwner(Long pedidoId) {
    User user = userService.getCurrentUser();
    if (user == null) {
      return false;
    }
    
    // Check if the order exists, if not, return true for user is owner, so the controller can throw 404
    if (!pedidoRepository.existsById(pedidoId)) {
      return true;
    }
    
    return pedidoRepository.findByIdAndUsuarioId(pedidoId, user.getId()).isPresent();
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void deletePedido(Long id) {
    if (!pedidoRepository.existsById(id)) {
      throw new ResourceNotFoundException("Resource not found");
    }
    try {
      pedidoRepository.deleteById(id);
    } catch (Exception e) {
      throw new DatabaseException("Constraint violation error");
    }
  }
  
}
