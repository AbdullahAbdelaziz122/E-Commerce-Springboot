package com.abdullah.ShoppingCart.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import com.abdullah.ShoppingCart.domain.dtos.AuthResponse;
import com.abdullah.ShoppingCart.domain.dtos.LoginRequest;
import com.abdullah.ShoppingCart.domain.dtos.RegisterRequest;
import com.abdullah.ShoppingCart.domain.dtos.RegisterResponse;

public interface IAuthenticationService {
	UserDetails authenticate(String email, String password);
	String generateToken(UserDetails userDetails);
	UserDetails validateToken(String token);
	AuthResponse login(LoginRequest request);
	RegisterResponse register(RegisterRequest request);
}
