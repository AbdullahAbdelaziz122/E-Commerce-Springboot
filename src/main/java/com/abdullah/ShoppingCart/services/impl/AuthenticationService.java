package com.abdullah.ShoppingCart.services.impl;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abdullah.ShoppingCart.domain.dtos.AuthResponse;
import com.abdullah.ShoppingCart.domain.dtos.LoginRequest;
import com.abdullah.ShoppingCart.domain.dtos.RegisterRequest;
import com.abdullah.ShoppingCart.domain.dtos.RegisterResponse;
import com.abdullah.ShoppingCart.domain.models.Role;
import com.abdullah.ShoppingCart.domain.models.User;
import com.abdullah.ShoppingCart.exceptions.UserAlreadyExistsException;
import com.abdullah.ShoppingCart.repositories.UserRepository;
import com.abdullah.ShoppingCart.services.IAuthenticationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService implements IAuthenticationService {

	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final UserService userService;

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private Long jwtExpiry;

	@Override
	public UserDetails authenticate(String email, String password) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(email, password));
		return userDetailsService.loadUserByUsername(email);
	}

	@Override
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiry))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();

	}

	@Override
	public UserDetails validateToken(String token) {
		String username = extractUsername(token);
		return userDetailsService.loadUserByUsername(username);

	}

	private String extractUsername(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}

	private Key getSigningKey() {
		byte[] keyBytes = secretKey.getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		UserDetails userDetails = authenticate(request.getEmail(), request.getPassword());

		String tokenValue = generateToken(userDetails);

		AuthResponse response = AuthResponse.builder()
				.token(tokenValue)
				.expiresIn(86400)
				.build();
		log.info("user login success: " + userDetails.getUsername());
		return response;
	}

	@Override
	public RegisterResponse register(RegisterRequest request) {

		if (userService.checkUser(request.getEmail()))
			throw new UserAlreadyExistsException(request.getEmail());
		User newUser = User.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.createdAt(LocalDateTime.now())
				.role(Role.USER).build();

		User savedUser = userService.save(newUser);
		return RegisterResponse.builder()
				.id(savedUser.getId())
				.email(savedUser.getEmail())
				.message("User registered successfully")
				.build();
	}

}
