package br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.response;

public class CalendarioResponseDTO {
    
    private String criacao; // A data/hora em formato String ISO (ex: "2020-09-09T20:15:00.358Z")
    private Integer expiracao;

    public CalendarioResponseDTO() {
    }
    
    public String getCriacao() {
        return criacao;
    }
    public void setCriacao(String criacao) {
        this.criacao = criacao;
    }
    public Integer getExpiracao() {
        return expiracao;
    }
    public void setExpiracao(Integer expiracao) {
        this.expiracao = expiracao;
    } 
}
