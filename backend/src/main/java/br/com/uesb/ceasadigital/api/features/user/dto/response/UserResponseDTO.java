package br.com.uesb.ceasadigital.api.features.user.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.uesb.ceasadigital.api.features.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

public class UserResponseDTO {
  
  @Schema(description = "ID único do usuário", example = "1")
  private Long id;
  
  @Schema(description = "Nome do usuário", example = "Maria da Silva")
  private String name;
  
  @Schema(description = "Email do usuário", example = "maria@gmail.com")
  private String email;
  
  @Schema(description = "CPF do usuário", example = "123.456.789-00")
  private String cpf;
  
  @Schema(description = "Telefone do usuário", example = "77912345678")
  private String telefone;
  
  @Schema(description = "Data e hora da última atualização do usuário")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime atualizadoEm;
  
  public UserResponseDTO(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.cpf = user.getCpf();
    this.telefone = user.getTelefone();
    this.atualizadoEm = user.getAtualizadoEm();
  }
  
  // Getters e Setters
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
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
  
  public String getTelefone() {
    return telefone;
  }
  
  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }
  
  public LocalDateTime getAtualizadoEm() {
    return atualizadoEm;
  }
  
  public void setAtualizadoEm(LocalDateTime atualizadoEm) {
    this.atualizadoEm = atualizadoEm;
  }
  
}