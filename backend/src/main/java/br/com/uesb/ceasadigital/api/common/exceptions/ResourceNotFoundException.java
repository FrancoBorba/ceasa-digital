package br.com.uesb.ceasadigital.api.common.exceptions;

public class ResourceNotFoundException extends RuntimeException{

  public ResourceNotFoundException(String msg){
    super(msg);   
  }
  
}
