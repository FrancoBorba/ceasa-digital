package br.com.uesb.ceasadigital.api.features.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.uesb.ceasadigital.api.common.response.ErrorResponse;
import br.com.uesb.ceasadigital.api.features.auth.dtos.ForgotPasswordRequestDTO;
import br.com.uesb.ceasadigital.api.features.auth.dtos.ResetPasswordRequestDTO;
import br.com.uesb.ceasadigital.api.features.auth.service.PasswordResetService;
import br.com.uesb.ceasadigital.api.features.user.dto.request.UserUpdatePasswordRequestDTO;
import br.com.uesb.ceasadigital.api.features.user.dto.request.UserUpdateRequestDTO;
import br.com.uesb.ceasadigital.api.features.user.dto.response.UserResponseDTO;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Endpoints para gerenciamento de Usuários e Senhas")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetService passwordResetService;

    @Value("${DATASOURCE_URL}")
    private String databaseUrl;

    @GetMapping("/public")
    @SecurityRequirements
    @Operation(summary = "Endpoint de teste público")
    public String debug() {
        return "Hello World Public";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Endpoint de teste para ADMIN")
    public String helloWorld() {
        return "Hello World Admin";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @Operation(summary = "Endpoint de teste para USER ou ADMIN")
    public String helloWorldUser() {
        return "Hello World User";
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_PRODUTOR', 'ROLE_ENTREGADOR')")
    @Operation(summary = "Retorna os dados do usuário autenticado")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser() {
        UserResponseDTO userDTO = userService.getAuthenticatedUser();
        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_PRODUTOR', 'ROLE_ENTREGADOR')")
    @Operation(summary = "Atualiza os dados (nome, email, cpf, tel) do usuário autenticado")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserUpdateRequestDTO userUpdateDTO) {
        UserResponseDTO updatedUser = userService.updateUser(userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/me/password")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN', 'ROLE_PRODUTOR', 'ROLE_ENTREGADOR')")
    @Operation(summary = "Atualiza a senha do usuário autenticado (requer senha antiga)")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserUpdatePasswordRequestDTO passwordRequestDTO) {
        userService.updatePassword(passwordRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot-password")
    @SecurityRequirements
    @Operation(summary = "Solicitar redefinição de senha", description = "Envia um link de redefinição para o e-mail do usuário, se o e-mail existir.", responses = {
            @ApiResponse(responseCode = "200", description = "Solicitação processada (e-mail enviado se o usuário existir)"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: e-mail mal formatado)", content = @Content),
    })
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO request) {
        try {
            passwordResetService.forgotPassword(request.getEmail());
        } catch (Exception e) {
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    @SecurityRequirements
    @Operation(summary = "Redefinir a senha usando o token", description = "Define uma nova senha para o usuário associado ao token, se o token for válido e não expirado.", responses = {
            @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: token faltando, senha curta)", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Token inválido ou expirado", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO request) {
        passwordResetService.resetPassword(request.getToken(), request.getPassword());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Listar usuários por filtro", description = "Busca usuários por role (ex: ROLE_PRODUTOR) e nome/email")
    public ResponseEntity<Page<UserResponseDTO>> listUsers(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<UserResponseDTO> users = userService.findAll(role, search, pageable);
        return ResponseEntity.ok(users);
    }
}