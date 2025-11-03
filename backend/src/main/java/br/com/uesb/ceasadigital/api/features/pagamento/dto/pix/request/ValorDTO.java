package br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.request;

public class ValorDTO {

    private String original; // API PIX usa String para valor (ex: "37.00")
    private Integer modalidadeAlteracao; // Opcional, 1 significa que pode mudar o valor , 0 que não pode 

    
    public ValorDTO() {
    }

    public String getOriginal() {
        return original;
    }
    public void setOriginal(String original) {
        this.original = original;
    }
    public Integer getModalidadeAlteracao() {
        return modalidadeAlteracao;
    }
    public void setModalidadeAlteracao(Integer modalidadeAlteracao) {
        this.modalidadeAlteracao = modalidadeAlteracao;
    }
}
