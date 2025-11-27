package br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.request;

import java.util.List;

public class SicoobCobrancaPixRequestDTO {
    private CalendarioDTO calendario;
    private DevedorDTO devedor;
    private ValorDTO valor;
    private String chave; // chave pix de quem vai receber
    private String solicitacaoPagador; // Descrição (ex: "Pedido 123 ") ou  "Serviço realizado."
    private List<InfoAdicionaisDTO> infoAdicionais;
    
    public SicoobCobrancaPixRequestDTO() {
    }

    public CalendarioDTO getCalendario() {
        return calendario;
    }
    public void setCalendario(CalendarioDTO calendario) {
        this.calendario = calendario;
    }
    public DevedorDTO getDevedor() {
        return devedor;
    }
    public void setDevedor(DevedorDTO devedor) {
        this.devedor = devedor;
    }
    public ValorDTO getValor() {
        return valor;
    }
    public void setValor(ValorDTO valor) {
        this.valor = valor;
    }
    public String getChave() {
        return chave;
    }
    public void setChave(String chave) {
        this.chave = chave;
    }
    public String getSolicitacaoPagador() {
        return solicitacaoPagador;
    }
    public void setSolicitacaoPagador(String solicitacaoPagador) {
        this.solicitacaoPagador = solicitacaoPagador;
    }
    public List<InfoAdicionaisDTO> getInfoAdicionais() {
        return infoAdicionais;
    }
    public void setInfoAdicionais(List<InfoAdicionaisDTO> infoAdicionais) {
        this.infoAdicionais = infoAdicionais;
    }
}
