package com.abdullah.ShoppingCart.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.abdullah.ShoppingCart.domain.dtos.RegisterRequest;
import com.abdullah.ShoppingCart.domain.models.User;
import com.abdullah.ShoppingCart.exceptions.UserNotFoundException;
import com.abdullah.ShoppingCart.repositories.UserRepository;
import com.abdullah.ShoppingCart.services.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService  implements IUserService{



    private final UserRepository repository;
    @Override
    public User getById(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return user;
    }

    public Boolean checkUser(UUID id){
        return repository.existsById(id);
    }
    
    public Boolean checkUser(String email){
        return repository.existsByEmail(email);
    }

    public User save(User newUser) {
        return repository.save(newUser);
    }

    
}
