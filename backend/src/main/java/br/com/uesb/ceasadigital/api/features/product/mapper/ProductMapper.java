package br.com.uesb.ceasadigital.api.features.product.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.uesb.ceasadigital.api.features.product.dto.ProductRequestDTO;
import br.com.uesb.ceasadigital.api.features.product.dto.ProductResponseAdminDTO;
import br.com.uesb.ceasadigital.api.features.product.dto.ProductResponseUserDTO;
import br.com.uesb.ceasadigital.api.features.product.model.Product;
import br.com.uesb.ceasadigital.api.features.product.model.Enum.UnidadeMedida;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  Product toEntity(ProductRequestDTO requestDTO);

  ProductResponseAdminDTO productToProductResponseAdminDTO(Product product);

  ProductResponseUserDTO productToProductResponseUserDTO(Product product);

  List<ProductResponseUserDTO> toProductUserDTOList(List<Product> products);

  List<ProductResponseAdminDTO> toProductAdminDTOList(List<Product> products);

  default UnidadeMedida mapUnidade(String value) {
    return UnidadeMedida.fromValue(value);
  }
}
