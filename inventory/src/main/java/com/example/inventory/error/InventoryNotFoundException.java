package com.example.inventory.error;

public class InventoryNotFoundException  extends RuntimeException {

    public InventoryNotFoundException(Long productId) {
        super("El inventario con ID de producto " + productId + " no fue encontrado.");
    }

}
