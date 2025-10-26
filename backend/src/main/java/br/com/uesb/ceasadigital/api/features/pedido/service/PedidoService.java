package br.com.uesb.ceasadigital.api.features.pedido.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.uesb.ceasadigital.api.common.exceptions.BadRequestException;
import br.com.uesb.ceasadigital.api.common.exceptions.DatabaseException;
import br.com.uesb.ceasadigital.api.common.exceptions.ForbiddenException;
import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.common.exceptions.UnauthorizedException;
import br.com.uesb.ceasadigital.api.features.carrinho.model.Carrinho;
import br.com.uesb.ceasadigital.api.features.carrinho.model.enums.CarrinhoStatus;
import br.com.uesb.ceasadigital.api.features.carrinho.repository.CarrinhoRepository;
import br.com.uesb.ceasadigital.api.features.carrinho.service.CarrinhoService;
import br.com.uesb.ceasadigital.api.features.endereco.model.Endereco;
import br.com.uesb.ceasadigital.api.features.endereco.repository.EnderecoRepository;
import br.com.uesb.ceasadigital.api.features.item_carrinho.model.ItemCarrinho;
import br.com.uesb.ceasadigital.api.features.item_pedido.model.ItemPedido;
import br.com.uesb.ceasadigital.api.features.pedido.dto.request.FinalizarCarrinhoRequestDTO;
import br.com.uesb.ceasadigital.api.features.pedido.dto.request.PedidoPageRequestDto;
import br.com.uesb.ceasadigital.api.features.pedido.dto.request.PedidoPostRequestDTO;
import br.com.uesb.ceasadigital.api.features.pedido.dto.request.PedidoPutRequestDTO;
import br.com.uesb.ceasadigital.api.features.pedido.dto.response.PedidoPageResponseDto;
import br.com.uesb.ceasadigital.api.features.pedido.dto.response.PedidoResponseDTO;
import br.com.uesb.ceasadigital.api.features.pedido.model.Pedido;
import br.com.uesb.ceasadigital.api.features.pedido.model.enums.PedidoStatus;
import br.com.uesb.ceasadigital.api.features.pedido.repository.PedidoRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;

@Service
public class PedidoService {

  private final Logger logger = LoggerFactory.getLogger(PedidoService.class);

  @Autowired
  private PedidoRepository pedidoRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private CarrinhoService carrinhoService;

  @Autowired
  private CarrinhoRepository carrinhoRepository;

  @Autowired
  private EnderecoRepository enderecoRepository;

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

  @Transactional(readOnly = true)
  public List<PedidoResponseDTO> getAllPedidosByCurrentUser() {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      throw new UnauthorizedException("Usuário não autenticado");
    }
    
    List<Pedido> pedidos = pedidoRepository.findByUsuarioId(currentUser.getId());
    return pedidos.stream()
        .map(PedidoResponseDTO::new)
        .toList();
  }

  @Transactional
  public PedidoResponseDTO finalizarCarrinho(FinalizarCarrinhoRequestDTO request) {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      throw new UnauthorizedException("Usuário não autenticado");
    }

    logger.info("Finalizando carrinho {} para usuário: {}", request.getCarrinhoId(), currentUser.getName());

    // Busca o carrinho específico pelo ID fornecido
    Carrinho carrinho = carrinhoRepository.findById(request.getCarrinhoId())
        .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado"));
    
    // Valida se o carrinho pertence ao usuário atual
    if (!carrinho.getUsuario().getId().equals(currentUser.getId())) {
      throw new ForbiddenException("O carrinho especificado não pertence ao usuário atual");
    }
    
    // Valida se o carrinho já foi finalizado
    if (carrinho.getStatus() == CarrinhoStatus.FINALIZADO) {
      throw new BadRequestException("Este carrinho já foi finalizado e não pode ser reutilizado. Use o carrinho ativo ou adicione novos itens para criar um novo carrinho.");
    }
    
    // Valida se o carrinho não está vazio
    if (carrinho.getItens() == null || carrinho.getItens().isEmpty()) {
      throw new BadRequestException("Carrinho vazio. Não é possível finalizar o pedido.");
    }

    Endereco endereco = enderecoRepository.findById(request.getEnderecoId())
        .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));

    if (!endereco.getUsuario().getId().equals(currentUser.getId())) {
      throw new ForbiddenException("Endereço não pertence ao usuário atual");
    }

    Pedido pedido = new Pedido();
    pedido.setUsuario(carrinho.getUsuario());
    pedido.setEndereco(endereco);
    pedido.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);
    pedido.setDataPedido(Instant.now());

    BigDecimal valorTotal = BigDecimal.ZERO;

    for (ItemCarrinho itemCarrinho : carrinho.getItens()) {
      ItemPedido itemPedido = new ItemPedido();
      itemPedido.setPedido(pedido);
      itemPedido.setOferta(itemCarrinho.getOfertaProdutor());
      itemPedido.setProduto(itemCarrinho.getProduto());
      itemPedido.setQuantidade(itemCarrinho.getQuantidade());
      itemPedido.setPrecoUnitario(itemCarrinho.getPrecoUnitarioArmazenado());

      pedido.getItens().add(itemPedido);

      BigDecimal subtotal = itemCarrinho.getPrecoUnitarioArmazenado()
          .multiply(itemCarrinho.getQuantidade());
      valorTotal = valorTotal.add(subtotal);
    }

    pedido.setValorTotal(valorTotal);

    Pedido pedidoSalvo = pedidoRepository.save(pedido);

    logger.info("Pedido criado com sucesso. ID: {}", pedidoSalvo.getId());

    carrinhoService.finalizarCarrinho(carrinho);

    logger.info("Carrinho marcado como FINALIZADO. Um novo carrinho será criado na próxima compra.");

    return new PedidoResponseDTO(pedidoSalvo);
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

  /**
   * Busca pedidos do usuário atual com paginação baseada em tempo
   * Permite ordenação ascendente (mais antigo para mais novo) ou descendente (mais novo para mais antigo)
   */
  @Transactional(readOnly = true)
  public PedidoPageResponseDto getAllPedidosByCurrentUserPaginated(PedidoPageRequestDto pageRequest) {
    User currentUser = userService.getCurrentUser();
    if (currentUser == null) {
      throw new UnauthorizedException("Usuário não autenticado");
    }

    logger.info("Buscando pedidos paginados para usuário: {} - Página: {}, Tamanho: {}, Ordenação: {} {}", 
                currentUser.getName(), pageRequest.getPage(), pageRequest.getSize(), 
                pageRequest.getSortBy(), pageRequest.getDirection());

    // Configurar ordenação baseada em tempo
    Sort.Direction sortDirection = "desc".equalsIgnoreCase(pageRequest.getDirection()) 
                                     ? Sort.Direction.DESC 
                                     : Sort.Direction.ASC;

    Sort sort = Sort.by(sortDirection, pageRequest.getSortBy());
    Pageable pageable = PageRequest.of(pageRequest.getPage(), pageRequest.getSize(), sort);

    // Buscar pedidos paginados do usuário
    Page<Pedido> pedidosPage = pedidoRepository.findByUsuarioId(currentUser.getId(), pageable);
    
    // Converter para DTOs
    Page<PedidoResponseDTO> pedidosResponsePage = pedidosPage.map(PedidoResponseDTO::new);
    
    return new PedidoPageResponseDto(pedidosResponsePage);
  }

  /**
   * Busca todos os pedidos com paginação baseada em tempo (para administradores)
   * Permite ordenação ascendente (mais antigo para mais novo) ou descendente (mais novo para mais antigo)
   */
  @Transactional(readOnly = true)
  public PedidoPageResponseDto getAllPedidosPaginated(PedidoPageRequestDto pageRequest) {
    logger.info("Buscando todos os pedidos paginados - Página: {}, Tamanho: {}, Ordenação: {} {}", 
                pageRequest.getPage(), pageRequest.getSize(), 
                pageRequest.getSortBy(), pageRequest.getDirection());

    // Configurar ordenação baseada em tempo
    Sort.Direction sortDirection = "desc".equalsIgnoreCase(pageRequest.getDirection()) 
                                     ? Sort.Direction.DESC 
                                     : Sort.Direction.ASC;

    Sort sort = Sort.by(sortDirection, pageRequest.getSortBy());
    Pageable pageable = PageRequest.of(pageRequest.getPage(), pageRequest.getSize(), sort);

    // Buscar todos os pedidos paginados
    Page<Pedido> pedidosPage = pedidoRepository.findAll(pageable);
    
    // Converter para DTOs
    Page<PedidoResponseDTO> pedidosResponsePage = pedidosPage.map(PedidoResponseDTO::new);
    
    return new PedidoPageResponseDto(pedidosResponsePage);
  }
  
}
