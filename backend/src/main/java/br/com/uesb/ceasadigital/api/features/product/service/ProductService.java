package br.com.uesb.ceasadigital.api.features.product.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
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

   private final Logger logger = LoggerFactory.getLogger(ProductService.class.getName());

  public List<ProductResponseUserDTO> findAllProducts(){

    logger.info("Find All Products");
    
    List<Product> allProducts = repository.findAll();

    return mapper.toProductUserDTOList(allProducts);
  }

  @Transactional(readOnly = true)
  public ProductResponseUserDTO findProductByID(Long id){
      logger.info("Find product with id: " + id);

     // TODO:
    // Throw Excpetions like price negative , Null name , etc
      var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

      return mapper.productToProductResponseUserDTO(entity);
  }


  @Transactional
  public ProductResponseUserDTO createProduct(ProductRequestDTO productRequestDTO){
    logger.info("Create a product");
    // TODO:
    // Throw Excpetions like price negative , Null name , etc
    var entity = mapper.toEntity(productRequestDTO);

    repository.save(entity);

    return mapper.productToProductResponseUserDTO(entity);
  }

  @Transactional
  public ProductResponseUserDTO updateProduct(Long id , ProductRequestDTO productRequestDTO){
    
    var entity = repository.findById(id).orElseThrow();

    logger.info("Update a info about the item with id : " + id);
    entity.setDescricao(productRequestDTO.getDescricao());
    entity.setNome(productRequestDTO.getNome());
    entity.setPreco(productRequestDTO.getPreco());
    entity.setUnidadeDeMedida(productRequestDTO.getUnidadeDeMedida());

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
