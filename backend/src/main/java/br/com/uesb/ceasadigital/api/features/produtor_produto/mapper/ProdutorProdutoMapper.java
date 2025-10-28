package br.com.uesb.ceasadigital.api.features.produtor_produto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.uesb.ceasadigital.api.features.product.mapper.ProductMapper;
import br.com.uesb.ceasadigital.api.features.produtor.mapper.ProdutorMapper;
import br.com.uesb.ceasadigital.api.features.produtor_produto.dto.response.ProdutorProdutoResponseDTO;
import br.com.uesb.ceasadigital.api.features.produtor_produto.model.ProdutorProduto;
import br.com.uesb.ceasadigital.api.features.user.mapper.UserMapper;

@Mapper(componentModel = "spring", uses = {ProdutorMapper.class, ProductMapper.class, UserMapper.class})
public interface ProdutorProdutoMapper {

    @Mapping(source = "produtor.id", target = "produtorId")
    @Mapping(source = "produtor.usuario.name", target = "produtorNome") 
    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "produtoNome")
    @Mapping(source = "atualizadoEm", target = "atualizadoEm") 
    @Mapping(source = "criadoEm", target = "criadoEm")
    @Mapping(source = "status", target = "status")
    ProdutorProdutoResponseDTO toResponseDTO(ProdutorProduto entity);

}