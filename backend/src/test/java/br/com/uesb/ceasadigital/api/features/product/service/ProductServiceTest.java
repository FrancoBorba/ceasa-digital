package br.com.uesb.ceasadigital.api.features.product.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;

import br.com.uesb.ceasadigital.api.features.product.dto.ProductRequestDTO;
import br.com.uesb.ceasadigital.api.features.product.dto.ProductResponseUserDTO;
import br.com.uesb.ceasadigital.api.features.product.mapper.ProductMapper;
import br.com.uesb.ceasadigital.api.features.product.model.Product;
import br.com.uesb.ceasadigital.api.features.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService Tests")
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
    @DisplayName("Should return all products successfully")
    void findAllProducts_ShouldReturnListOfProducts() {
        // Arrange
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Page<Product> productPage = new PageImpl<>(productList);
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findAll(pageable)).thenReturn(productPage);
        when(mapper.productToProductResponseUserDTO(product)).thenReturn(responseDTO);

        // Act
        Page<ProductResponseUserDTO> result = service.findAllProducts(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Banana", result.getContent().get(0).getNome());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should return product when ID is valid")
    void findProductByID_WithValidId_ShouldReturnProduct() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(mapper.productToProductResponseUserDTO(product)).thenReturn(responseDTO);

        // Act
        ProductResponseUserDTO result = service.findProductByID(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Banana", result.getNome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when product ID is not found")
    void findProductByID_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.findProductByID(99L));
        verify(repository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should create a new product successfully")
    void createProduct_ShouldSaveAndReturnResponse() {
        // Arrange
        when(mapper.toEntity(requestDTO)).thenReturn(product);
        when(mapper.productToProductResponseUserDTO(product)).thenReturn(responseDTO);
        when(repository.save(product)).thenReturn(product);

        // Act
        ProductResponseUserDTO result = service.createProduct(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Banana", result.getNome());
        verify(repository, times(1)).save(product);
    }

    @Test
    @DisplayName("Should update an existing product successfully")
    void updateProduct_ShouldUpdateAndReturnResponse() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(mapper.productToProductResponseUserDTO(product)).thenReturn(responseDTO);

        // Act
        ProductResponseUserDTO result = service.updateProduct(1L, requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Banana", result.getNome());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(product);
    }

    @Test
    @DisplayName("Should delete a product successfully when ID exists")
    void deleteProduct_WithValidId_ShouldDeleteProduct() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        service.deleteProduct(1L);

        // Assert
        verify(repository, times(1)).delete(product);
    }

    @Test
    @DisplayName("Should throw exception when trying to delete non-existing product")
    void deleteProduct_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.deleteProduct(99L));
        verify(repository, never()).delete(any());
    }
}
