package br.com.uesb.ceasadigital.api.common.validator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.uesb.ceasadigital.api.common.validator.validators.CNHValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD}) 
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CNHValidator.class)
@Documented
public @interface CNH {
  
  String message () default "CNH Inválida";
  
   Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
