package br.com.uesb.ceasadigital.api.features.pagamento.dto.pix.request;

import com.fasterxml.jackson.annotation.JsonInclude;

// Esta anotação diz ao Jackson para só incluir campos que não são nulos no JSON final.
// Se 'cpf' for nulo, ele não será enviado. Se 'cnpj' for nulo, ele não será enviado.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DevedorDTO {

    // Imagino que possa ser CPF , o exemplo no site deles é cnpj e a documentação não esclarece
    // Apis: https://developers.sicoob.com.br/portal/apis
    // Documentação: https://developers.sicoob.com.br/portal/documentacao?slugItem=apis&slugSubItem=pix
    private String cnpj; 
    private String cpf;

    private String nome;
    
    public DevedorDTO() {
    }
 

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
