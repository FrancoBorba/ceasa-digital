package br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.request;

public class CalendarioDTO {
    private int expiracao; // Duração em segundos

    public CalendarioDTO() {
    }

    public CalendarioDTO(int expiracao) {
        this.expiracao = expiracao;
    }

    public int getExpiracao() {
        return expiracao;
    }

    public void setExpiracao(int expiracao) {
        this.expiracao = expiracao;
    }
}
