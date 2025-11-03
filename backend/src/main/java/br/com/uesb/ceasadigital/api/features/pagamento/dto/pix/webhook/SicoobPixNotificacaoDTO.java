package br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.webhook;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Ignora campos desconhecidos que o Sicoob possa enviar no futuro
@JsonIgnoreProperties(ignoreUnknown = true)
public class SicoobPixNotificacaoDTO {

    private String endToEndId; // ID global da transação PIX
    private String txid; // O ID da cobrança que nós criamos
    private String valor; // O valor que foi pago
    private String horario; // Data/Hora do pagamento (String ISO)
    

    private List<Object> devolucoes; // Não precisamos detalhar a devolução por agora

    // Getters e Setters
    public String getEndToEndId() {
        return endToEndId;
    }
    public void setEndToEndId(String endToEndId) {
        this.endToEndId = endToEndId;
    }

    public String getTxid() {
        return txid;
    }
    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }

    public List<Object> getDevolucoes() {
        return devolucoes;
    }
    public void setDevolucoes(List<Object> devolucoes) {
        this.devolucoes = devolucoes;
    }
}