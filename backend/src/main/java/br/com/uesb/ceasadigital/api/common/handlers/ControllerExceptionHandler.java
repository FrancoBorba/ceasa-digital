package br.com.uesb.ceasadigital.api.common.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.uesb.ceasadigital.api.common.exceptions.DatabaseException;
import br.com.uesb.ceasadigital.api.common.exceptions.ResourceNotFoundException;
import br.com.uesb.ceasadigital.api.common.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
  
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> ResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {

    HttpStatus status = HttpStatus.NOT_FOUND; // 404
    ErrorResponse err = new ErrorResponse(Instant.now(), status.value(), e.getMessage() , request.getRequestURI()); // Criando o Custom Error
    return ResponseEntity.status(status).body(err); // Retornando o Erro
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<ErrorResponse> Database(DatabaseException e, HttpServletRequest request) {

    HttpStatus status = HttpStatus.CONFLICT; // 409 , representa erro de integridade referencial
    ErrorResponse err = new ErrorResponse(Instant.now(), status.value(), e.getMessage() , request.getRequestURI()); // Criando o Custom Error
    return ResponseEntity.status(status).body(err); // Retornando o Erro
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> genericError(Exception e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
    ErrorResponse err = new ErrorResponse(Instant.now(), status.value(), "Erro interno do servidor", request.getRequestURI(), e.getMessage());
    return ResponseEntity.status(status).body(err);
  }
  
  /*
  @ExceptionHandler(MappingException.class)
  public ResponseEntity<ErrorResponse> modelMapperError(MappingException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY; // 422
    ErrorResponse err = new ErrorResponse(Instant.now(), status.value(), "Erro de mapeamento", request.getRequestURI(), e.getMessage());
    return ResponseEntity.status(status).body(err);
  }
  */
}