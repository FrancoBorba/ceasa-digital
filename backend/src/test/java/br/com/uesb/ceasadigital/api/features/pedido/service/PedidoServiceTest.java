package br.com.uesb.ceasadigital.api.features.pedido.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import br.com.uesb.ceasadigital.api.common.exceptions.*;
import br.com.uesb.ceasadigital.api.features.carrinho.model.Carrinho;
import br.com.uesb.ceasadigital.api.features.carrinho.model.enums.CarrinhoStatus;
import br.com.uesb.ceasadigital.api.features.carrinho.repository.CarrinhoRepository;
import br.com.uesb.ceasadigital.api.features.carrinho.service.CarrinhoService;
import br.com.uesb.ceasadigital.api.features.endereco.model.Endereco;
import br.com.uesb.ceasadigital.api.features.endereco.repository.EnderecoRepository;
import br.com.uesb.ceasadigital.api.features.item_carrinho.model.ItemCarrinho;
import br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.response.ResultadoCobrancaDTO;
import br.com.uesb.ceasadigital.api.features.pagamento.interfaces.GatewayPagamento;
import br.com.uesb.ceasadigital.api.features.pedido.dto.request.FinalizarCarrinhoRequestDTO;
import br.com.uesb.ceasadigital.api.features.pedido.dto.request.PedidoPostRequestDTO;
import br.com.uesb.ceasadigital.api.features.pedido.dto.request.PedidoPutRequestDTO;
import br.com.uesb.ceasadigital.api.features.pedido.dto.response.PedidoResponseDTO;
import br.com.uesb.ceasadigital.api.features.pedido.model.Pedido;
import br.com.uesb.ceasadigital.api.features.pedido.model.enums.PedidoStatus;
import br.com.uesb.ceasadigital.api.features.pedido.repository.PedidoRepository;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PedidoService Tests")
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private UserService userService;

    @Mock
    private CarrinhoService carrinhoService;

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private GatewayPagamento gatewayPagamentoService;

    @InjectMocks
    private PedidoService pedidoService;

    private User user;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Ana");

        pedido = new Pedido();
        pedido.setId(10L);
        pedido.setUsuario(user);
        pedido.setValorTotal(BigDecimal.TEN);
        pedido.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);
        pedido.setDataPedido(Instant.now());
    }

    @Test
    @DisplayName("Should return pedido by ID when found")
    void getPedidoById_WithValidId_ShouldReturnPedido() {
        when(pedidoRepository.findById(10L)).thenReturn(Optional.of(pedido));

        PedidoResponseDTO result = pedidoService.getPedidoById(10L);

        assertNotNull(result);
        assertEquals(pedido.getId(), result.getId());
        verify(pedidoRepository).findById(10L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when pedido not found")
    void getPedidoById_WithInvalidId_ShouldThrowException() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.getPedidoById(99L));
    }

    @Test
    @DisplayName("Should insert pedido successfully for authenticated user")
    void insertPedido_WithValidUser_ShouldInsertPedido() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        PedidoPostRequestDTO dto = new PedidoPostRequestDTO();
        dto.setValorTotal(BigDecimal.TEN);

        PedidoResponseDTO result = pedidoService.insertPedido(dto);

        assertNotNull(result);
        assertEquals(pedido.getValorTotal(), result.getTotal());
        verify(pedidoRepository).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Should throw RuntimeException when user not authenticated on insert")
    void insertPedido_WithoutUser_ShouldThrowException() {
        when(userService.getCurrentUser()).thenReturn(null);

        PedidoPostRequestDTO dto = new PedidoPostRequestDTO();
        dto.setValorTotal(BigDecimal.ONE);

        assertThrows(RuntimeException.class, () -> pedidoService.insertPedido(dto));
    }

    @Test
    @DisplayName("Should update pedido status successfully")
    void updatePedido_WithValidId_ShouldUpdateStatus() {
        PedidoPutRequestDTO dto = new PedidoPutRequestDTO();
        dto.setStatus(PedidoStatus.PAGO);

        when(pedidoRepository.getReferenceById(10L)).thenReturn(pedido);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        PedidoResponseDTO result = pedidoService.updatePedido(10L, dto);

        assertNotNull(result);
        assertEquals(PedidoStatus.PAGO, dto.getStatus());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating non-existent pedido")
    void updatePedido_WithInvalidId_ShouldThrowException() {
        PedidoPutRequestDTO dto = new PedidoPutRequestDTO();
        when(pedidoRepository.getReferenceById(anyLong())).thenThrow(new RuntimeException("Not found"));

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.updatePedido(99L, dto));
    }

    @Test
    @DisplayName("Should delete pedido successfully when exists")
    void deletePedido_WithValidId_ShouldDelete() {
        when(pedidoRepository.existsById(10L)).thenReturn(true);
        doNothing().when(pedidoRepository).deleteById(10L);

        pedidoService.deletePedido(10L);

        verify(pedidoRepository).deleteById(10L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existent pedido")
    void deletePedido_WithInvalidId_ShouldThrowException() {
        when(pedidoRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> pedidoService.deletePedido(99L));
    }

    @Test
    @DisplayName("Should finalize carrinho and create pedido successfully")
    void finalizarCarrinho_WithValidData_ShouldCreatePedido() {
        FinalizarCarrinhoRequestDTO request = new FinalizarCarrinhoRequestDTO();
        request.setCarrinhoId(1L);
        request.setEnderecoId(2L);

        Carrinho carrinho = new Carrinho();
        carrinho.setId(1L);
        carrinho.setUsuario(user);
        carrinho.setStatus(CarrinhoStatus.ATIVO);

        ItemCarrinho item = new ItemCarrinho();
        item.setQuantidade(BigDecimal.ONE);
        item.setPrecoUnitarioArmazenado(BigDecimal.TEN);
        carrinho.setItens(List.of(item));

        Endereco endereco = new Endereco();
        endereco.setId(2L);
        endereco.setUsuario(user);

        ResultadoCobrancaDTO resultadoCobranca = new ResultadoCobrancaDTO();
        resultadoCobranca.setSucesso(true);
        resultadoCobranca.setTxid("TX123");

        when(userService.getCurrentUser()).thenReturn(user);
        when(carrinhoRepository.findById(1L)).thenReturn(Optional.of(carrinho));
        when(enderecoRepository.findById(2L)).thenReturn(Optional.of(endereco));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido p = invocation.getArgument(0);
            p.setId(20L);
            return p;
        });

        when(gatewayPagamentoService.iniciarCobrancaPix(any(Pedido.class)))
                .thenReturn(resultadoCobranca);

        PedidoResponseDTO result = pedidoService.finalizarCarrinho(request);

        assertNotNull(result);
        assertEquals(20L, result.getId());
        assertEquals("TX123", result.getDadosPix().getTxid());
        verify(carrinhoService).finalizarCarrinho(carrinho);
    }

    @Test
    @DisplayName("Should throw UnauthorizedException when user not authenticated on finalize")
    void finalizarCarrinho_WithoutUser_ShouldThrowException() {
        when(userService.getCurrentUser()).thenReturn(null);

        FinalizarCarrinhoRequestDTO request = new FinalizarCarrinhoRequestDTO();
        request.setCarrinhoId(1L);

        assertThrows(UnauthorizedException.class, () -> pedidoService.finalizarCarrinho(request));
    }

    @Test
    @DisplayName("Should throw BadRequestException when carrinho is empty")
    void finalizarCarrinho_WithEmptyCarrinho_ShouldThrowException() {
        FinalizarCarrinhoRequestDTO request = new FinalizarCarrinhoRequestDTO();
        request.setCarrinhoId(1L);
        request.setEnderecoId(2L);

        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(user);
        carrinho.setItens(List.of()); // vazio
        carrinho.setStatus(CarrinhoStatus.ATIVO);

        Endereco endereco = new Endereco();
        endereco.setUsuario(user);

        when(userService.getCurrentUser()).thenReturn(user);
        when(carrinhoRepository.findById(1L)).thenReturn(Optional.of(carrinho));

        assertThrows(BadRequestException.class, () -> pedidoService.finalizarCarrinho(request));
    }
}
