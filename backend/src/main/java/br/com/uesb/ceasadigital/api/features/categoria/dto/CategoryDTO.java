package br.com.uesb.ceasadigital.api.features.categoria.dto;

import br.com.uesb.ceasadigital.api.features.categoria.model.Category;

public class CategoryDTO {
  private Long id;
  private String name;

  public CategoryDTO(Category category) {
    this.id = category.getId();
    this.name = category.getName();
  }

  public CategoryDTO() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
