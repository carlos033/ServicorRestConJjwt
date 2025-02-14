package com.infrastructure.controller;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import com.domain.exception.ExcepcionServicio;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ExcepcionServicio.class)
	public ResponseEntity<Problem> handleExcepcionServicio(ExcepcionServicio ex) {
		Problem problem = Problem.builder().withTitle("Excepción de Servicio").withStatus(Status.BAD_REQUEST).withDetail(ex.getReason()).build();
		return ResponseEntity.status(ex.getStatusCode()).body(problem);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Problem> handleEntityNotFound(EntityNotFoundException ex) {
		Problem problem = Problem.builder().withTitle("Recurso no encontrado").withStatus(Status.NOT_FOUND).withDetail(ex.getMessage()).build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Problem> handleValidationException(MethodArgumentNotValidException ex) {
		String detalles = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).collect(Collectors.joining(", "));

		Problem problem = Problem.builder().withTitle("Error de validación").withStatus(Status.BAD_REQUEST).withDetail(detalles).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Problem> handleJsonParseException(HttpMessageNotReadableException ex) {
		Problem problem = Problem.builder().withTitle("Formato JSON incorrecto").withStatus(Status.BAD_REQUEST).withDetail(ex.getMostSpecificCause().getMessage()).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Problem> handleAccessDeniedException(AccessDeniedException ex) {
		Problem problem = Problem.builder().withTitle("Acceso denegado").withStatus(Status.FORBIDDEN).withDetail("No tienes permisos para acceder a este recurso.").build();
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problem);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Problem> handleGeneralException(Exception ex) {
		Problem problem = Problem.builder().withTitle("Error interno del servidor").withStatus(Status.INTERNAL_SERVER_ERROR).withDetail(ex.getMessage()).build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
	}
}
