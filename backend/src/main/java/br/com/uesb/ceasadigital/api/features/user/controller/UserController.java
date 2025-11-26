package br.com.uesb.ceasadigital.api.features.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.uesb.ceasadigital.api.features.auth.dtos.ForgotPasswordRequestDTO;
import br.com.uesb.ceasadigital.api.features.auth.dtos.ResetPasswordRequestDTO;
import br.com.uesb.ceasadigital.api.features.auth.service.PasswordResetService;
import br.com.uesb.ceasadigital.api.features.user.dto.request.UserUpdatePasswordRequestDTO;
import br.com.uesb.ceasadigital.api.features.user.dto.request.UserUpdateRequestDTO;
import br.com.uesb.ceasadigital.api.features.user.dto.response.UserResponseDTO;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;
import br.com.uesb.ceasadigital.api.common.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

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
  @Operation(summary = "Solicitar redefinição de senha")
  public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO request) {
    try {
      passwordResetService.forgotPassword(request.getEmail());
    } catch (Exception e) {
    }
    return ResponseEntity.ok().build();
  }

  @PostMapping("/reset-password")
  @SecurityRequirements
  @Operation(summary = "Redefinir a senha usando o token")
  public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO request) {
    passwordResetService.resetPassword(request.getToken(), request.getPassword());
    return ResponseEntity.ok().build();
  }
  
  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<Page<UserResponseDTO>> listUsers(
          @RequestParam(required = false) String role,
          @RequestParam(required = false) String search,
          @PageableDefault(size = 10) Pageable pageable
  ) {
      Page<UserResponseDTO> users = userService.findAll(role, search, pageable);
      return ResponseEntity.ok(users);
  }
}