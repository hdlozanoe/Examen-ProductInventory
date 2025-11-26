package com.example.product.error;

public class ProductServiceUnavailableException extends RuntimeException {
    public ProductServiceUnavailableException() {
        super("El servicio de productos no está disponible en este momento.");
    }
}
