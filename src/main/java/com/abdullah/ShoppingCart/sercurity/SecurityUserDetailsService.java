package com.abdullah.ShoppingCart.sercurity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.abdullah.ShoppingCart.domain.models.User;
import com.abdullah.ShoppingCart.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService{
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
		return new SecurityUser(user);
	}

	private final UserRepository repository;
	
	
}
