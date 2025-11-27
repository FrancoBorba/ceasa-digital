package br.com.uesb.ceasadigital.api.features.oferta_produtor.documentation;

import br.com.uesb.ceasadigital.api.features.oferta_produtor.dto.response.OfertaProdutorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Ofertas (Produtor)", description = "Endpoints para o Produtor gerenciar suas ofertas e metas de estoque.")
@SecurityRequirement(name = "bearerAuth")
public interface OfertaProdutorControllerDocs {
  
  @Operation(
  summary = "Lista as ofertas (metas) do produtor logado",
  description = "Retorna uma lista de todas as ofertas/metas de estoque atribuídas ao produtor autenticado.",
  parameters = {
    @Parameter(name = "status",
    description = "Filtra as ofertas por status (Ex: PENDENTE, ATIVO, ESGOTADO). Se não informado, retorna todos.",
    example = "PENDENTE")
  },
  responses = {
    @ApiResponse(responseCode = "200", description = "Lista de ofertas retornada com sucesso."),
    @ApiResponse(responseCode = "401", description = "Não autorizado."),
    @ApiResponse(responseCode = "403", description = "Acesso negado (usuário não é produtor).")
  }
  )
  ResponseEntity<List<OfertaProdutorResponseDTO>> getMinhasOfertas(
  @RequestParam(required = false) String status);
  
  
  @Operation(
  summary = "Confirma uma oferta/meta PENDENTE",
  description = "Permite ao produtor 'ACEITAR' uma meta de estoque que estava PENDENTE.",
  responses = {
    @ApiResponse(responseCode = "200", description = "Oferta confirmada com sucesso. Status mudou para ATIVO."),
    @ApiResponse(responseCode = "400", description = "Requisição inválida (Ex: oferta não está PENDENTE)."),
    @ApiResponse(responseCode = "401", description = "Não autorizado."),
    @ApiResponse(responseCode = "403", description = "Acesso negado (oferta não pertence a este produtor)."),
    @ApiResponse(responseCode = "404", description = "Oferta não encontrada.")
  }
  )
  ResponseEntity<OfertaProdutorResponseDTO> confirmarOferta(@PathVariable Long id);
}