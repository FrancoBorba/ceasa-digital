package br.com.uesb.ceasadigital.api.features.product.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uesb.ceasadigital.api.exceptions.InvalidProductException;
import br.com.uesb.ceasadigital.api.exceptions.ProductNotFoundException;
import br.com.uesb.ceasadigital.api.features.product.dto.ProductRequestDTO;
import br.com.uesb.ceasadigital.api.features.product.dto.ProductResponseUserDTO;
import br.com.uesb.ceasadigital.api.features.product.mapper.ProductMapper;
import br.com.uesb.ceasadigital.api.features.product.model.Product;
import br.com.uesb.ceasadigital.api.features.product.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired 
    private ProductMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public List<ProductResponseUserDTO> findAllProducts() {
        logger.info("Find All Products");
        List<Product> allProducts = repository.findAll();
        return mapper.toProductUserDTOList(allProducts);
    }

    public ProductResponseUserDTO findProductByID(Long id) {
        logger.info("Find product with id: {}", id);
        var entity = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return mapper.productToProductResponseUserDTO(entity);
    }

    public ProductResponseUserDTO createProduct(ProductRequestDTO productRequestDTO) {
        logger.info("Create a product");

        if (productRequestDTO.getNome() == null || productRequestDTO.getNome().isBlank()) {
            throw new InvalidProductException("O nome do produto não pode ser vazio.");
        }

        if (productRequestDTO.getPreco() != null && productRequestDTO.getPreco().doubleValue() < 0) {
            throw new InvalidProductException("O preço do produto não pode ser negativo.");
        }

        var entity = mapper.toEntity(productRequestDTO);
        repository.save(entity);
        return mapper.productToProductResponseUserDTO(entity);
    }

    public ProductResponseUserDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        logger.info("Update product with id: {}", id);

        if (productRequestDTO.getPreco() != null && productRequestDTO.getPreco().doubleValue() < 0) {
            throw new InvalidProductException("Preço inválido ao atualizar o produto.");
        }

        entity.setDescricao(productRequestDTO.getDescricao());
        entity.setNome(productRequestDTO.getNome());
        entity.setPreco(productRequestDTO.getPreco());
        entity.setUnidadeDeMedida(productRequestDTO.getUnidadeDeMedida());

        repository.save(entity);
        return mapper.productToProductResponseUserDTO(entity);
    }

    public void deleteProduct(Long id) {
        logger.info("Deletando produto com ID: {}", id);

        Product entity = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        repository.delete(entity);
    }
}
