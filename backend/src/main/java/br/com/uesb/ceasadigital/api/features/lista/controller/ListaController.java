package br.com.uesb.ceasadigital.api.features.lista.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.uesb.ceasadigital.api.features.carrinho.dto.response.CarrinhoResponseDTO;
import br.com.uesb.ceasadigital.api.features.lista.dto.request.ListaCreateRequestDTO;
import br.com.uesb.ceasadigital.api.features.lista.dto.request.ListaSendToCarrinhoRequestDTO;
import br.com.uesb.ceasadigital.api.features.lista.dto.response.ListaResponseDTO;
import br.com.uesb.ceasadigital.api.features.lista.service.ListaCarrinhoService;
import br.com.uesb.ceasadigital.api.features.lista.service.ListaService;
import br.com.uesb.ceasadigital.api.features.lista.documentation.ListaControllerDocs;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/listas", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Lista", description = "Gerenciamento de listas de compras do usuário")
public class ListaController implements ListaControllerDocs {

  @Autowired
  private ListaService listaService;

  @Autowired
  private ListaCarrinhoService listaCarrinhoService;

  @Override
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ListaResponseDTO> create(@Valid @RequestBody ListaCreateRequestDTO request) {
    ListaResponseDTO created = listaService.create(request);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(created.getId())
        .toUri();
    return ResponseEntity.created(uri).body(created);
  }

  @Override
  @GetMapping
  public ResponseEntity<List<ListaResponseDTO>> findAll() {
    return ResponseEntity.ok(listaService.findAll());
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<ListaResponseDTO> findById(@PathVariable Long id) {
    return ResponseEntity.ok(listaService.findById(id));
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    listaService.delete(id);
    return ResponseEntity.noContent().build();
  }

  // Enviar todos os itens ao carrinho
  @Override
  @PostMapping("/{id}/enviar-carrinho/todos")
  public ResponseEntity<CarrinhoResponseDTO> sendAllToCarrinho(@PathVariable Long id) {
    CarrinhoResponseDTO response = listaCarrinhoService.sendAllItemsToCarrinho(id);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // Enviar itens selecionados ao carrinho
  @Override
  @PostMapping(value = "/{id}/enviar-carrinho/selecionados", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CarrinhoResponseDTO> sendSelectedToCarrinho(
      @PathVariable Long id,
      @Valid @RequestBody ListaSendToCarrinhoRequestDTO request) {
    CarrinhoResponseDTO response = listaCarrinhoService.sendSelectedItemsToCarrinho(id, request.getItemIds());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}