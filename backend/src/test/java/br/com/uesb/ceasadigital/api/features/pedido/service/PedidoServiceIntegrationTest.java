package br.com.uesb.ceasadigital.api.features.pedido.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.uesb.ceasadigital.api.features.carrinho.model.Carrinho;
import br.com.uesb.ceasadigital.api.features.carrinho.model.enums.CarrinhoStatus;
import br.com.uesb.ceasadigital.api.features.carrinho.repository.CarrinhoRepository;
import br.com.uesb.ceasadigital.api.features.carrinho.service.CarrinhoService;
import br.com.uesb.ceasadigital.api.features.endereco.model.Endereco;
import br.com.uesb.ceasadigital.api.features.endereco.repository.EnderecoRepository;
import br.com.uesb.ceasadigital.api.features.item_carrinho.model.ItemCarrinho;
import br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.response.ResultadoCobrancaDTO;
import br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.webhook.SicoobPixNotificacaoDTO;
import br.com.uesb.ceasadigital.api.features.pagamento.interfaces.GatewayPagamento;
import br.com.uesb.ceasadigital.api.features.pedido.dto.request.FinalizarCarrinhoRequestDTO;
import br.com.uesb.ceasadigital.api.features.pedido.dto.response.PedidoResponseDTO;
import br.com.uesb.ceasadigital.api.features.pedido.model.Pedido;
import br.com.uesb.ceasadigital.api.features.pedido.model.enums.PedidoStatus;
import br.com.uesb.ceasadigital.api.features.pedido.repository.PedidoRepository;
import br.com.uesb.ceasadigital.api.features.product.model.Product;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.repository.UserRepository;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PedidoServiceIntegrationTest {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @MockBean
    private GatewayPagamento gatewayPagamentoService;

    @MockBean
    private UserService userService;

    @MockBean
    private CarrinhoService carrinhoService;

    @Autowired
    private UserRepository userRepository;


    private User usuario;
    private Carrinho carrinho;
    private Endereco endereco;

    @BeforeEach
    void setup() {
        // Usuário autenticado
        usuario = new User();
        usuario.setName("Ana Carolina");
        usuario.setEmail("ana@teste.com");
        usuario.setEmailConfirmado(true);
        usuario.setCpf("123.456.789-00");
        usuario.setPassword("123456"); 
        usuario = userRepository.save(usuario);

        when(userService.getCurrentUser()).thenReturn(usuario);

        endereco = new Endereco();
        endereco.setCep("45000-000");
        endereco.setLogradouro("Rua das Flores");
        endereco.setBairro("Centro");
        endereco.setCidade("Vitória da Conquista");
        endereco.setEstado("BA");
        endereco.setNumero("100");
        endereco.setPrincipal(true);
        endereco.setUsuario(usuario);
        endereco = enderecoRepository.save(endereco);

        // Carrinho com um item
        carrinho = new Carrinho();
        carrinho.setUsuario(usuario);
        carrinho.setStatus(CarrinhoStatus.ATIVO);
        carrinho.setCriadoEm(LocalDateTime.now());

        ItemCarrinho item = new ItemCarrinho();
        item.setCarrinho(carrinho);
        Product product = new Product();
        product.setId(1L);
        product.setNome("Maçã");
        product.setPreco(new BigDecimal("3.50"));
        item.setProduto(product);
        item.setPrecoUnitarioArmazenado(new BigDecimal("3.50"));
        item.setQuantidade(new BigDecimal("2"));
        carrinho.setItens(List.of(item));

        carrinhoRepository.save(carrinho);

        // Mock do pagamento PIX
        ResultadoCobrancaDTO cobrancaSucesso = new ResultadoCobrancaDTO();
        cobrancaSucesso.setSucesso(true);
        cobrancaSucesso.setTxid("TX12345");

        when(gatewayPagamentoService.iniciarCobrancaPix(any(Pedido.class)))
                .thenReturn(cobrancaSucesso);
    }

    @Test
    void deveExecutarFluxoCompletoDeCompra_ComSucesso() {
        FinalizarCarrinhoRequestDTO request = new FinalizarCarrinhoRequestDTO();
        request.setCarrinhoId(carrinho.getId());
        request.setEnderecoId(endereco.getId());

        PedidoResponseDTO pedidoCriado = pedidoService.finalizarCarrinho(request);

        assertNotNull(pedidoCriado);
        assertEquals(PedidoStatus.AGUARDANDO_PAGAMENTO, pedidoCriado.getStatus());
        assertNotNull(pedidoCriado.getDadosPix());
        assertEquals("TX12345", pedidoCriado.getDadosPix().getTxid());

        Pedido pedidoSalvo = pedidoRepository.findById(pedidoCriado.getId()).orElseThrow();
        assertEquals(usuario.getId(), pedidoSalvo.getUsuario().getId());
        assertEquals(PedidoStatus.AGUARDANDO_PAGAMENTO, pedidoSalvo.getStatus());

        SicoobPixNotificacaoDTO notificacao = new SicoobPixNotificacaoDTO();
        notificacao.setTxid("TX12345");

        pedidoService.processarNotificacaoWebHook(notificacao);

        Pedido pedidoPago = pedidoRepository.findById(pedidoSalvo.getId()).orElseThrow();
        assertEquals(PedidoStatus.PAGO, pedidoPago.getStatus());
    }
}