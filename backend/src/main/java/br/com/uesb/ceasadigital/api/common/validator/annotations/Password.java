package br.com.uesb.ceasadigital.api.common.validator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.uesb.ceasadigital.api.common.validator.validators.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD}) 
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {
  
  String message() default "A senha está inválida";

      Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
