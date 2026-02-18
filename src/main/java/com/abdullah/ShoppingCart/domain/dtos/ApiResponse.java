package com.abdullah.ShoppingCart.domain.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;




@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private ApiError error;
    private LocalDateTime timestamp;
}