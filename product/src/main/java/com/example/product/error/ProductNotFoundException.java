package com.example.product.error;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long productId) {
        super("El producto con ID " + productId + " no fue encontrado.");
    }
    
}
