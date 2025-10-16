package br.com.uesb.ceasadigital.api.features.item_pedido.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import br.com.uesb.ceasadigital.api.features.item_pedido.service.ItemPedidoService;

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
  // @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
  public ResponseEntity<Object> getAllItensByCurrentUser() {
    return ResponseEntity.ok(itemPedidoService.getAllItensByCurrentUser());
  }

  // Buscar item por ID
  @GetMapping("/{id}")
  // @PreAuthorize("hasRole('ROLE_ADMIN') or @itemPedidoService.userIsOwner(#id)")
  public ResponseEntity<Object> getItemById(@PathVariable Long id) {
    return ResponseEntity.ok(itemPedidoService.getItemById(id));
  }

  @GetMapping("/pedido/{pedidoId}")
  // @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
  public ResponseEntity<Object> getAllItensByPedidoId(@PathVariable Long pedidoId) {
    return ResponseEntity.ok(itemPedidoService.getAllItensByPedidoId(pedidoId));
  }
}
