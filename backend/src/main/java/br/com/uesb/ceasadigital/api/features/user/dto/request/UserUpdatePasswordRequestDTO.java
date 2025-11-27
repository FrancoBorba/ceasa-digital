package br.com.uesb.ceasadigital.api.features.user.dto.request;

import br.com.uesb.ceasadigital.api.common.validator.annotations.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class UserUpdatePasswordRequestDTO {
  
  @NotBlank(message = "A senha antiga é obrigatória")
  @Schema(description = "Senha antiga do usuário", example = "senha123", required = true)
  private String oldPassword;
  
  @NotBlank(message = "A nova senha é obrigatória")
  @Password
  @Schema(description = "Nova senha do usuário. Deve atender aos critérios de segurança (mínimo 8 caracteres, uma maiúscula, uma minúscula, um número e um caractere especial).", example = "NovaSenha@123", required = true)
  private String newPassword;
  
  public String getOldPassword() {
    return oldPassword;
  }
  
  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }
  
  public String getNewPassword() {
    return newPassword;
  }
  
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
}