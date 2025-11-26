package com.example.inventory.error;

public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException(Long productId) {
        super("No hay suficiente stock para el producto con ID " + productId + ".");
    }
    
}
