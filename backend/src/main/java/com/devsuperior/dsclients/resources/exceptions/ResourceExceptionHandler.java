package com.devsuperior.dsclients.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dsclients.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		Integer status = HttpStatus.NOT_FOUND.value();
		StandardError error = new StandardError(
				Instant.now(),
				status,
				"Resource Not Found",
				e.getMessage(),
				request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
		
		
	}
}
