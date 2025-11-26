package com.example.inventory.error;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long productId) {
        super("El producto con ID " + productId + " no fue encontrado en el servicio de producto.");
    }
    
}
