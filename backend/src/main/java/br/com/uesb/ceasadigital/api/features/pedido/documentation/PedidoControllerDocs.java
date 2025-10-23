package br.com.uesb.ceasadigital.api.features.pedido.documentation;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.uesb.ceasadigital.api.features.pedido.dto.request.FinalizarCarrinhoRequestDTO;
import br.com.uesb.ceasadigital.api.features.pedido.dto.request.PedidoPutRequestDTO;
import br.com.uesb.ceasadigital.api.features.pedido.dto.response.PedidoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public interface PedidoControllerDocs {

  @Operation(
    summary = "Listar todos os pedidos do usuário",
    description = "Retorna todos os pedidos do usuário autenticado",
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200", 
        content = @Content(schema = @Schema(implementation = PedidoResponseDTO.class))),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<List<PedidoResponseDTO>> getAllPedidosByCurrentUser();

  @Operation(
    summary = "Buscar pedido por ID",
    description = "Retorna os detalhes completos de um pedido específico",
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200",
        content = @Content(schema = @Schema(implementation = PedidoResponseDTO.class))),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Pedido não encontrado", responseCode = "404", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<PedidoResponseDTO> getPedidoById(Long id);

  @Operation(
    summary = "Criar pedido (finalizar carrinho)",
    description = """
        Converte o carrinho especificado em um pedido finalizado.
        
        **Fluxo:**
        1. Valida que o carrinho pertence ao usuário
        2. Valida que o carrinho não está vazio
        3. Valida que o endereço pertence ao usuário
        4. Cria o pedido com status AGUARDANDO_PAGAMENTO
        5. Converte todos os itens do carrinho em itens do pedido
        6. Calcula o valor total
        7. Limpa o carrinho
        8. Retorna o pedido criado com todos os detalhes
        
        **Importante:** O carrinho será limpo após a criação do pedido.
        **Obrigatório:** Informar tanto o carrinho_id quanto o endereco_id.
        """,
    responses = {
      @ApiResponse(description = "Pedido criado com sucesso", responseCode = "201",
        content = @Content(schema = @Schema(implementation = PedidoResponseDTO.class))),
      @ApiResponse(description = "Carrinho vazio ou endereço inválido", responseCode = "400", content = @Content),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Endereço não encontrado", responseCode = "404", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<PedidoResponseDTO> criarPedido(FinalizarCarrinhoRequestDTO request);

  @Operation(
    summary = "Atualizar status do pedido",
    description = "Atualiza apenas o status de um pedido existente. O valor total não pode ser alterado após a criação do pedido.",
    responses = {
      @ApiResponse(description = "Pedido atualizado com sucesso", responseCode = "200",
        content = @Content(schema = @Schema(implementation = PedidoResponseDTO.class))),
      @ApiResponse(description = "Requisição inválida", responseCode = "400", content = @Content),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Pedido não encontrado", responseCode = "404", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<PedidoResponseDTO> updatePedido(Long id, PedidoPutRequestDTO pedidoRequestDTO);

  @Operation(
    summary = "Deletar pedido",
    description = "Remove um pedido do sistema",
    responses = {
      @ApiResponse(description = "Pedido deletado com sucesso", responseCode = "204", content = @Content),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Pedido não encontrado", responseCode = "404", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<Void> deletePedido(Long id);
}

