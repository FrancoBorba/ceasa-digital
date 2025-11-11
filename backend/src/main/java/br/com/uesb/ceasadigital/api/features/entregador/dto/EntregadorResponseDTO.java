package br.com.uesb.ceasadigital.api.features.entregador.dto;

import java.time.LocalDateTime;
import br.com.uesb.ceasadigital.api.features.user.dto.response.UserMinDTO; // Reutiliza DTO mínimo do usuário
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta para o perfil de um entregador")
public class EntregadorResponseDTO {

    @Schema(description = "ID do perfil do entregador", example = "1")
    private Long id;

    @Schema(description = "Informações básicas do usuário associado")
    private UserMinDTO usuario; // Inclui informações do usuário

    @Schema(description = "CNH do entregador", example = "12345678901")
    private String cnh;

    @Schema(description = "Tipo de veículo", example = "Moto")
    private String tipoVeiculo;

    @Schema(description = "Data de criação do perfil")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime criadoEm;

    @Schema(description = "Data da última atualização do perfil")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime atualizadoEm;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public UserMinDTO getUsuario() { return usuario; }
    public void setUsuario(UserMinDTO usuario) { this.usuario = usuario; }
    public String getCnh() { return cnh; }
    public void setCnh(String cnh) { this.cnh = cnh; }
    public String getTipoVeiculo() { return tipoVeiculo; }
    public void setTipoVeiculo(String tipoVeiculo) { this.tipoVeiculo = tipoVeiculo; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}