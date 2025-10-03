package br.com.uesb.ceasadigital.api.features.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uesb.ceasadigital.api.features.product.documentation.ProductControllerDocs;
import br.com.uesb.ceasadigital.api.features.product.dto.ProductRequestDTO;
import br.com.uesb.ceasadigital.api.features.product.dto.ProductResponseUserDTO;
import br.com.uesb.ceasadigital.api.features.product.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/products")
public class ProductController implements ProductControllerDocs {
  
  @Autowired
  ProductService service;

  @Override
  @GetMapping(
    produces = {
      MediaType.APPLICATION_JSON_VALUE
    }
  )
  public ResponseEntity<List<ProductResponseUserDTO>> findAll() {

      return ResponseEntity.ok(service.findAllProducts());
  }
  
   @Override
   @GetMapping(
    value = "/{id}",
      produces = {
      MediaType.APPLICATION_JSON_VALUE
    })
  public ProductResponseUserDTO findById(@PathVariable(value = "id") Long id) {
    return service.findProductByID(id);
  }

  @Override
  @PostMapping(
    produces = {
      MediaType.APPLICATION_JSON_VALUE 
      },
    consumes = {
      MediaType.APPLICATION_JSON_VALUE
      }
  )
  public ProductResponseUserDTO createProduct(@RequestBody @Valid ProductRequestDTO productRequest) {
    return service.createProduct(productRequest);
  }


  @Override
  @PutMapping(
    value = "/{id}" ,
     produces = {
      MediaType.APPLICATION_JSON_VALUE 
      },
    consumes = {
      MediaType.APPLICATION_JSON_VALUE
      }
  )
  public ProductResponseUserDTO updateProduct(@PathVariable(value = "id")Long id ,@RequestBody @Valid ProductRequestDTO productRequestDTO) {
    return service.updateProduct(id,productRequestDTO);
  }

 

  @Override
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(Long id) {
     service.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

  
}
