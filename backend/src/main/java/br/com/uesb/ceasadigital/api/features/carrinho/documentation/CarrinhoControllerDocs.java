package br.com.uesb.ceasadigital.api.features.carrinho.documentation;

import org.springframework.http.ResponseEntity;

import br.com.uesb.ceasadigital.api.features.carrinho.dto.response.CarrinhoResponseDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.request.CarrinhoAddItemRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.request.CarrinhoUpdateItemRequestDTO;
import br.com.uesb.ceasadigital.api.features.item_carrinho.dto.response.CarrinhoItemResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Carrinho", description = "Endpoints para gerir o carrinho de compras do utilizador logado.")
public interface CarrinhoControllerDocs {

    @Operation(
        summary = "Busca o carrinho do utilizador",
        description = "Retorna o carrinho de compras ativo para o utilizador autenticado. Se não existir um, cria um novo e o retorna.",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Não Autorizado", responseCode = "401"),
            @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500")
        }
    )
    ResponseEntity<CarrinhoResponseDTO> findMyCarrinho();

    @Operation(
        summary = "Limpa todos os itens do carrinho",
        description = "Remove todos os itens do carrinho de compras do utilizador autenticado.",
        responses = {
            @ApiResponse(description = "Sem Conteúdo", responseCode = "204"), // Sucesso na exclusão
            @ApiResponse(description = "Não Autorizado", responseCode = "401"),
            @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500")
        }
    )
    ResponseEntity<Void> limparMeuCarrinho();

     @Operation(summary = "Adiciona um item ao carrinho de compras",
               description = "Adiciona um novo item ao carrinho do usuário logado ou atualiza a quantidade se o item já existir.")
    @ApiResponse(responseCode = "201", description = "Item adicionado ou atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: ID da oferta não existe, quantidade inválida)")
    public ResponseEntity<CarrinhoItemResponseDTO> adicionarItemAoCarrinho( CarrinhoAddItemRequestDTO itemRequest);
        
         // --- NOVO MÉTODO DE ATUALIZAÇÃO ---

    @Operation(summary = "Atualiza a quantidade de um item no carrinho",
               description = "Modifica a quantidade de um item específico que já está no carrinho do usuário.")
    @ApiResponse(responseCode = "200", description = "Quantidade do item atualizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: quantidade negativa)")
    @ApiResponse(responseCode = "404", description = "Item não encontrado no carrinho do usuário")
    public ResponseEntity<CarrinhoItemResponseDTO> atualizarItemNoCarrinho(
            Long idItem,
            CarrinhoUpdateItemRequestDTO itemRequest);
        
     
    

    // --- NOVO MÉTODO DE DELEÇÃO ---

    @Operation(summary = "Remove um item do carrinho")
    @ApiResponse(responseCode = "204", description = "Item removido com sucesso")
    @ApiResponse(responseCode = "404", description = "Item não encontrado no carrinho do usuário")
    public ResponseEntity<Void> removerItemDoCarrinho( Long idItem);
        
}