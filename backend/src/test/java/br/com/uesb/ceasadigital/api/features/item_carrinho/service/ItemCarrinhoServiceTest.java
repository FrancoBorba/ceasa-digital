package br.com.uesb.ceasadigital.api.features.item_carrinho.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.carrinho.dto.response.CarrinhoResponseDTO;
import br.com.uesb.ceasadigital.api.features.carrinho.model.Carrinho;
import br.com.uesb.ceasadigital.api.features.carrinho.service.CarrinhoService;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.request.CarrinhoAddItemRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.request.CarrinhoUpdateItemRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.response.CarrinhoItemResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.mapper.ItemCarrinhoMapper;
import br.com.uesb.ceasadigital.api.features.item_carrinho.model.ItemCarrinho;
import br.com.uesb.ceasadigital.api.features.item_carrinho.repository.ItemCarrinhoRepository;
import br.com.uesb.ceasadigital.api.features.product.model.Product;
import br.com.uesb.ceasadigital.api.features.product.repository.ProductRepository;

import jakarta.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
@DisplayName("ItemCarrinhoService Unit Tests")
class ItemCarrinhoServiceTest {

    @Mock
    private CarrinhoService carrinhoService;

    @Mock
    private ItemCarrinhoMapper mapper;

    @Mock
    private ItemCarrinhoRepository repository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ItemCarrinhoService service;

    private Carrinho carrinho;
    private CarrinhoResponseDTO carrinhoResponseDTO;
    private ItemCarrinho itemCarrinho;
    private Product product;

    @BeforeEach
    void setUp() {
        carrinho = new Carrinho();
        carrinho.setId(1L);

        carrinhoResponseDTO = new CarrinhoResponseDTO();
        carrinhoResponseDTO.setId(1L);

        product = new Product();
        product.setId(1L);
        product.setPreco(new BigDecimal("10.0"));

        itemCarrinho = new ItemCarrinho();
        itemCarrinho.setId(1L);
        itemCarrinho.setCarrinho(carrinho);
        itemCarrinho.setProduto(product);
        itemCarrinho.setQuantidade(BigDecimal.valueOf(2));
        itemCarrinho.setPrecoUnitarioArmazenado(product.getPreco());
    }

    @Test
    @DisplayName("Should add new item to carrinho")
    void addItemInCarrinho_ShouldAddNewItem() {
        CarrinhoAddItemRequestDTO request = new CarrinhoAddItemRequestDTO();
        request.setProdutoID(product.getId());
        request.setQuantidade(BigDecimal.valueOf(3));

        when(carrinhoService.getCarrinho()).thenReturn(carrinho);
        when(repository.findByCarrinhoAndProdutoId(carrinho, product.getId())).thenReturn(Optional.empty());
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(carrinhoService.findCarrinho()).thenReturn(carrinhoResponseDTO);

        CarrinhoResponseDTO result = service.addItemInCarrinho(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).save(any(ItemCarrinho.class));
        verify(entityManager).clear();
    }

    @Test
    @DisplayName("Should increment quantity if item already exists")
    void addItemInCarrinho_ShouldIncrementQuantity() {
        CarrinhoAddItemRequestDTO request = new CarrinhoAddItemRequestDTO();
        request.setProdutoID(product.getId());
        request.setQuantidade(BigDecimal.valueOf(3));

        itemCarrinho.setQuantidade(BigDecimal.valueOf(2));

        when(carrinhoService.getCarrinho()).thenReturn(carrinho);
        when(repository.findByCarrinhoAndProdutoId(carrinho, product.getId()))
                .thenReturn(Optional.of(itemCarrinho));
        when(carrinhoService.findCarrinho()).thenReturn(carrinhoResponseDTO);

        service.addItemInCarrinho(request);

        assertEquals(BigDecimal.valueOf(5), itemCarrinho.getQuantidade());
        verify(repository).save(itemCarrinho);
        verify(entityManager).clear();
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when product not found")
    void addItemInCarrinho_ShouldThrow_WhenProductNotFound() {
        CarrinhoAddItemRequestDTO request = new CarrinhoAddItemRequestDTO();
        request.setProdutoID(999L);
        request.setQuantidade(BigDecimal.ONE);

        when(carrinhoService.getCarrinho()).thenReturn(carrinho);
        when(repository.findByCarrinhoAndProdutoId(carrinho, 999L)).thenReturn(Optional.empty());
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.addItemInCarrinho(request));
    }

    @Test
    @DisplayName("Should update item quantity")
    void updateItemInCarrinho_ShouldUpdateQuantity() {
        CarrinhoUpdateItemRequestDTO request = new CarrinhoUpdateItemRequestDTO();
        request.setQuantidade(BigDecimal.valueOf(10));

        when(repository.findById(1L)).thenReturn(Optional.of(itemCarrinho));
        when(repository.save(itemCarrinho)).thenReturn(itemCarrinho);
        when(mapper.toResponseDTO(itemCarrinho)).thenReturn(new CarrinhoItemResponseDTO());

        CarrinhoItemResponseDTO response = service.updateItemInCarrinho(1L, request);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(10), itemCarrinho.getQuantidade());
        verify(repository).save(itemCarrinho);
        verify(mapper).toResponseDTO(itemCarrinho);
    }

    @Test
    @DisplayName("Should delete item from carrinho")
    void deleteItemFromCarrinho_ShouldDeleteItem() {
        service.deleteItemFromCarrinho(1L);
        verify(repository).deleteById(1L);
    }
}
