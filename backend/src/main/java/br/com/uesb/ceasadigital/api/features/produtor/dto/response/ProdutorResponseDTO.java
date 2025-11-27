package br.com.uesb.ceasadigital.api.features.produtor.dto.response;

import java.time.LocalDateTime;

import br.com.uesb.ceasadigital.api.features.user.dto.response.UserMinDTO;

public class ProdutorResponseDTO {
    private Long id;
    private UserMinDTO usuario;
    private String numeroDeIdentificacao;
    private String tipoDeIdentificacao;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public UserMinDTO getUsuario() {
        return usuario;
    }
    public void setUsuario(UserMinDTO usuario) {
        this.usuario = usuario;
    }
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
    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    
}
