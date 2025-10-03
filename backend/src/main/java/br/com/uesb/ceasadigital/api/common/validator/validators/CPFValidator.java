package br.com.uesb.ceasadigital.api.common.validator.validators;

import java.util.InputMismatchException;

import br.com.uesb.ceasadigital.api.common.validator.annotations.CPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPF , String> {

  @Override
  public boolean isValid(String cpf, ConstraintValidatorContext context) {
    
    
    if(cpf == null || cpf.isBlank() ){
      return true;
    }

    // remove pontos, hifens etc
    String cpfNumerico = cpf.replaceAll("[^\\d]", ""); 

    // Verifica se tem 11 numeros
     if (cpfNumerico.length() != 11) {
            return false;
        }

    // Verifica se todos os numeros são iguais
    if (cpfNumerico.matches("(\\d)\\1{10}")) {
            return false;
        }

      // Como funcionar a checagem
        try {

          // Pega os 9 primeiros digitos e vai mltiplicando por seus pesos(10 , 9 ... 2) soma tudo 
          // e subtrai 11 do resto da soma por 11 , esse valor tem que ser igual ao décimo digito
            char dig10 = calcularDigitoVerificador(cpfNumerico.substring(0, 9));

          // Semelhante ao processo anterios mas inclui o primeiro digito verificador
            char dig11 = calcularDigitoVerificador(cpfNumerico.substring(0, 9) + dig10);

            return (dig10 == cpfNumerico.charAt(9)) && (dig11 == cpfNumerico.charAt(10));

        } catch (InputMismatchException e) {
            return false;
        }

  }


  private char calcularDigitoVerificador(String base) {
        int soma = 0;
        int peso = base.length() + 1;

        for (int i = 0; i < base.length(); i++) {
            soma += (base.charAt(i) - '0') * peso;
            peso--;
        }

        int resto = 11 - (soma % 11);
        if ((resto == 10) || (resto == 11)) {
            return '0';
        } else {
            // Converte o número do dígito para seu caractere correspondente
            return (char) (resto + '0');
        }
    }
  
}
