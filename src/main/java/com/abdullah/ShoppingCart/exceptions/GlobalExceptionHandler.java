package com.abdullah.ShoppingCart.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.abdullah.ShoppingCart.domain.dtos.ApiErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiErrorResponse> handleBadCreditialException(BadCredentialsException ex) {
		log.warn("=== BadCredentialsException caught ===");
		ApiErrorResponse error = ApiErrorResponse.builder().status(HttpStatus.UNAUTHORIZED.value())
				.message("Incorrect Username or Password").timestamp(LocalDateTime.now()).build();

		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {
	    ApiErrorResponse error = ApiErrorResponse.builder()
	            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
	            .message("An internal error occurred") 
	            .timestamp(LocalDateTime.now())
	            .build();

	    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
