package br.com.uesb.ceasadigital.api.features.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uesb.ceasadigital.api.features.product.model.Product;

public interface ProductRepository extends JpaRepository<Product , Long> {
  
}
