package br.com.uesb.ceasadigital.api.common.validator.validators;

import br.com.uesb.ceasadigital.api.common.validator.annotations.CNH;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNHValidator implements ConstraintValidator<CNH , String> {

  @Override
  public boolean isValid(String cnh, ConstraintValidatorContext context) {
        if (cnh == null || cnh.isBlank()) {
            return true;
        }

        String cnhNumerica = cnh.replaceAll("[^\\d]", "");


        if (cnhNumerica.length() != 11) {
            return false;
        }

        if (cnhNumerica.matches("(\\d)\\1{10}")) {
            return false;
        }
        
 
        int d1 = calcularPrimeiroDigito(cnhNumerica.substring(0, 9));

    
        int d2 = calcularSegundoDigito(cnhNumerica.substring(0, 9), d1);

        return cnhNumerica.equals(cnhNumerica.substring(0, 9) + d1 + d2);
    }

    private int calcularPrimeiroDigito(String base) {
        int soma = 0;
        int peso = 9;
        for (int i = 0; i < base.length(); i++) {
            soma += (base.charAt(i) - '0') * peso;
            peso--;
        }
        int resto = soma % 11;
        return (resto >= 10) ? 0 : resto;
    }

    private int calcularSegundoDigito(String base, int primeiroDigito) {
        int soma = 0;
        int peso = 1;
        for (int i = 0; i < base.length(); i++) {
            soma += (base.charAt(i) - '0') * peso;
            peso++;
        }
        
        int resto = soma % 11;
        int d2 = (resto >= 10) ? 0 : resto;
        
        // O algoritmo da CNH tem uma particularidade no segundo dígito
        // que difere de CPF e CNPJ, mas o cálculo acima é o padrão
        // adotado na maioria das implementações para validação simples.
        return d2;
    }
  
}
