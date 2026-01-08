package com.infrastructure.controller;

import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.domain.exception.ExcepcionServicio;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  record ErrorResponse(String title, int status, String detail) {
  }

  @ExceptionHandler(ExcepcionServicio.class)
  public ResponseEntity<ErrorResponse> handleExcepcionServicio(ExcepcionServicio ex) {
    log.warn("Excepción de servicio: {}", ex.getReason());
    int status = ex.getStatusCode().value();
    ErrorResponse error = new ErrorResponse("Excepción de Servicio", status, ex.getReason());
    return ResponseEntity.status(status).body(error);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
    log.info("Recurso no encontrado: {}", ex.getMessage());
    ErrorResponse error = new ErrorResponse("Recurso no encontrado", 404, ex.getMessage());
    return ResponseEntity.status(404).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex) {
    String detalles = ex.getBindingResult().getFieldErrors().stream()
        .map(fe -> fe.getField() + ": " + fe.getDefaultMessage()).collect(Collectors.joining(", "));
    log.warn("Error de validación: {}", detalles);
    ErrorResponse error = new ErrorResponse("Error de validación", 400, detalles);
    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleJsonParseException(
      HttpMessageNotReadableException ex) {
    log.warn("JSON inválido recibido", ex);
    ErrorResponse error = new ErrorResponse("Formato JSON incorrecto", 400,
        "El cuerpo de la petición no es un JSON válido");
    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
    log.warn("Acceso denegado", ex);
    ErrorResponse error = new ErrorResponse("Acceso denegado", 403,
        "No tienes permisos para acceder a este recurso.");
    return ResponseEntity.status(403).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
    log.error("Error interno no controlado", ex);
    ErrorResponse error = new ErrorResponse("Error interno del servidor", 500, ex.getMessage());
    return ResponseEntity.status(500).body(error);
  }
}
