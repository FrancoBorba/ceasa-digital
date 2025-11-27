package br.com.uesb.ceasadigital.api.features.produtor_produto.mapper;

import java.util.Optional;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.uesb.ceasadigital.api.features.categoria.model.Category;
import br.com.uesb.ceasadigital.api.features.product.mapper.ProductMapper;
import br.com.uesb.ceasadigital.api.features.produtor.mapper.ProdutorMapper;
import br.com.uesb.ceasadigital.api.features.produtor_produto.dto.response.ProdutorProdutoResponseDTO;
import br.com.uesb.ceasadigital.api.features.produtor_produto.model.ProdutorProduto;
import br.com.uesb.ceasadigital.api.features.user.mapper.UserMapper;

@Mapper(componentModel = "spring", uses = {ProdutorMapper.class, ProductMapper.class, UserMapper.class})
public interface ProdutorProdutoMapper {
    
    String BASE_URL = "http://localhost:8080";

    @Mapping(source = "produtor.id", target = "produtorId")
    @Mapping(source = "produtor.usuario.name", target = "produtorNome") 
    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "produtoNome")
    @Mapping(source = "atualizadoEm", target = "atualizadoEm") 
    @Mapping(source = "criadoEm", target = "criadoEm")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "produto.fotoUrl", target = "produtoImgUrl")
    @Mapping(target = "produtoCategoriaNome", ignore = true)
    ProdutorProdutoResponseDTO toResponseDTO(ProdutorProduto entity);
    
    @AfterMapping
    default void setCamposDerivados(ProdutorProduto entity, @MappingTarget ProdutorProdutoResponseDTO dto) {
        if (entity.getProduto() == null) {
            return;
        }

        if (entity.getProduto().getCategories() != null && !entity.getProduto().getCategories().isEmpty()) {
            Optional<Category> firstCategory = entity.getProduto().getCategories().stream().findFirst();
            if (firstCategory.isPresent()) {
                dto.setProdutoCategoriaNome(firstCategory.get().getName());
            } else {
                dto.setProdutoCategoriaNome(null); 
            }
        }
        
        String fotoUrlRelativa = entity.getProduto().getFotoUrl();
        
        if (fotoUrlRelativa != null && !fotoUrlRelativa.isBlank()) {
            String urlAbsoluta = BASE_URL + fotoUrlRelativa;
            dto.setProdutoImgUrl(urlAbsoluta);
        } else {
            // TODO: definir uma imagem "placeholder" caso nao haja foto
            // dto.setProdutoImgUrl(BASE_URL + "produtos/placeholder.jpg");
        }
    }
    
}