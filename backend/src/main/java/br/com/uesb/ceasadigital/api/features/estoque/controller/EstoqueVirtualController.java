package br.com.uesb.ceasadigital.api.features.estoque.controller;

import br.com.uesb.ceasadigital.api.features.estoque.dto.request.EstoqueVirtualRequestDTO;
import br.com.uesb.ceasadigital.api.features.estoque.dto.response.EstoqueVirtualResponseDTO;
import br.com.uesb.ceasadigital.api.features.estoque.service.EstoqueVirtualService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/metas-estoque")
@PreAuthorize("hasRole('ROLE_ADMIN')") // Protege todos os endpoints da classe
public class EstoqueVirtualController {
  
  private final EstoqueVirtualService estoqueService;
  
  public EstoqueVirtualController(EstoqueVirtualService estoqueService) {
    this.estoqueService = estoqueService;
  }
  
  @PostMapping
  public ResponseEntity<EstoqueVirtualResponseDTO> create(@Valid @RequestBody EstoqueVirtualRequestDTO requestDTO) {
    EstoqueVirtualResponseDTO responseDTO = estoqueService.create(requestDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
    .buildAndExpand(responseDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(responseDTO);
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<EstoqueVirtualResponseDTO> findById(@PathVariable Long id) {
    return ResponseEntity.ok(estoqueService.findById(id));
  }
  
  @GetMapping
  public ResponseEntity<List<EstoqueVirtualResponseDTO>> findAll() {
    return ResponseEntity.ok(estoqueService.findAll());
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<EstoqueVirtualResponseDTO> update(@PathVariable Long id, @Valid @RequestBody EstoqueVirtualRequestDTO requestDTO) {
    return ResponseEntity.ok(estoqueService.update(id, requestDTO));
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<EstoqueVirtualResponseDTO> delete(@PathVariable Long id) {
    EstoqueVirtualResponseDTO deletedDTO = estoqueService.delete(id);
    return ResponseEntity.ok(deletedDTO);
  }
}