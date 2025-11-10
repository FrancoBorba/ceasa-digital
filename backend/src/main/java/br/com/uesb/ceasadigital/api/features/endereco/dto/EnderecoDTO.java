package br.com.uesb.ceasadigital.api.features.endereco.dto;

import br.com.uesb.ceasadigital.api.features.endereco.model.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EnderecoDTO {

  @Schema(description = "ID do endereço (somente leitura, gerado automaticamente)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  @NotBlank(message = "CEP é obrigatório")
  @Size(max = 9, message = "CEP deve ter no máximo 9 caracteres")
  @Schema(description = "CEP do endereço", example = "45000-000")
  @JsonProperty("cep")
  private String cep;

  @NotBlank(message = "Logradouro é obrigatório")
  @Size(max = 255, message = "Logradouro deve ter no máximo 255 caracteres")
  @Schema(description = "Nome da rua/avenida", example = "Rua das Flores")
  @JsonProperty("logradouro")
  private String logradouro;

  @NotBlank(message = "Número é obrigatório")
  @Size(max = 20, message = "Número deve ter no máximo 20 caracteres")
  @Schema(description = "Número do endereço", example = "123")
  @JsonProperty("numero")
  private String numero;

  @Size(max = 100, message = "Complemento deve ter no máximo 100 caracteres")
  @Schema(description = "Complemento do endereço", example = "Apto 101")
  @JsonProperty("complemento")
  private String complemento;

  @NotBlank(message = "Bairro é obrigatório")
  @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres")
  @Schema(description = "Nome do bairro", example = "Centro")
  @JsonProperty("bairro")
  private String bairro;

  @NotBlank(message = "Cidade é obrigatória")
  @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
  @Schema(description = "Nome da cidade", example = "Vitória da Conquista")
  @JsonProperty("cidade")
  private String cidade;

  @NotBlank(message = "Estado é obrigatório")
  @Size(min = 2, max = 2, message = "Estado deve ter exatamente 2 caracteres (sigla)")
  @Pattern(regexp = "^[A-Z]{2}$", message = "Estado deve ser uma sigla válida em maiúsculas (ex: BA, SP, RJ)")
  @Schema(description = "Sigla do estado (UF)", example = "BA", pattern = "^[A-Z]{2}$")
  @JsonProperty("estado")
  private String estado;

  public EnderecoDTO() {
  }

  public EnderecoDTO(Endereco endereco) {
    if (endereco != null) {
      this.id = endereco.getId();
      this.cep = endereco.getCep();
      this.logradouro = endereco.getLogradouro();
      this.numero = endereco.getNumero();
      this.complemento = endereco.getComplemento();
      this.bairro = endereco.getBairro();
      this.cidade = endereco.getCidade();
      this.estado = endereco.getEstado();
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public String getLogradouro() {
    return logradouro;
  }

  public void setLogradouro(String logradouro) {
    this.logradouro = logradouro;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public String getComplemento() {
    return complemento;
  }

  public void setComplemento(String complemento) {
    this.complemento = complemento;
  }

  public String getBairro() {
    return bairro;
  }

  public void setBairro(String bairro) {
    this.bairro = bairro;
  }

  public String getCidade() {
    return cidade;
  }

  public void setCidade(String cidade) {
    this.cidade = cidade;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }
}

