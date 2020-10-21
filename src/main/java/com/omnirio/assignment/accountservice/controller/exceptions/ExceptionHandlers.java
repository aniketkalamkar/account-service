package com.omnirio.assignment.accountservice.controller.exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandlers {

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<?> handleDataAccessException (DataAccessException daException){
		log.error(daException.getMessage());
		return ResponseEntity.badRequest().build();
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException (AccessDeniedException daException){
		log.error(daException.getMessage());
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException daException){
		log.error(daException.getMessage());
		return new ResponseEntity<>(daException.getStatusCode());
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<?> handleCustomerNotFoundException(CustomerNotFoundException daException){
		log.error(daException.getMessage());
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
