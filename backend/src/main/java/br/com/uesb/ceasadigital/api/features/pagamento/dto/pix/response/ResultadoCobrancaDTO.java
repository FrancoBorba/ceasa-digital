package br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.response;

public class ResultadoCobrancaDTO {

    private boolean sucesso;
    private String txid; // O ID da transação PIX (para salvar no Pedido)
    private String pixCopiaECola; // A string do QR Code
    private String qrCodeImageUrl; // URL para buscar a IMAGEM do QR Code

    
    public ResultadoCobrancaDTO() {
    }
    public boolean isSucesso() {
        return sucesso;
    }
    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }
    public String getTxid() {
        return txid;
    }
    public void setTxid(String txid) {
        this.txid = txid;
    }
    public String getPixCopiaECola() {
        return pixCopiaECola;
    }
    public void setPixCopiaECola(String pixCopiaECola) {
        this.pixCopiaECola = pixCopiaECola;
    }
    public String getQrCodeImageUrl() {
        return qrCodeImageUrl;
    }
    public void setQrCodeImageUrl(String qrCodeImageUrl) {
        this.qrCodeImageUrl = qrCodeImageUrl;
    }
}
