package br.com.uesb.ceasadigital.api.features.endereco.documentation;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.uesb.ceasadigital.api.features.endereco.dto.EnderecoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Endereços", description = "Endpoints para gerenciamento de endereços do usuário")
public interface EnderecoControllerDocs {

  @Operation(
    summary = "Listar endereços do usuário",
    description = "Retorna todos os endereços cadastrados do usuário autenticado",
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200",
        content = @Content(schema = @Schema(implementation = EnderecoDTO.class))),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<List<EnderecoDTO>> listarMeusEnderecos();

  @Operation(
    summary = "Buscar endereço por ID",
    description = "Retorna os detalhes de um endereço específico do usuário",
    responses = {
      @ApiResponse(description = "Sucesso", responseCode = "200",
        content = @Content(schema = @Schema(implementation = EnderecoDTO.class))),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Endereço não encontrado", responseCode = "404", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<EnderecoDTO> buscarEnderecoPorId(Long id);

  @Operation(
    summary = "Criar novo endereço",
    description = "Cadastra um novo endereço para o usuário autenticado",
    responses = {
      @ApiResponse(description = "Endereço criado com sucesso", responseCode = "201",
        content = @Content(schema = @Schema(implementation = EnderecoDTO.class))),
      @ApiResponse(description = "Requisição inválida", responseCode = "400", content = @Content),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<EnderecoDTO> criarEndereco(EnderecoDTO enderecoDTO);

  @Operation(
    summary = "Atualizar endereço",
    description = "Atualiza os dados de um endereço existente do usuário",
    responses = {
      @ApiResponse(description = "Endereço atualizado com sucesso", responseCode = "200",
        content = @Content(schema = @Schema(implementation = EnderecoDTO.class))),
      @ApiResponse(description = "Requisição inválida", responseCode = "400", content = @Content),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Endereço não encontrado", responseCode = "404", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<EnderecoDTO> atualizarEndereco(Long id, EnderecoDTO enderecoDTO);

  @Operation(
    summary = "Deletar endereço",
    description = "Remove um endereço do usuário",
    responses = {
      @ApiResponse(description = "Endereço deletado com sucesso", responseCode = "204", content = @Content),
      @ApiResponse(description = "Não Autorizado", responseCode = "401", content = @Content),
      @ApiResponse(description = "Endereço não encontrado", responseCode = "404", content = @Content),
      @ApiResponse(description = "Erro Interno do Servidor", responseCode = "500", content = @Content)
    }
  )
  ResponseEntity<Void> deletarEndereco(Long id);
}

