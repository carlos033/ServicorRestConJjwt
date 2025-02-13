package com.infrastructure.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.domain.exception.ExcepcionServicio;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ExcepcionServicio.class)
	public ResponseEntity<Map<String, String>> handleExcepcionServicio(ExcepcionServicio ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("error", "Excepción de Servicio");
		errorResponse.put("detalle", ex.getReason());
		return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleEntityNotFound(EntityNotFoundException ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("error", "Recurso no encontrado");
		errorResponse.put("detalle", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("error", "Error de validación");
		String detalles = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).collect(Collectors.joining(", "));
		errorResponse.put("detalle", detalles);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, String>> handleJsonParseException(HttpMessageNotReadableException ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("error", "Formato JSON incorrecto");
		errorResponse.put("detalle", ex.getMostSpecificCause().getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("error", "Acceso denegado");
		errorResponse.put("detalle", "No tienes permisos para acceder a este recurso.");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("error", "Error interno del servidor");
		errorResponse.put("detalle", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
}
