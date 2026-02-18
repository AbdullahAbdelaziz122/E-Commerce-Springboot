package com.abdullah.ShoppingCart.domain.dtos;



import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class ApiError {
    private String code;          
    private String message;        
    private Map<String, String> details; 
}
