package br.com.uesb.ceasadigital.api.features.entregador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Para controle de acesso
import org.springframework.web.bind.annotation.*; // Corrigido para importar tudo de web.bind.annotation
import br.com.uesb.ceasadigital.api.features.entregador.dto.EntregadorRequestDTO;
import br.com.uesb.ceasadigital.api.features.entregador.dto.EntregadorResponseDTO;
import br.com.uesb.ceasadigital.api.features.entregador.service.EntregadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/entregador/perfil") // Rota base para perfil do entregador
@Tag(name = "Perfil do Entregador", description = "Endpoints para gerenciar o perfil do entregador logado")
public class EntregadorController {

    @Autowired
    private EntregadorService entregadorService;

    @PostMapping // Usar POST para criar o perfil pela primeira vez
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ENTREGADOR')") // Permite USER criar, ou ENTREGADOR atualizar via POST se preferir
    @Operation(summary = "Criar ou atualizar perfil do entregador",
               description = "Cria um novo perfil de entregador para o usuário logado ou atualiza um existente.",
               responses = {
                   @ApiResponse(description = "Perfil criado/atualizado com sucesso", responseCode = "200"),
                   @ApiResponse(description = "Dados inválidos", responseCode = "400"),
                   @ApiResponse(description = "Não autorizado", responseCode = "401"),
                   @ApiResponse(description = "Usuário já tem outro perfil (ex: produtor)", responseCode = "409")
               })
    public ResponseEntity<EntregadorResponseDTO> criarOuAtualizarPerfil(@Valid @RequestBody EntregadorRequestDTO requestDTO) {
        EntregadorResponseDTO responseDTO = entregadorService.criarOuAtualizarPerfil(requestDTO);
        // Retorna 200 OK tanto para criação quanto para atualização neste endpoint único
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/me") // Rota para buscar o perfil do usuário logado
    @PreAuthorize("hasRole('ROLE_ENTREGADOR')") // Só quem já é entregador pode buscar
    @Operation(summary = "Buscar meu perfil de entregador",
               description = "Retorna os detalhes do perfil de entregador do usuário autenticado.",
               responses = {
                   @ApiResponse(description = "Perfil encontrado", responseCode = "200"),
                   @ApiResponse(description = "Não autorizado", responseCode = "401"),
                   @ApiResponse(description = "Perfil não encontrado para este usuário", responseCode = "404")
               })
    public ResponseEntity<EntregadorResponseDTO> buscarMeuPerfil() {
        EntregadorResponseDTO responseDTO = entregadorService.buscarMeuPerfil();
        return ResponseEntity.ok(responseDTO);
    }

     @PutMapping // Usar PUT para atualizar o perfil existente (alternativa ao POST)
     @PreAuthorize("hasRole('ROLE_ENTREGADOR')") // Só pode atualizar se já for entregador
     @Operation(summary = "Atualizar perfil do entregador",
                description = "Atualiza os dados (CNH, tipo de veículo) do perfil de entregador existente do usuário logado.",
                responses = {
                    @ApiResponse(description = "Perfil atualizado com sucesso", responseCode = "200"),
                    @ApiResponse(description = "Dados inválidos", responseCode = "400"),
                    @ApiResponse(description = "Não autorizado", responseCode = "401"),
                    @ApiResponse(description = "Perfil não encontrado para atualizar", responseCode = "404")
                })
     public ResponseEntity<EntregadorResponseDTO> atualizarPerfil(@Valid @RequestBody EntregadorRequestDTO requestDTO) {
         // Reutiliza o mesmo método do service, que lida com criação ou atualização
         EntregadorResponseDTO responseDTO = entregadorService.criarOuAtualizarPerfil(requestDTO);
         return ResponseEntity.ok(responseDTO);
     }
}