package br.com.uesb.ceasadigital.api.features.item_pedido.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.uesb.ceasadigital.api.features.item_pedido.dto.response.ItemPedidoResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_pedido.service.ItemPedidoService;

@RestController
@RequestMapping("/itens-pedido")
public class ItemPedidoController {

  @Autowired
  private ItemPedidoService itemPedidoService;

  // Listar todos os itens de pedido do usuário atual (paginado)
  @GetMapping("/usuario")
  public ResponseEntity<Page<ItemPedidoResponseDTO>> getItensByUsuario(
      @RequestParam Long usuarioId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String direction) {

    Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

    Page<ItemPedidoResponseDTO> itens = itemPedidoService.getItensByUsuario(usuarioId, pageable);
    return ResponseEntity.ok(itens);
  }

  // Buscar item por ID
  @GetMapping("/{id}")
  public ResponseEntity<ItemPedidoResponseDTO> getItemById(@PathVariable Long id) {
    ItemPedidoResponseDTO item = itemPedidoService.getItemById(id);
    return ResponseEntity.ok(item);
  }

  // Listar todos os itens de um pedido específico (paginado)
  @GetMapping("/pedido/{pedidoId}")
  public ResponseEntity<Page<ItemPedidoResponseDTO>> getAllItensByPedidoId(
      @PathVariable Long pedidoId,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
      @RequestParam(value = "direction", defaultValue = "asc") String direction) {
    Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
    Page<ItemPedidoResponseDTO> itens = itemPedidoService.getItensByPedidoId(pedidoId, pageable);
    return ResponseEntity.ok(itens);
  }
}
