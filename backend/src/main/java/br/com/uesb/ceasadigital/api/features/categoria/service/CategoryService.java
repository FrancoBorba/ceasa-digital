package br.com.uesb.ceasadigital.api.features.categoria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.uesb.ceasadigital.api.common.exceptions.DatabaseException;
import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.features.categoria.dto.CategoryDTO;
import br.com.uesb.ceasadigital.api.features.categoria.model.Category;
import br.com.uesb.ceasadigital.api.features.categoria.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepostory;

  @Transactional(readOnly = true)
  public Page<CategoryDTO> findAll(Pageable pageable) {
    Page<Category> categories = categoryRepostory.findAll(pageable);
    return categories.map(CategoryDTO::new);
  }

  @Transactional(readOnly = true)
  public CategoryDTO findById(Long id) {
    Category category = categoryRepostory.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    return new CategoryDTO(category);
  }

  @Transactional
  public CategoryDTO insert(CategoryDTO categoryDTO) {
    Category category = new Category();
    category.setName(categoryDTO.getName());
    category = categoryRepostory.save(category);
    return new CategoryDTO(category);
  }

  @Transactional
  public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
    try {
      Category entity = categoryRepostory.getReferenceById(id);
      entity.setName(categoryDTO.getName());
      entity = categoryRepostory.save(entity);
      return new CategoryDTO(entity);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Category not found");
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    if (!categoryRepostory.existsById(id)) {
      categoryRepostory.existsById(id);
      throw new ResourceNotFoundException("Category not found");
    }
    try {
      categoryRepostory.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Integrity violation error");
    }
  }
}
