package br.com.uesb.ceasadigital.api.features.auth.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequestDTO {

    @NotBlank(message = "Token é obrigatório") 
    private String token;

    @NotBlank(message = "Senha é obrigatória") 
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres") 
    private String password;

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
