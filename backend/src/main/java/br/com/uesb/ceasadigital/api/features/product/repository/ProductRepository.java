package br.com.uesb.ceasadigital.api.features.product.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.uesb.ceasadigital.api.features.product.model.Product;

public interface ProductRepository extends JpaRepository<Product , Long> {
  @Query(value = "SELECT DISTINCT obj FROM Product obj LEFT JOIN FETCH obj.categories",
  countQuery = "SELECT COUNT(DISTINCT obj) FROM Product obj")
  Page<Product> findAllWithCategories(Pageable pageable);

  @Query("SELECT obj FROM Product obj LEFT JOIN FETCH obj.categories WHERE obj.id = :id")
  Optional<Product> findByIdWithCategories(@Param("id") Long id);
}
