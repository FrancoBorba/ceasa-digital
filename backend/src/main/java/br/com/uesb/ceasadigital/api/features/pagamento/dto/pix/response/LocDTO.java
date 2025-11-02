package br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.response;

public class LocDTO {
    
    private Long id; // Esse id chama outro endpoint do Sicoob para gerar o qrcode
    private String location; // A URL onde o payload do QR Code está

    /*
    Existem varios tipos de cobrança
    Cob: Nosso caso por enquanto sempre séra esse ( cobrança imediate)
    CobV: Compras com Vencimento
    LoteCobV : Cobranças com loteSicoobCobrancaPixResponseDTO
    */ 

    private String tipoCob; 

    public LocDTO() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getTipoCob() {
        return tipoCob;
    }
    public void setTipoCob(String tipoCob) {
        this.tipoCob = tipoCob;
    }
}
