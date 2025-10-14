package br.com.uesb.ceasadigital.api.common.validator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.uesb.ceasadigital.api.common.validator.validators.CPFValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CPFValidator.class) // Classe que possui a regra de négocio
@Documented
public @interface CPF {
  

  String message() default "CPF inválido";

    //groups permite que você crie "subconjuntos" de regras de validação e as ative apenas quando necessário
    // Exemplo verificar se o nome é NOTNULL apenas quando criar
    Class<?>[] groups() default {};

    // payload é uma forma avançada de "etiquetar" suas regras de validação com informações extras
    Class<? extends Payload>[] payload() default {};

}
