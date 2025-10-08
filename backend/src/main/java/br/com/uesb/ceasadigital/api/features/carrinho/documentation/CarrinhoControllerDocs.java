package br.com.uesb.ceasadigital.api.features.carrinho.documentation;

import org.springframework.http.ResponseEntity;

import br.com.uesb.ceasadigital.api.features.carrinho.dto.response.CarrinhoResponseDTO;
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
}