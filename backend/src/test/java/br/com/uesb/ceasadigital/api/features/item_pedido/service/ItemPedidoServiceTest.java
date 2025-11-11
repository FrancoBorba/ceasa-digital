package br.com.uesb.ceasadigital.api.features.item_pedido.service;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.item_pedido.dto.response.ItemPedidoResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_pedido.mapper.ItemPedidoMapper;
import br.com.uesb.ceasadigital.api.features.item_pedido.model.ItemPedido;
import br.com.uesb.ceasadigital.api.features.item_pedido.repository.ItemPedidoRepository;
import br.com.uesb.ceasadigital.api.features.pagamento.interfaces.GatewayPagamento;
import br.com.uesb.ceasadigital.api.features.pedido.model.Pedido;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ItemPedidoService Tests")
class ItemPedidoServiceTest {

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Mock
    private ItemPedidoMapper mapper;

    @Mock
    private UserService userService;

    @Mock
    private EntityManager entityManager;
    
    @InjectMocks
    private ItemPedidoService itemPedidoService;

    private User user;
    private Pedido pedido;
    private ItemPedido item;
    private ItemPedidoResponseDTO dto;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Ana");

        pedido = new Pedido();
        pedido.setId(10L);
        pedido.setUsuario(user);

        item = new ItemPedido();
        item.setId(100L);
        item.setPedido(pedido);

        dto = new ItemPedidoResponseDTO();
        dto.setId(100L);

        pageable = PageRequest.of(0, 10);
    }

    @Test
    @DisplayName("Should return paginated itens by user when found")
    void getItensByUsuario_WithItens_ShouldReturnPage() {
        Page<ItemPedido> page = new PageImpl<>(List.of(item));

        when(itemPedidoRepository.findAllByPedidoUsuarioId(1L, pageable)).thenReturn(page);
        when(mapper.toResponseDTO(item)).thenReturn(dto);

        Page<ItemPedidoResponseDTO> result = itemPedidoService.getItensByUsuario(1L, pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(100L, result.getContent().get(0).getId());
        verify(itemPedidoRepository).findAllByPedidoUsuarioId(1L, pageable);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when no itens found by user")
    void getItensByUsuario_WithoutItens_ShouldThrowException() {
        Page<ItemPedido> emptyPage = Page.empty(pageable);
        when(itemPedidoRepository.findAllByPedidoUsuarioId(1L, pageable)).thenReturn(emptyPage);

        assertThrows(ResourceNotFoundException.class,
                () -> itemPedidoService.getItensByUsuario(1L, pageable));
    }

    @Test
    @DisplayName("Should return item by ID when found")
    void getItemById_WithValidId_ShouldReturnDTO() {
        when(itemPedidoRepository.findById(100L)).thenReturn(Optional.of(item));
        when(mapper.toResponseDTO(item)).thenReturn(dto);

        ItemPedidoResponseDTO result = itemPedidoService.getItemById(100L);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        verify(itemPedidoRepository).findById(100L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when item not found by ID")
    void getItemById_WithInvalidId_ShouldThrowException() {
        when(itemPedidoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> itemPedidoService.getItemById(999L));
    }

    @Test
    @DisplayName("Should return paginated itens by pedido when found")
    void getItensByPedidoId_WithItens_ShouldReturnPage() {
        Page<ItemPedido> page = new PageImpl<>(List.of(item));
        when(itemPedidoRepository.findAllByPedidoId(10L, pageable)).thenReturn(page);
        when(mapper.toResponseDTO(item)).thenReturn(dto);

        Page<ItemPedidoResponseDTO> result = itemPedidoService.getItensByPedidoId(10L, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(100L, result.getContent().get(0).getId());
        verify(itemPedidoRepository).findAllByPedidoId(10L, pageable);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when no itens found for pedido")
    void getItensByPedidoId_WithoutItens_ShouldThrowException() {
        Page<ItemPedido> emptyPage = Page.empty(pageable);
        when(itemPedidoRepository.findAllByPedidoId(10L, pageable)).thenReturn(emptyPage);

        assertThrows(ResourceNotFoundException.class,
                () -> itemPedidoService.getItensByPedidoId(10L, pageable));
    }

    @Test
    @DisplayName("Should return true when current user owns the item")
    void userIsOwner_WithOwnership_ShouldReturnTrue() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(itemPedidoRepository.findById(100L)).thenReturn(Optional.of(item));

        boolean result = itemPedidoService.userIsOwner(100L);

        assertTrue(result);
        verify(itemPedidoRepository).findById(100L);
    }

    @Test
    @DisplayName("Should return false when user not authenticated")
    void userIsOwner_WithoutUser_ShouldReturnFalse() {
        when(userService.getCurrentUser()).thenReturn(null);

        boolean result = itemPedidoService.userIsOwner(100L);

        assertFalse(result);
        verify(userService).getCurrentUser();
        verify(itemPedidoRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("Should return false when user does not own the item")
    void userIsOwner_WithDifferentUser_ShouldReturnFalse() {
        User another = new User();
        another.setId(2L);
        pedido.setUsuario(another);

        when(userService.getCurrentUser()).thenReturn(user);
        when(itemPedidoRepository.findById(100L)).thenReturn(Optional.of(item));

        boolean result = itemPedidoService.userIsOwner(100L);

        assertFalse(result);
    }

    @Test
    @DisplayName("Should set nomeProdutor when oferta exists and query returns result")
    void toResponseDTOWithProdutor_WithValidOferta_ShouldSetNomeProdutor() {
        // Preparar item com oferta
        var oferta = new br.com.uesb.ceasadigital.api.features.oferta_produtor.model.OfertaProdutor();
        oferta.setId(99L);
        item.setOferta(oferta);

        when(mapper.toResponseDTO(item)).thenReturn(new ItemPedidoResponseDTO());

        Query mockQuery = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("ofertaId"), eq(99L))).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn("Produtor Teste");

        ItemPedidoResponseDTO result = invokePrivateMapper(item);

        assertEquals("Produtor Teste", result.getNomeProdutor());
    }

    // Auxiliar para invocar método privado via reflexão
    private ItemPedidoResponseDTO invokePrivateMapper(ItemPedido item) {
        try {
            var method = ItemPedidoService.class.getDeclaredMethod("toResponseDTOWithProdutor", ItemPedido.class);
            method.setAccessible(true);
            return (ItemPedidoResponseDTO) method.invoke(itemPedidoService, item);
        } catch (Exception e) {
            fail("Erro ao invocar método privado: " + e.getMessage());
            return null;
        }
    }
}
