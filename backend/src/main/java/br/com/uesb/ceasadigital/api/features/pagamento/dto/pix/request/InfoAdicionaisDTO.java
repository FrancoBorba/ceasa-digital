package br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.request;

public class InfoAdicionaisDTO {
    
    private String nome;
    private String valor;
    
    public InfoAdicionaisDTO() {
    }
    public InfoAdicionaisDTO(String nome, String valor) {
        this.nome = nome;
        this.valor = valor;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
}
