package br.com.uesb.ceasadigital.api.common.response;

public class FieldMessage {
  
  private String fieldName;
  private String message;

  public FieldMessage(String fieldName, String message) {
    this.fieldName = fieldName;
    this.message = message;
  }
  
  public String getFieldName() {
    return fieldName;
  }

  public String getMessage() {
    return message;
  }
  
}
