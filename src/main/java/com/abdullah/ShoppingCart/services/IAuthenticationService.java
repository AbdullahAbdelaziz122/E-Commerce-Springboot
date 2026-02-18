package com.abdullah.ShoppingCart.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthenticationService {
	UserDetails authenticate(String email, String password);
	String generateToken(UserDetails userDetails);
}
