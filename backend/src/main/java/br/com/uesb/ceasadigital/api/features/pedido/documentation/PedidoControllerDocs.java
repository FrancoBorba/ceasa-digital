package br.com.uesb.ceasadigital.api.features.pedido.documentation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import br.com.uesb.ceasadigital.api.features.pedido.dto.request.FinalizarCarrinhoRequestDTO;
import br.com.uesb.ceasadigital.api.features.pedido.dto.request.PedidoPutRequestDTO;
import br.com.uesb.ceasadigital.api.features.pedido.dto.response.PedidoPageResponseDto;
import br.com.uesb.ceasadigital.api.features.pedido.dto.response.PedidoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public interface PedidoControllerDocs {

  @Operation(
    summary = "Listar pedidos do usuário",
    description = """
        Retorna pedidos do usuário autenticado com paginação opcional baseada em tempo.
        
        **Parâmetros de Paginação (Opcionais):**
        - **page**: Número da página (padrão: 0)
        - **size**: Tamanho da página (padrão: 10)
        - **direction**: 'asc' (ascendente - do mais antigo para o mais novo) ou 'desc' (descendente - do mais novo para o mais antigo, padrão)
        - **sortBy**: Campo para ordenação - 'dataPedido', 'criadoEm' ou 'atualizadoEm' (padrão: 'dataPedido')
        
        **Exemplo:** Para obter pedidos mais recentes primeiro: ?direction=desc&sortBy=dataPedido
        **Sem parâmetros:** Retorna primeira página com 10 itens ordenados por data (mais recentes primeiro)
        """,
    tags = {"Pedidos"},
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200", 
        content = @Content(
          mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = PedidoPageResponseDto.class)
        )),
      @ApiResponse(description = "Requisição inválida", responseCode = "400", content = @Content),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<PedidoPageResponseDto> getAllPedidosByCurrentUser(
    @RequestParam(value = "page", required = false) Integer page,
    @RequestParam(value = "size", required = false) Integer size,
    @RequestParam(value = "direction", required = false) String direction,
    @RequestParam(value = "sortBy", required = false) String sortBy
  );

  @Operation(
    summary = "Listar todos os pedidos (Admin)",
    description = """
        Retorna todos os pedidos do sistema com paginação e ordenação (apenas para administradores).
        
        **Parâmetros de Ordenação:**
        - **page**: Número da página (padrão: 0)
        - **size**: Tamanho da página (padrão: 10)
        - **direction**: 'asc' (ascendente - do mais antigo para o mais novo) ou 'desc' (descendente - do mais novo para o mais antigo, padrão)
        - **sortBy**: Campo para ordenação - 'dataPedido', 'criadoEm' ou 'atualizadoEm' (padrão: 'dataPedido')
        
        **Exemplo:** Para obter pedidos mais recentes primeiro: ?direction=desc&sortBy=dataPedido
        """,
    tags = {"Pedidos"},
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200", 
        content = @Content(
          mediaType = MediaType.APPLICATION_JSON_VALUE,
          schema = @Schema(implementation = PedidoPageResponseDto.class)
        )),
      @ApiResponse(description = "Requisição inválida", responseCode = "400", content = @Content),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Acesso Negado", responseCode = "403", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<PedidoPageResponseDto> getAllPedidosPaginated(
    @RequestParam(value = "page", defaultValue = "0") Integer page,
    @RequestParam(value = "size", defaultValue = "10") Integer size,
    @RequestParam(value = "direction", defaultValue = "desc") String direction,
    @RequestParam(value = "sortBy", defaultValue = "dataPedido") String sortBy
  );

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

