package br.com.uesb.ceasadigital.api.features.pedido.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.uesb.ceasadigital.api.features.pedido.dto.request.PedidoPostRequestDTO;
import br.com.uesb.ceasadigital.api.features.pedido.dto.request.PedidoPutRequestDTO;
import br.com.uesb.ceasadigital.api.features.pedido.dto.response.PedidoResponseDTO;
import br.com.uesb.ceasadigital.api.features.pedido.service.PedidoService;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/pedidos")
public class PedidoController {
  

  @Autowired
  private PedidoService pedidoService;

  @GetMapping()
  //@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
  public ResponseEntity<List<PedidoResponseDTO>> getAllPedidosByCurrentUser() {
    return ResponseEntity.ok(pedidoService.getAllPedidosByCurrentUser());
  }

  @GetMapping(value ="/{id}")
  //@PreAuthorize("hasRole('ROLE_ADMIN') or @pedidoService.userIsOwner(#id)")
  public ResponseEntity<PedidoResponseDTO> getPedidoById(@PathVariable Long id) {
      return ResponseEntity.ok(pedidoService.getPedidoById(id));
  }

  @PostMapping()
  //@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
  public ResponseEntity<PedidoResponseDTO> insertPedido(@Valid @RequestBody PedidoPostRequestDTO pedidoRequestDTO) {
    PedidoResponseDTO pedidoResponseDTO = pedidoService.insertPedido(pedidoRequestDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedidoResponseDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(pedidoResponseDTO);
  }

  @PutMapping(value ="/{id}")
  //@PreAuthorize("hasRole('ROLE_ADMIN') or @pedidoService.userIsOwner(#id)")
  public ResponseEntity<PedidoResponseDTO> updatePedido(@PathVariable Long id, @Valid @RequestBody PedidoPutRequestDTO pedidoRequestDTO) {
    return ResponseEntity.ok(pedidoService.updatePedido(id, pedidoRequestDTO));
  }

  @DeleteMapping(value ="/{id}")
  //@PreAuthorize("hasRole('ROLE_ADMIN') or @pedidoService.userIsOwner(#id)")
  public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
    pedidoService.deletePedido(id);
    return ResponseEntity.noContent().build();
  }


}
