package br.com.uesb.ceasadigital.api.features.item_pedido.controller;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.uesb.ceasadigital.api.features.item_pedido.dto.request.ItemPedidoPostRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_pedido.dto.request.ItemPedidoPutRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_pedido.dto.response.ItemPedidoResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_pedido.service.ItemPedidoService;
import jakarta.validation.Valid;

/**
 * @author: Caíque Santos Santana
 * @date: 14/10/2023
 * @description: Controller para gerenciar endpoints relacionados a ItemPedido.
 */
@RestController
@RequestMapping("/itens-pedido")
public class ItemPedidoController {

  @Autowired
  private ItemPedidoService itemPedidoService;

  // Listar todos os itens de pedido do usuário atual
  @GetMapping
  @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
  public ResponseEntity<Object> getAllItensByCurrentUser() {
    return ResponseEntity.ok(itemPedidoService.getAllItensByCurrentUser());
  }

  // Buscar item por ID
  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') or @itemPedidoService.userIsOwner(#id)")
  public ResponseEntity<Object> getItemById(@PathVariable Long id) {
    return ResponseEntity.ok(itemPedidoService.getItemById(id));
  }

  // Criar novo item de pedido
  @PostMapping
  @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
  public ResponseEntity<ItemPedidoResponseDTO> insertItem(@Valid @RequestBody ItemPedidoPostRequestDTO itemRequestDTO) {
    ItemPedidoResponseDTO itemResponseDTO = itemPedidoService.insertItem(itemRequestDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(itemResponseDTO.getId())
        .toUri();
    return ResponseEntity.created(uri).body(itemResponseDTO);
  }

  // Atualizar item existente
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') or @itemPedidoService.userIsOwner(#id)")
  public ResponseEntity<Object> updateItem(@PathVariable Long id,
      @Valid @RequestBody ItemPedidoPutRequestDTO itemRequestDTO) {
    return ResponseEntity.ok(itemPedidoService.updateItem(id, itemRequestDTO));
  }

  // Deletar item
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN') or @itemPedidoService.userIsOwner(#id)")
  public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
    itemPedidoService.deleteItem(id);
    return ResponseEntity.noContent().build();
  }
}
