package br.com.uesb.ceasadigital.api.features.produtor.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdutorRequestDTO {
    @NotNull(message = "O id do usuário é obrigatório")
    @Schema(description = "id do usuario ligado ao produtor ", example = "12")
    private Long idUser;


    @NotBlank(message = "O número de identificação é obrigatório")
    @Schema(description = "CNPJ ou CPF do produtor", example = "12.345.678/0001-99")
    private String numeroDeIdentificacao;

    @NotBlank(message = "O tipo de identificação é obrigatório")
    @Schema(description = "Tipo de identificação (CNPJ ou CPF)", example = "CNPJ")
    private String tipoDeIdentificacao;

    /* getters e setters */
    
    public String getNumeroDeIdentificacao() {
        return numeroDeIdentificacao;
    }

    public void setNumeroDeIdentificacao(String numeroDeIdentificacao) {
        this.numeroDeIdentificacao = numeroDeIdentificacao;
    }

    public String getTipoDeIdentificacao() {
        return tipoDeIdentificacao;
    }

    public void setTipoDeIdentificacao(String tipoDeIdentificacao) {
        this.tipoDeIdentificacao = tipoDeIdentificacao;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
    
}
