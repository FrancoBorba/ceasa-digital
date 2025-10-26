package br.com.uesb.ceasadigital.api.features.product.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.uesb.ceasadigital.api.common.exceptions.ProductNotFoundException;
import br.com.uesb.ceasadigital.api.features.product.dto.ProductRequestDTO;
import br.com.uesb.ceasadigital.api.features.product.dto.ProductResponseUserDTO;
import br.com.uesb.ceasadigital.api.features.product.mapper.ProductMapper;
import br.com.uesb.ceasadigital.api.features.product.model.Product;
import br.com.uesb.ceasadigital.api.features.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService Unit Tests")
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductService service;

    private Product product;
    private ProductRequestDTO requestDTO;
    private ProductResponseUserDTO responseDTO;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setNome("Banana");
        product.setDescricao("Banana prata");
        product.setPreco(new BigDecimal("5.0"));
        product.setUnidadeDeMedida("Kg");

        requestDTO = new ProductRequestDTO();
        requestDTO.setNome("Banana");
        requestDTO.setDescricao("Banana prata");
        requestDTO.setPreco(new BigDecimal("5.0"));
        requestDTO.setUnidadeDeMedida("Kg");

        responseDTO = new ProductResponseUserDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Banana");
        responseDTO.setDescricao("Banana prata");
        responseDTO.setPreco(new BigDecimal("5.0"));
        responseDTO.setUnidadeDeMedida("Kg");
    }

    @Test
    @DisplayName("Should return paged products successfully")
    void findAllProducts_ShouldReturnPagedProducts() {
        // Arrange
        Page<Product> productPage = new PageImpl<>(List.of(product));
        when(repository.findAll(pageable)).thenReturn(productPage);
        when(mapper.productToProductResponseUserDTO(product)).thenReturn(responseDTO);

        // Act
        Page<ProductResponseUserDTO> result = service.findAllProducts(pageable);

        // Assert
        assertNotNull(result, "Result page should not be null");
        assertEquals(1, result.getTotalElements(), "Page should contain exactly one product");
        assertEquals("Banana", result.getContent().get(0).getNome(), "Product name should match");
        verify(repository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Should return product when ID is valid")
    void findProductByID_ShouldReturnProduct_WhenIdExists() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(mapper.productToProductResponseUserDTO(product)).thenReturn(responseDTO);

        // Act
        ProductResponseUserDTO result = service.findProductByID(1L);

        // Assert
        assertNotNull(result, "Response DTO should not be null");
        assertEquals("Banana", result.getNome(), "Returned product name should be 'Banana'");
        verify(repository).findById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Should throw exception when product is not found by ID")
    void findProductByID_ShouldThrowException_WhenIdDoesNotExist() {
        // Arrange
        Long nonExistentId = 999L;
        when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ProductNotFoundException.class, () -> {
            service.findProductByID(nonExistentId);
        });

        verify(repository, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("Should create a new product successfully")
    void createProduct_ShouldReturnResponse_WhenProductIsSaved() {
        // Arrange
        when(mapper.toEntity(requestDTO)).thenReturn(product);
        when(repository.save(product)).thenReturn(product);
        when(mapper.productToProductResponseUserDTO(product)).thenReturn(responseDTO);

        // Act
        ProductResponseUserDTO result = service.createProduct(requestDTO);

        // Assert
        assertNotNull(result, "Created product should not be null");
        assertEquals("Banana", result.getNome(), "Created product name should match");
        verify(repository).save(product);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Should update an existing product successfully")
    void updateProduct_ShouldReturnUpdatedResponse_WhenProductExists() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.save(product)).thenReturn(product);
        when(mapper.productToProductResponseUserDTO(product)).thenReturn(responseDTO);

        // Act
        ProductResponseUserDTO result = service.updateProduct(1L, requestDTO);

        // Assert
        assertNotNull(result, "Updated product should not be null");
        assertEquals("Banana", result.getNome(), "Updated product name should match");
        verify(repository).findById(1L);
        verify(repository).save(product);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Should throw exception when trying to update non-existing product")
    void updateProduct_ShouldThrowException_WhenProductDoesNotExist() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.updateProduct(99L, requestDTO),
                "Expected RuntimeException when updating non-existing product");

        verify(repository).findById(99L);
        verify(repository, never()).save(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Should delete product successfully when ID exists")
    void deleteProduct_ShouldDeleteSuccessfully_WhenIdExists() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        service.deleteProduct(1L);

        // Assert
        verify(repository).findById(1L);
        verify(repository).delete(product);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existing product")
    void deleteProduct_ShouldThrowException_WhenIdDoesNotExist() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.deleteProduct(99L),
                "Expected RuntimeException when deleting non-existing product");

        verify(repository).findById(99L);
        verify(repository, never()).delete(any());
        verifyNoMoreInteractions(repository);
    }
}