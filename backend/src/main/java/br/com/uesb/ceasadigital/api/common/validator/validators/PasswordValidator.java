package br.com.uesb.ceasadigital.api.common.validator.validators;

import br.com.uesb.ceasadigital.api.common.validator.annotations.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password , String> {

    private static final int MIN_LENGTH = 8;
    private static final String REGEX_UPPERCASE = ".*[A-Z].*";
    private static final String REGEX_LOWERCASE = ".*[a-z].*";
    private static final String REGEX_NUMBER = ".*[0-9].*";
    private static final String REGEX_SPECIAL_CHAR = ".*[!@#\\$%\\^&\\*].*";

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {

     if (password == null || password.isBlank()) {
          return true;
        }

         if (password.length() < MIN_LENGTH) {
            return setCustomMessage(context, "A senha deve ter no mínimo " + MIN_LENGTH + " caracteres.");
        }

        if (!Pattern.matches(REGEX_UPPERCASE, password)) {
            return setCustomMessage(context, "A senha deve conter ao menos uma letra maiúscula.");
        }

        if (!Pattern.matches(REGEX_LOWERCASE, password)) {
            return setCustomMessage(context, "A senha deve conter ao menos uma letra minúscula.");
        }

        if (!Pattern.matches(REGEX_NUMBER, password)) {
            return setCustomMessage(context, "A senha deve conter ao menos um número.");
        }

        if (!Pattern.matches(REGEX_SPECIAL_CHAR, password)) {
            return setCustomMessage(context, "A senha deve conter ao menos um caractere especial (ex: !@#$%).");
        }

     
        return true;
  }

  private boolean setCustomMessage(ConstraintValidatorContext context, String message) {
        //  Desabilita a mensagem de erro padrão da anotação
        context.disableDefaultConstraintViolation();
        //  Cria a nova mensagem para a annotation
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

        return false;
    }
  
}
