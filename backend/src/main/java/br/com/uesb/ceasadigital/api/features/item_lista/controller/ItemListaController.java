package br.com.uesb.ceasadigital.api.features.item_lista.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uesb.ceasadigital.api.features.item_lista.dto.request.ItemListaAddRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_lista.dto.request.ItemListaUpdateRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_lista.dto.response.ItemListaResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_lista.service.ItemListaService;
import br.com.uesb.ceasadigital.api.features.item_lista.documentation.ItemListaControllerDocs;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/listas/{listaId}/itens")
@Tag(name = "Lista", description = "Gerenciamento de listas de compras e seus itens")
public class ItemListaController implements ItemListaControllerDocs {

  @Autowired
  private ItemListaService itemListaService;

  @Override
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ItemListaResponseDTO> addItem(@PathVariable Long listaId, @RequestBody @Valid ItemListaAddRequestDTO request) {
    ItemListaResponseDTO response = itemListaService.addItem(listaId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Override
  @PutMapping(value = "/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ItemListaResponseDTO> updateItem(@PathVariable Long listaId, @PathVariable Long itemId, @RequestBody @Valid ItemListaUpdateRequestDTO request) {
    ItemListaResponseDTO response = itemListaService.updateItem(listaId, itemId, request);
    return ResponseEntity.ok(response);
  }

  @Override
  @DeleteMapping(value = "/{itemId}")
  public ResponseEntity<Void> deleteItem(@PathVariable Long listaId, @PathVariable Long itemId) {
    itemListaService.deleteItem(listaId, itemId);
    return ResponseEntity.noContent().build();
  }
}