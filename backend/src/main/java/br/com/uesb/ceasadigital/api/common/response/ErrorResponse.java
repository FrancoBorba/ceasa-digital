package br.com.uesb.ceasadigital.api.common.response;

import java.time.Instant;

public class ErrorResponse {

  private Instant timestamp;
  private Integer status;
  private String error;
  private String path;
  private String details;

  public ErrorResponse(Instant timestamp, Integer status, String error, String path) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.path = path;
  }

  public ErrorResponse(Instant timestamp, Integer status, String error, String path, String details) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.path = path;
    this.details = details;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public Integer getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

  public String getPath() {
    return path;
  }
  
  public String getDetails() {
    return details;
  }
}
