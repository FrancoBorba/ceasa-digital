package br.com.uesb.ceasadigital.api.features.pedido.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Optional;

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
import org.springframework.scheduling.annotation.Async;

import br.com.uesb.ceasadigital.api.common.exceptions.BadRequestException;
import br.com.uesb.ceasadigital.api.common.exceptions.DatabaseException;
import br.com.uesb.ceasadigital.api.common.exceptions.ForbiddenException;
import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.common.exceptions.UnauthorizedException;
import br.com.uesb.ceasadigital.api.common.notification.EmailService;
import br.com.uesb.ceasadigital.api.features.carrinho.model.Carrinho;
import br.com.uesb.ceasadigital.api.features.carrinho.model.enums.CarrinhoStatus;
import br.com.uesb.ceasadigital.api.features.carrinho.repository.CarrinhoRepository;
import br.com.uesb.ceasadigital.api.features.carrinho.service.CarrinhoService;
import br.com.uesb.ceasadigital.api.features.endereco.model.Endereco;
import br.com.uesb.ceasadigital.api.features.endereco.repository.EnderecoRepository;
import br.com.uesb.ceasadigital.api.features.item_carrinho.model.ItemCarrinho;
import br.com.uesb.ceasadigital.api.features.item_pedido.model.ItemPedido;
import br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.response.ResultadoCobrancaDTO;
import br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.webhook.SicoobPixNotificacaoDTO;
import br.com.uesb.ceasadigital.api.features.pagamento.interfaces.GatewayPagamento;
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
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.repository.OfertaProdutorRepository;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.service.FilaDePrioridadeService;

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
  
  @Autowired
  private EmailService emailService;
  
  @Autowired
  private OfertaProdutorRepository ofertaProdutorRepository;
  
  @Autowired
  private GatewayPagamento gatewayPagamentoService;

  @Autowired
  private FilaDePrioridadeService filaDePrioridadeService;
  
  @Transactional
  public void processarNotificacaoWebHook(SicoobPixNotificacaoDTO notificacao) {
    if (notificacao == null || notificacao.getTxid() == null) {
      logger.warn("Webhook PIX recebido, mas notificação ou txid estão nulos.");
      return;
    }
    
    Pedido pedido = pedidoRepository.findByTxidPagamento(notificacao.getTxid())
    .orElseThrow(() -> new ResourceNotFoundException(
    "Pedido não encontrado para o txid de pagamento: " + notificacao.getTxid()));
    
    if (pedido.getStatus() == PedidoStatus.PAGO) {
      logger.warn("Pedido ID: {} (txid: {}) já está PAGO. Ignorando notificação duplicada.", pedido.getId(),
      notificacao.getTxid());
      return;
    }
    
    // chama o metodo que confirma o pagamento
    this.confirmarPagamento(pedido.getId());
    
  }
  
  @Transactional
  public void confirmarPagamento(Long pedidoId) {
    logger.info("Confirmando pagamento para o Pedido ID: {}", pedidoId);
    
    Pedido pedido = pedidoRepository.findById(pedidoId)
    .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado para confirmação: " + pedidoId));
    
    /*
    * TODO: CHAMAR AQUI O MÉTODO DE CONTROLE DO ESTOQUE
    */
    pedido.setStatus(PedidoStatus.PAGO);
    pedidoRepository.save(pedido);
    
  }
  
  @Transactional
  public void cancelarPedido(Long pedidoId) {
    logger.info("Confirmando pagamento para o Pedido ID: {}", pedidoId);
    
    Pedido pedido = pedidoRepository.findById(pedidoId)
    .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado para confirmação: " + pedidoId));
    
    if (pedido.getStatus() == PedidoStatus.AGUARDANDO_PAGAMENTO) {
      pedido.setStatus(PedidoStatus.CANCELADO);
      pedidoRepository.save(pedido);
      /*
      * TODO: CHAMAR AQUI O MÉTODO DE CONTROLE DO ESTOQUE
      */
    } else {
      logger.warn("Tentativa de cancelar pedido que não está AGUARDANDO_PAGAMENTO. Status atual: {}",
      pedido.getStatus());
    }
    
    /*
    * TODO: CHAMAR AQUI O MÉTODO DE CONTROLE DO ESTOQUE
    */
    
  }
  
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
    
    // Check if the order exists, if not, return true for user is owner, so the
    // controller can throw 404
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
      throw new BadRequestException(
      "Este carrinho já foi finalizado e não pode ser reutilizado. Use o carrinho ativo ou adicione novos itens para criar um novo carrinho.");
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

      // Seleciona o vendedor atual do item
      OfertaProdutor ofertaProdutor = filaDePrioridadeService.selecionarOfertaDisponivel(
        itemCarrinho.getProduto().getId()
      , itemCarrinho.getQuantidade());

      ItemPedido itemPedido = new ItemPedido();
      itemPedido.setPedido(pedido);
      itemPedido.setProduto(itemCarrinho.getProduto());
      itemPedido.setQuantidade(itemCarrinho.getQuantidade());
      itemPedido.setPrecoUnitario(itemCarrinho.getPrecoUnitarioArmazenado());
      itemPedido.setOferta(ofertaProdutor);
      
      pedido.getItens().add(itemPedido);
      
      BigDecimal subtotal = itemCarrinho.getPrecoUnitarioArmazenado()
      .multiply(itemCarrinho.getQuantidade());
      valorTotal = valorTotal.add(subtotal);
    }
    
    pedido.setValorTotal(valorTotal);
    
    Pedido pedidoSalvo = pedidoRepository.save(pedido);
    
    ResultadoCobrancaDTO resultadoPagamento = gatewayPagamentoService.iniciarCobrancaPix(pedidoSalvo);
    
    if (resultadoPagamento.isSucesso()) { // A api do sicoob enviou os dados
      pedidoSalvo.setTxidPagamento(resultadoPagamento.getTxid());
      pedidoRepository.save(pedidoSalvo); // Atualiza o pedido com o TXID
    } else {
      throw new BadRequestException("Falha ao iniciar o processo de pagamento.");
    }
    
    PedidoResponseDTO responseDTO = new PedidoResponseDTO(pedidoSalvo);
    responseDTO.setDadosPix(resultadoPagamento);
    
    logger.info("Pedido criado com sucesso. ID: {}", pedidoSalvo.getId());
    
    carrinhoService.finalizarCarrinho(carrinho);
    
    logger.info("Carrinho marcado como FINALIZADO. Um novo carrinho será criado na próxima compra.");
    
    notificarProdutores(pedidoSalvo);
    
    return responseDTO;
  }
  
  /**
  * Método assíncrono para notificar os produtores sobre os itens vendidos em um
  * pedido.
  * Itera sobre os itens do pedido, agrupa-os por produtor e envia um e-mail para
  * cada um.
  */
  @Async
  public void notificarProdutores(Pedido pedido) {
    logger.info("Iniciando processo de notificação para o Pedido ID: {}", pedido.getId());
    
    // 1. Agrupar itens por Produtor (usando o User do Produtor como chave)
    Map<User, List<ItemPedido>> itensPorProdutor = new HashMap<>();
    
    for (ItemPedido item : pedido.getItens()) {
      // Verifica se o item tem uma oferta associada
      if (item.getOferta() != null && item.getOferta().getId() != null) {
        
        // Carrega a Oferta completa para obter o Produtor (e o User associado)
        // Usamos findById para carregar a entidade completa com suas relações
        Optional<OfertaProdutor> ofertaOpt = ofertaProdutorRepository.findById(item.getOferta().getId());
        
        if (ofertaOpt.isPresent() && ofertaOpt.get().getProdutor() != null
        && ofertaOpt.get().getProdutor().getUsuario() != null) {
          User produtorUser = ofertaOpt.get().getProdutor().getUsuario();
          
          // Adiciona o item à lista daquele produtor
          itensPorProdutor.computeIfAbsent(produtorUser, k -> new ArrayList<>()).add(item);
        } else {
          logger.warn(
          "ItemPedido ID {} (Pedido ID {}) tem uma Oferta ID {} mas não foi possível encontrar o Produtor associado.",
          item.getId(), pedido.getId(), item.getOferta().getId());
        }
      } else {
        logger.warn("ItemPedido ID {} (Pedido ID {}) está sem Oferta associada. Não é possível notificar produtor.",
        item.getId(), pedido.getId());
        // Se o item não tiver oferta, não podemos notificar o produtor.
        // Isso acontece se o ItemCarrinhoService adicionar itens só com produtoId.
      }
    }
    
    // 2. Enviar e-mail para cada produtor
    for (Map.Entry<User, List<ItemPedido>> entry : itensPorProdutor.entrySet()) {
      User produtor = entry.getKey();
      List<ItemPedido> itensDoProdutor = entry.getValue();
      
      String emailDestino = produtor.getEmail();
      String assunto = "🔔 Novo Pedido Recebido (Nº " + pedido.getId() + ") - Você tem itens para separar!";
      
      StringBuilder corpoEmail = new StringBuilder();
      corpoEmail.append("Olá, ").append(produtor.getName()).append("!\n\n");
      corpoEmail.append(
      "Um novo pedido foi finalizado e contém produtos das suas ofertas. Por favor, separe os seguintes itens para coleta/entrega:\n\n");
      corpoEmail.append("--------------------------------\n");
      corpoEmail.append("Pedido ID: ").append(pedido.getId()).append("\n");
      corpoEmail.append("Cliente: ").append(pedido.getUsuario().getName()).append("\n");
      corpoEmail.append("--------------------------------\n\n");
      corpoEmail.append("Itens a separar:\n\n");
      
      for (ItemPedido item : itensDoProdutor) {
        corpoEmail.append("  - Produto: ").append(item.getProduto().getNome()).append("\n");
        corpoEmail.append("    Quantidade: ").append(item.getQuantidade()).append(" ")
        .append(item.getProduto().getUnidadeDeMedida()).append("\n\n");
      }
      
      corpoEmail.append("Obrigado,\nEquipe CEASA Digital");
      
      try {
        emailService.sendSimpleMail(emailDestino, assunto, corpoEmail.toString());
        logger.info("Notificação enviada com sucesso para o produtor: {}", emailDestino);
      } catch (Exception e) {
        logger.error("Falha ao enviar e-mail de notificação para {}: {}", emailDestino, e.getMessage());
        // Mesmo se o e-mail falhar, não interrompa o fluxo principal do pedido.
      }
    }
    
    logger.info("Processo de notificação para o Pedido ID {} concluído.", pedido.getId());
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
  * Permite ordenação ascendente (mais antigo para mais novo) ou descendente
  * (mais novo para mais antigo)
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
  * Permite ordenação ascendente (mais antigo para mais novo) ou descendente
  * (mais novo para mais antigo)
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