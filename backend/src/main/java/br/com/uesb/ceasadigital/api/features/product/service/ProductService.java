package br.com.uesb.ceasadigital.api.features.product.service;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.uesb.ceasadigital.api.common.exceptions.InvalidProductException;
import br.com.uesb.ceasadigital.api.common.exceptions.ProductNotFoundException;
import br.com.uesb.ceasadigital.api.features.categoria.dto.CategoryDTO;
import br.com.uesb.ceasadigital.api.features.product.dto.ProductRequestDTO;
import br.com.uesb.ceasadigital.api.features.product.dto.ProductResponseUserDTO;
import br.com.uesb.ceasadigital.api.features.product.mapper.ProductMapper;
import br.com.uesb.ceasadigital.api.features.product.model.Product;
import br.com.uesb.ceasadigital.api.features.product.model.Enum.UnidadeMedida;
import br.com.uesb.ceasadigital.api.features.product.repository.ProductRepository;

@Service
public class ProductService {

  @Autowired
  private ProductRepository repository;

  @Autowired
  private ProductMapper mapper;

  private final Logger logger = LoggerFactory.getLogger(ProductService.class.getName());

  public Page<ProductResponseUserDTO> findAllProducts(Pageable pageable) {

    logger.info("Find All Products");

    Page<Product> products = repository.findAllWithCategories(pageable);

    return products.map(product -> new ProductResponseUserDTO(product,
        product.getCategories().stream().map(CategoryDTO::new).collect(Collectors.toList())));
  }

  @Transactional(readOnly = true)
  public ProductResponseUserDTO findProductByID(Long id) {
    logger.info("Find product with id: " + id);

    // TODO:
    // Throw Excpetions like price negative , Null name , etc
    var entity = repository.findByIdWithCategories(id).orElseThrow(() -> new ProductNotFoundException(id));

    return new ProductResponseUserDTO(entity,
        entity.getCategories().stream().map(CategoryDTO::new).collect(Collectors.toList()));
  }

  @Transactional
  public ProductResponseUserDTO createProduct(ProductRequestDTO productRequestDTO) {
    logger.info("Create a product");

    if (productRequestDTO.getNome() == null || productRequestDTO.getNome().isBlank()) {
      throw new InvalidProductException("O nome do produto não pode ser vazio.");
    }
    if (productRequestDTO.getPreco() != null && productRequestDTO.getPreco().doubleValue() < 0) {
      throw new InvalidProductException("O preço do produto não pode ser negativo.");
    }
    // TODO:
    // Throw Excpetions like price negative , Null name , etc
    var entity = mapper.toEntity(productRequestDTO);
    entity.setUnidadeDeMedida(
        UnidadeMedida.fromValue(productRequestDTO.getUnidadeDeMedida()));

    repository.save(entity);

    return mapper.productToProductResponseUserDTO(entity);
  }

  @Transactional
  public ProductResponseUserDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {

    var entity = repository.findById(id).orElseThrow();

    if (productRequestDTO.getPreco() != null && productRequestDTO.getPreco().doubleValue() < 0) {
      throw new InvalidProductException("Preço inválido ao atualizar o produto.");
    }

    logger.info("Update a info about the item with id : " + id);
    entity.setDescricao(productRequestDTO.getDescricao());
    entity.setNome(productRequestDTO.getNome());
    entity.setPreco(productRequestDTO.getPreco());
    entity.setUnidadeDeMedida(
        UnidadeMedida.fromValue(productRequestDTO.getUnidadeDeMedida()));

    repository.save(entity);

    return mapper.productToProductResponseUserDTO(entity);
  }

  @Transactional
  public void deleteProduct(Long id) {
    logger.info("Deletando o produto com ID: " + id);

    Product entity = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Produto with {} not found: " + id)); //
    repository.delete(entity);
  }
}