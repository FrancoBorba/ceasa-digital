package br.com.uesb.ceasadigital.api.common.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
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
    ErrorResponse err = new ErrorResponse(Instant.now(), status.value(), e.getMessage() , request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<ErrorResponse> Database(DatabaseException e, HttpServletRequest request) {

    HttpStatus status = HttpStatus.CONFLICT; // 409
    ErrorResponse err = new ErrorResponse(Instant.now(), status.value(), e.getMessage() , request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> genericError(Exception e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
    ErrorResponse err = new ErrorResponse(Instant.now(), status.value(), "Internal server error", request.getRequestURI(), e.getMessage());
    return ResponseEntity.status(status).body(err);
  }

  // OAuth2 errors (401)
  // Authentication errors (401)
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> authenticationException(AuthenticationException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.UNAUTHORIZED; // 401
    ErrorResponse err = new ErrorResponse(Instant.now(), status.value(), "Missing or invalid access token", request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

  // Access denied errors (403)
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> accessDeniedException(AccessDeniedException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.FORBIDDEN; // 403
    ErrorResponse err = new ErrorResponse(Instant.now(), status.value(), "Access denied: you don't have permission to access this resource", request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

  // JWT Bearer Token errors (401)
  @ExceptionHandler(InvalidBearerTokenException.class)
  public ResponseEntity<ErrorResponse> invalidBearerTokenException(InvalidBearerTokenException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.UNAUTHORIZED; // 401
    
    String customMessage = "Invalid or expired access token";
    
    // Check if the error is specifically about JWT expiration
    if (e.getMessage() != null) {
      if (e.getMessage().contains("Jwt expired")) {
        customMessage = "Access token has expired. Please refresh your token or login again";
      } else if (e.getMessage().contains("decode the Jwt")) {
        customMessage = "Invalid access token format";
      } else if (e.getMessage().contains("signature")) {
        customMessage = "Invalid access token signature";
      }
    }
    
    ErrorResponse err = new ErrorResponse(Instant.now(), status.value(), customMessage, request.getRequestURI());
    return ResponseEntity.status(status).body(err);
  }

  // Authorization Server errors (401)
  @ExceptionHandler(OAuth2AuthenticationException.class)
  public ResponseEntity<ErrorResponse> oauth2AuthenticationException(OAuth2AuthenticationException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.UNAUTHORIZED; // 401
    
    OAuth2Error error = e.getError();
    String customMessage = "OAuth2 authentication error";
    String customPath = "/oauth2/token";

    switch (error.getErrorCode()) {
      case "invalid_client":
        customMessage = "Invalid Client ID or Client Secret";
        break;
      case "invalid_grant":
        // Distinguish between password and refresh token errors
        if (request.getParameter("grant_type") != null && 
            request.getParameter("grant_type").equals("refresh_token")) {
          customMessage = "Invalid or expired refresh token";
        } else {
          customMessage = "Invalid username or password";
        }
        break;
      case "Invalid credentials":
        customMessage = "Invalid username or password";
        break;
      case "unsupported_grant_type":
        customMessage = "Unsupported grant type. Use 'password' or 'refresh_token'";
        break;
      case "invalid_request":
        customMessage = "Invalid OAuth2 request parameters";
        break;
      default:
        if (error.getDescription() != null) {
          customMessage = "OAuth2 error: " + error.getDescription();
        }
    }
    
    ErrorResponse err = new ErrorResponse(Instant.now(), status.value(), customMessage, customPath);
    return ResponseEntity.status(status).body(err);
  }

  // Specific handler for refresh token errors - provides more detailed error information
  public ResponseEntity<ErrorResponse> handleRefreshTokenError(String errorCode, String errorDescription, HttpServletRequest request) {
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    String customMessage;
    
    switch (errorCode) {
      case "invalid_grant":
        customMessage = "Refresh token is invalid, expired, or has been revoked";
        break;
      case "invalid_request":
        if (errorDescription != null && errorDescription.contains("refresh_token")) {
          customMessage = "Refresh token parameter is missing or malformed";
        } else {
          customMessage = "Invalid refresh token request";
        }
        break;
      case "unsupported_grant_type":
        customMessage = "Refresh token grant type is not supported";
        break;
      default:
        customMessage = "Refresh token authentication failed: " + (errorDescription != null ? errorDescription : "Unknown error");
    }
    
    ErrorResponse err = new ErrorResponse(Instant.now(), status.value(), customMessage, "/oauth2/token");
    return ResponseEntity.status(status).body(err);
  }
  
}