package br.com.uesb.ceasadigital.api.features.endereco.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.uesb.ceasadigital.api.features.endereco.documentation.EnderecoControllerDocs;
import br.com.uesb.ceasadigital.api.features.endereco.dto.EnderecoDTO;
import br.com.uesb.ceasadigital.api.features.endereco.service.EnderecoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController implements EnderecoControllerDocs {

  @Autowired
  private EnderecoService enderecoService;

  @Override
  @GetMapping
  public ResponseEntity<List<EnderecoDTO>> listarMeusEnderecos() {
    return ResponseEntity.ok(enderecoService.listarMeusEnderecos());
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<EnderecoDTO> buscarEnderecoPorId(@PathVariable Long id) {
    return ResponseEntity.ok(enderecoService.buscarEnderecoPorId(id));
  }

  @Override
  @PostMapping
  public ResponseEntity<EnderecoDTO> criarEndereco(@Valid @RequestBody EnderecoDTO enderecoDTO) {
    EnderecoDTO enderecoSalvo = enderecoService.criarEndereco(enderecoDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(enderecoSalvo.getId())
        .toUri();
    return ResponseEntity.created(uri).body(enderecoSalvo);
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<EnderecoDTO> atualizarEndereco(
      @PathVariable Long id,
      @Valid @RequestBody EnderecoDTO enderecoDTO) {
    return ResponseEntity.ok(enderecoService.atualizarEndereco(id, enderecoDTO));
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
    enderecoService.deletarEndereco(id);
    return ResponseEntity.noContent().build();
  }
}

