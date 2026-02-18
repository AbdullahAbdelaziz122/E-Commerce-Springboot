package com.abdullah.ShoppingCart.services;

import java.util.UUID;

import com.abdullah.ShoppingCart.domain.dtos.RegisterRequest;
import com.abdullah.ShoppingCart.domain.models.User;

public interface IUserService {
    User getById(UUID id);
}
