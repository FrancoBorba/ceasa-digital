package br.com.uesb.ceasadigital.api.common.response;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends ErrorResponse{

  private List<FieldMessage> fieldMessages = new ArrayList<>();

  public ValidationError(Instant timestamp, Integer status, String error, String path) {
    super(timestamp, status, error, path);
  }

  public List<FieldMessage> getFieldMessages() {
    return fieldMessages;
  }

  public void addValidationError(String fieldName, String message) {
    fieldMessages.add(new FieldMessage(fieldName, message));
  }
}
