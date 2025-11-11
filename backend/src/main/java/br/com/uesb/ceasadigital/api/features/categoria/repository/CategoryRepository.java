package br.com.uesb.ceasadigital.api.features.categoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.uesb.ceasadigital.api.features.categoria.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
  
}
