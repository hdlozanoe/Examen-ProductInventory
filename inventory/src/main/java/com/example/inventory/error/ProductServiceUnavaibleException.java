package com.example.inventory.error;

public class ProductServiceUnavaibleException extends RuntimeException {

    public ProductServiceUnavaibleException(String message) {
        super(message);
    }

    public ProductServiceUnavaibleException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
