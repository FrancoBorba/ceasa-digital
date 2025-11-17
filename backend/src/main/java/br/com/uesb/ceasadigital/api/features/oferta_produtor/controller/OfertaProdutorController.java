package br.com.uesb.ceasadigital.api.features.oferta_produtor.controller;

import br.com.uesb.ceasadigital.api.features.oferta_produtor.documentation.OfertaProdutorControllerDocs;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.dto.response.OfertaProdutorResponseDTO;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.service.OfertaProdutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ofertas-produtor")
public class OfertaProdutorController implements OfertaProdutorControllerDocs {
  
  @Autowired
  private OfertaProdutorService service;
  
  @Override
  @GetMapping("/me")
  @PreAuthorize("hasRole('ROLE_PRODUTOR')")
  public ResponseEntity<List<OfertaProdutorResponseDTO>> getMinhasOfertas(
  @RequestParam(required = false) String status) {
    
    return ResponseEntity.ok(service.listarMinhasOfertas(status));
  }
  
  @Override
  @PostMapping("/me/confirmar/{id}")
  @PreAuthorize("hasRole('ROLE_PRODUTOR')")
  public ResponseEntity<OfertaProdutorResponseDTO> confirmarOferta(@PathVariable Long id) {
    return ResponseEntity.ok(service.confirmarOferta(id));
  }
}