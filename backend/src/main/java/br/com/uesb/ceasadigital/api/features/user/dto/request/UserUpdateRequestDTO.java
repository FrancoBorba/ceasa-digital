package br.com.uesb.ceasadigital.api.features.user.dto.request;

import br.com.uesb.ceasadigital.api.common.validator.annotations.CPF;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserUpdateRequestDTO {
  
  @Size(min = 2, max = 255, message = "O nome deve ter entre 2 e 255 caracteres")
  @Schema(description = "Novo nome do usuário", example = "Maria da Silva", required = true)
  private String name;
  
  @Email(message = "O email fornecido é inválido")
  @Schema(description = "Novo email do usuário. Deve ser um email válido e único.", example = "maria.silva@gmail.com", required = true)
  private String email;
  
  @Size(max = 20, message = "O telefone não pode exceder 20 caracteres")
  @Schema(description = "Novo número de telefone do usuário", example = "(77) 91234-5678")
  private String telefone;
  
  @CPF
  @Schema(description = "Novo CPF do usuário. Deve ser um CPF válido e único.", example = "123.456.789-00", required = true)
  private String cpf;
  
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getTelefone() {
    return telefone;
  }
  
  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getCpf() {
    return cpf;
  }
  
  public void setCpf(String cpf) {
    this.cpf = cpf;
  }
}