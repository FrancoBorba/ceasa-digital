package br.com.uesb.ceasadigital.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CarrinhoNotFoundException.class)
public ResponseEntity<Map<String, Object>> handleCarrinhoNotFound(CarrinhoNotFoundException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("timestamp", LocalDateTime.now());
    error.put("status", HttpStatus.NOT_FOUND.value());
    error.put("error", "Carrinho não encontrado");
    error.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
}

@ExceptionHandler(ItemCarrinhoNotFoundException.class)
public ResponseEntity<Map<String, Object>> handleItemCarrinhoNotFound(ItemCarrinhoNotFoundException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("timestamp", LocalDateTime.now());
    error.put("status", HttpStatus.NOT_FOUND.value());
    error.put("error", "Item do carrinho não encontrado");
    error.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
}

@ExceptionHandler(InvalidCarrinhoOperationException.class)
public ResponseEntity<Map<String, Object>> handleInvalidCarrinhoOperation(InvalidCarrinhoOperationException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("timestamp", LocalDateTime.now());
    error.put("status", HttpStatus.BAD_REQUEST.value());
    error.put("error", "Operação inválida no carrinho");
    error.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
}

}