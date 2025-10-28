package br.com.uesb.ceasadigital.api.features.entregador.dto;

import br.com.uesb.ceasadigital.api.common.validator.annotations.CNH; // Import da sua validação CNH
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para criar ou atualizar o perfil de um entregador")
public class EntregadorRequestDTO {

    @NotBlank(message = "O número da CNH é obrigatório")
    @CNH // Usa a sua validação customizada
    @Schema(description = "Número da CNH (apenas números)", example = "12345678901", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cnh;

    @NotBlank(message = "O tipo de veículo é obrigatório")
    @Size(max = 30, message = "Tipo de veículo deve ter no máximo 30 caracteres")
    @Schema(description = "Tipo de veículo utilizado para entregas", example = "Moto", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tipoVeiculo;

    // Getters e Setters
    public String getCnh() { return cnh; }
    public void setCnh(String cnh) { this.cnh = cnh; }
    public String getTipoVeiculo() { return tipoVeiculo; }
    public void setTipoVeiculo(String tipoVeiculo) { this.tipoVeiculo = tipoVeiculo; }
}