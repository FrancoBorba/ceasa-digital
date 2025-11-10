package br.com.uesb.ceasadigital.api.features.auth.controller;

import br.com.uesb.ceasadigital.api.features.auth.dtos.ForgotPasswordRequestDTO;
import br.com.uesb.ceasadigital.api.features.auth.dtos.ResetPasswordRequestDTO;
import br.com.uesb.ceasadigital.api.features.auth.service.PasswordResetService;
import br.com.uesb.ceasadigital.api.common.response.ErrorResponse; 

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirements; // Import para desabilitar segurança

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth") // Define a rota base para este controller
@Tag(name = "Autenticação e Redefinição de Senha", description = "Endpoints para solicitar e redefinir senha") // Tag Swagger
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    @SecurityRequirements // Indica que este endpoint NÃO requer autenticação Bearer
    @Operation(
        summary = "Solicitar redefinição de senha",
        description = "Envia um link de redefinição para o e-mail do usuário, se o e-mail existir.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Solicitação processada (e-mail enviado se o usuário existir)"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: e-mail mal formatado)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado (a resposta será 200 para não vazar informação)", content = @Content) // Nota de segurança
        }
    )
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO request) {
        try {
            passwordResetService.forgotPassword(request.getEmail());
        } catch (Exception e) {
            // Mesmo se o usuário não for encontrado, retornamos OK para não indicar se um e-mail está cadastrado ou não.
            // Logar o erro internamente se necessário.
            // logger.error("Erro ao processar forgot-password para {}: {}", request.getEmail(), e.getMessage());
        }
        // Sempre retorna OK para o cliente
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    @SecurityRequirements // Indica que este endpoint NÃO requer autenticação Bearer
    @Operation(
        summary = "Redefinir a senha usando o token",
        description = "Define uma nova senha para o usuário associado ao token, se o token for válido e não expirado.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: token faltando, senha curta)", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Token inválido ou expirado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        }
    )
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO request) {
        // O PasswordResetService lançará exceções se o token for inválido/expirado ou a senha for inválida,
        // que serão tratadas pelo ControllerExceptionHandler global.
        passwordResetService.resetPassword(request.getToken(), request.getPassword());
        return ResponseEntity.ok().build();
    }
}

