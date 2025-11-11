package br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.response;

import java.util.List;

import br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.request.DevedorDTO;
import br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.request.InfoAdicionaisDTO;
import br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.request.ValorDTO;


// DTO que o Sicoob envia para a gente, não precisamos de todos os dados então por enquanto
// vamos enviar para o nosso front end o DTO ResultadoCobrancaDTO
public class SicoobCobrancaPixResponseDTO {
    private CalendarioResponseDTO calendario; 
    private String txid;
    private int revisao;
    private LocDTO loc; 
    private String location; // Este é o payload do "Copia e Cola"
    private String status;
    private DevedorDTO devedor; 
    private ValorDTO valor; 
    private String chave;
    private String solicitacaoPagador;
    private List<InfoAdicionaisDTO> infoAdicionais; // <-- REAPROVEITADO


    public SicoobCobrancaPixResponseDTO() {
    }

    public CalendarioResponseDTO getCalendario() {
        return calendario;
    }
    public void setCalendario(CalendarioResponseDTO calendario) {
        this.calendario = calendario;
    }
    public String getTxid() {
        return txid;
    }
    public void setTxid(String txid) {
        this.txid = txid;
    }
    public int getRevisao() {
        return revisao;
    }
    public void setRevisao(int revisao) {
        this.revisao = revisao;
    }
    public LocDTO getLoc() {
        return loc;
    }
    public void setLoc(LocDTO loc) {
        this.loc = loc;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
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
