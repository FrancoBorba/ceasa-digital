package br.com.uesb.ceasadigital.api.features.categoria.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.uesb.ceasadigital.api.features.categoria.documentation.CategoryControllerDocs;
import br.com.uesb.ceasadigital.api.features.categoria.dto.CategoryDTO;
import br.com.uesb.ceasadigital.api.features.categoria.service.CategoryService;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController implements CategoryControllerDocs {
  
  @Autowired
  private CategoryService categoryService;

  @Override
  @GetMapping
  public ResponseEntity<Page<CategoryDTO>> findAll(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
    return ResponseEntity.ok(categoryService.findAll(pageable));
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
    return ResponseEntity.ok(categoryService.findById(id));
  }

  @Override
  @PostMapping
  public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO categoryDTO) {
    categoryDTO = categoryService.insert(categoryDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoryDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(categoryDTO);
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
    return ResponseEntity.ok(categoryService.update(id, categoryDTO));
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    categoryService.delete(id);
    return ResponseEntity.noContent().build();
  }

}