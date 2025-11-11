package br.com.uesb.ceasadigital.api.features.lista.documentation;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.uesb.ceasadigital.api.features.carrinho.dto.response.CarrinhoResponseDTO;
import br.com.uesb.ceasadigital.api.features.lista.dto.request.ListaCreateRequestDTO;
import br.com.uesb.ceasadigital.api.features.lista.dto.request.ListaSendToCarrinhoRequestDTO;
import br.com.uesb.ceasadigital.api.features.lista.dto.response.ListaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Lista", description = "Endpoints para gerenciamento de listas de compras do usuário")
public interface ListaControllerDocs {

  @Operation(
    summary = "Criar lista",
    description = "Cria uma nova lista de compras pertencente ao usuário autenticado.",
    responses = {
      @ApiResponse(description = "Lista criada com sucesso", responseCode = "201",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = ListaResponseDTO.class))),
      @ApiResponse(description = "Requisição inválida", responseCode = "400", content = @Content),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<ListaResponseDTO> create(ListaCreateRequestDTO request);

  @Operation(
    summary = "Listar minhas listas",
    description = "Retorna todas as listas de compras do usuário autenticado.",
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          array = @ArraySchema(schema = @Schema(implementation = ListaResponseDTO.class)))),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<List<ListaResponseDTO>> findAll();

  @Operation(
    summary = "Buscar lista por ID",
    description = "Retorna os detalhes de uma lista do usuário autenticado.",
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = ListaResponseDTO.class))),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Lista não encontrada", responseCode = "404", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<ListaResponseDTO> findById(Long id);

  @Operation(
    summary = "Deletar lista",
    description = "Remove uma lista pertencente ao usuário autenticado.",
    responses = {
      @ApiResponse(description = "Lista deletada com sucesso", responseCode = "204", content = @Content),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Lista não encontrada", responseCode = "404", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<Void> delete(Long id);

  @Operation(
    summary = "Enviar todos os itens da lista ao carrinho",
    description = "Envia todos os itens da lista selecionada para o carrinho do usuário autenticado, mantendo as quantidades desejadas.",
    responses = {
      @ApiResponse(description = "Itens enviados ao carrinho", responseCode = "201",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = CarrinhoResponseDTO.class))),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Lista não encontrada", responseCode = "404", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<CarrinhoResponseDTO> sendAllToCarrinho(Long id);

  @Operation(
    summary = "Enviar itens selecionados da lista ao carrinho",
    description = "Envia apenas os itens especificados da lista selecionada para o carrinho do usuário autenticado.",
    responses = {
      @ApiResponse(description = "Itens enviados ao carrinho", responseCode = "201",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = CarrinhoResponseDTO.class))),
      @ApiResponse(description = "Requisição inválida", responseCode = "400", content = @Content),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Lista ou item da lista não encontrado", responseCode = "404", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<CarrinhoResponseDTO> sendSelectedToCarrinho(Long id, ListaSendToCarrinhoRequestDTO request);
}