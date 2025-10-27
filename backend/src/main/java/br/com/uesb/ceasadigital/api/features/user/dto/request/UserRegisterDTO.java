package br.com.uesb.ceasadigital.api.features.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


// this DTO is used on input on registration
@Schema(description = "Data Transfer Object for new user registration request")
public class UserRegisterDTO {
    
    public UserRegisterDTO() {
    }

    @NotBlank(message = "O nome é obrigatório")
    @Schema(description = "Nome completo do usuário.", 
            example = "Maria Silva", 
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    @Schema(description = "Endereço de e-mail do usuário.",
            example = "mariasilva@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    
    @NotBlank(message = "A senha é obrigatória")
    @Schema(description = "Senha de acesso do usuário.",
            example = "senha123", 
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(
        description = "Número de telefone para contato do usuário (opcional).",
        example = "(77) 91234-5678",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String telefone;
    
    @NotBlank(message = "O cpf é obrigatório")
    @Schema(
        description = "CPF do usuário, contendo exatamente 11 dígitos, sem pontuação.",
        example = "12345678900",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String cpf;

    
    /* getters e setters */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }    
}
