package br.com.uesb.ceasadigital.api.features.item_lista.documentation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.uesb.ceasadigital.api.features.item_lista.dto.request.ItemListaAddRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_lista.dto.request.ItemListaUpdateRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_lista.dto.response.ItemListaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Lista", description = "Endpoints para gerenciar listas e seus itens")
public interface ItemListaControllerDocs {

  @Operation(
    summary = "Adicionar item à lista",
    description = "Adiciona um novo item à lista do usuário. Se o produto já estiver na lista, soma a quantidade desejada.",
    responses = {
      @ApiResponse(description = "Item adicionado com sucesso", responseCode = "201",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = ItemListaResponseDTO.class))),
      @ApiResponse(description = "Requisição inválida", responseCode = "400", content = @Content),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Produto ou lista não encontrada", responseCode = "404", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<ItemListaResponseDTO> addItem(Long listaId, ItemListaAddRequestDTO request);

  @Operation(
    summary = "Atualizar item da lista",
    description = "Atualiza a quantidade desejada de um item existente na lista do usuário.",
    responses = {
      @ApiResponse(description = "Item atualizado com sucesso", responseCode = "200",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = ItemListaResponseDTO.class))),
      @ApiResponse(description = "Requisição inválida", responseCode = "400", content = @Content),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Item não encontrado na lista", responseCode = "404", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<ItemListaResponseDTO> updateItem(Long listaId, Long itemId, ItemListaUpdateRequestDTO request);

  @Operation(
    summary = "Deletar item da lista",
    description = "Remove um item da lista do usuário autenticado.",
    responses = {
      @ApiResponse(description = "Item deletado com sucesso", responseCode = "204", content = @Content),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Item não encontrado na lista", responseCode = "404", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<Void> deleteItem(Long listaId, Long itemId);
}