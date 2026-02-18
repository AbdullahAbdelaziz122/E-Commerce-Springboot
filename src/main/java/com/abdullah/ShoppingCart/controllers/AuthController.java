package com.abdullah.ShoppingCart.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdullah.ShoppingCart.domain.dtos.AuthResponse;
import com.abdullah.ShoppingCart.domain.dtos.LoginRequest;
import com.abdullah.ShoppingCart.services.IAuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final IAuthenticationService authenticationService;
	
	@PostMapping
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
		UserDetails userDetails = authenticationService
				.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
		
		String tokenValue = authenticationService.generateToken(userDetails);
		
		AuthResponse response = AuthResponse.builder()
		.token(tokenValue)
		.expiresIn(86400)
		.build();
		
		return ResponseEntity.ok(response);
	}
}
