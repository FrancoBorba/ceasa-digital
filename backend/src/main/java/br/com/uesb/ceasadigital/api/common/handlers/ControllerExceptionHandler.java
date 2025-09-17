package br.com.uesb.ceasadigital.api.common.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

  // Captura erros de usuário não encontrado (401) - Não revela se o usuário existe
  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ErrorResponse> usernameNotFoundException(UsernameNotFoundException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.UNAUTHORIZED; // 401
    ErrorResponse err = new ErrorResponse(Instant.now(), status.value(), "Credenciais inválidas", request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

  // Captura erros de acesso negado - usuário autenticado mas sem permissão (403)
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> accessDeniedException(AccessDeniedException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.FORBIDDEN; // 403
    ErrorResponse err = new ErrorResponse(Instant.now(), status.value(), "Acesso negado: você não tem permissão para acessar este recurso", request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }
  
  
}