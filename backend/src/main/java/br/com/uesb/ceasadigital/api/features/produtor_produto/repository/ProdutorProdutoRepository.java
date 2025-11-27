package br.com.uesb.ceasadigital.api.features.produtor_produto.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.uesb.ceasadigital.api.features.product.model.Product;
import br.com.uesb.ceasadigital.api.features.produtor.model.Produtor;
import br.com.uesb.ceasadigital.api.features.produtor_produto.model.ProdutorProduto;

public interface ProdutorProdutoRepository extends JpaRepository<ProdutorProduto, Long> {
    Optional<ProdutorProduto> findByProdutorAndProduto(Produtor produtor, Product produto);
    List<ProdutorProduto> findAllByStatus(String status);
    List<ProdutorProduto> findAllByProdutorId(Long produtorId);

    @Query("SELECT pp FROM ProdutorProduto pp WHERE pp.produtor.id = :produtorId AND pp.status IN :statuses")
    List<ProdutorProduto> findAllByProdutorIdAndStatusIn(@Param("produtorId") Long produtorId, @Param("statuses") Set<String> statuses);

    /**
     * Encontra todas as permissões ativas para um produto específico.
     * Usado para saber quais produtores devem receber uma oferta quando uma meta de estoque é criada.
     */
    List<ProdutorProduto> findAllByProdutoIdAndStatus(Long produtoId, String status);
}