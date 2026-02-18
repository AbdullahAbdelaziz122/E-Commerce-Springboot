package com.abdullah.ShoppingCart.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.abdullah.ShoppingCart.domain.dtos.ApiError;
import com.abdullah.ShoppingCart.domain.dtos.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
		ApiResponse<Void> response = ApiResponse.<Void>builder()
				.success(false)
				.message("Authentication failed")
				.error(ApiError.builder()
						.code("INVALID_CREDENTIALS")
						.message("Incorrect email or password")
						.build())
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
		ApiResponse<Void> response = ApiResponse.<Void>builder()
				.success(false)
				.message(ex.getMessage())
				.error(ApiError.builder()
						.code("USER_ALREADY_EXISTS")
						.message(ex.getMessage())
						.build())
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleUserNotFoundException(UserNotFoundException ex) {
		ApiResponse<Void> response = ApiResponse.<Void>builder()
				.success(false)
				.message(ex.getMessage())
				.error(ApiError.builder()
						.code("USER_NOT_FOUND")
						.message(ex.getMessage())
						.build())
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		ApiResponse<Void> response = ApiResponse.<Void>builder()
				.success(false)
				.message("Validation failed")
				.error(ApiError.builder()
						.code("VALIDATION_FAILED")
						.message("Invalid input")
						.details(errors)
						.build())
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {

		log.warn("InterServerException:\n " + ex);

		ApiResponse<Void> response = ApiResponse.<Void>builder()
				.success(false)
				.message("An internal error occurred")
				.error(ApiError.builder()
						.code("INTERNAL_SERVER_ERROR")
						.message("Please try again later")
						.build())
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
