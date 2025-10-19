package br.com.uesb.ceasadigital.api.common.validator.validators;

import java.util.InputMismatchException;

import br.com.uesb.ceasadigital.api.common.validator.annotations.CNPJ;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNPJValidator implements ConstraintValidator<CNPJ , String>{

  @Override
  public boolean isValid(String cnpj, ConstraintValidatorContext context) {
   
   if(cnpj == null || cnpj.isBlank()){
    return true;
   }

   String cnpjNumerico = cnpj.replaceAll("[^\\d]", "");

   if(cnpjNumerico.length() != 14){
    return false;
   }

   if (cnpjNumerico.matches("(\\d)\\1{13}")) {
    return false;
   }

     try {
        char dig13 = calcularDigitoVerificador(cnpjNumerico.substring(0, 12));
        char dig14 = calcularDigitoVerificador(cnpjNumerico.substring(0, 13));

        return (dig13 == cnpjNumerico.charAt(12)) && (dig14 == cnpjNumerico.charAt(13));

        } catch (InputMismatchException e) {
            return false;
        }
    
  }

  private char calcularDigitoVerificador(String base) {
        int soma = 0;
        int[] pesos;


        if (base.length() == 12) {
       
            pesos = new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        } else if (base.length() == 13) {
     
            pesos = new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        } else {
      
            return ' ';
        }

        for (int i = 0; i < base.length(); i++) {
            soma += (base.charAt(i) - '0') * pesos[i];
        }

        int resto = soma % 11;
        int digito = (resto < 2) ? 0 : (11 - resto);

     
        return (char) (digito + '0');
    }
}
