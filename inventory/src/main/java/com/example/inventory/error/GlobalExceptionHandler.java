package com.example.inventory.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<JsonApiErrorResponse> buildErrorResponse(
        String status, String title, String detail, HttpStatus httpStatus) {
        JsonApiError error = new JsonApiError(status, title, detail);
        JsonApiErrorResponse errorResponse = new JsonApiErrorResponse(java.util.List.of(error));
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<JsonApiErrorResponse> handleInventoryNotFoundException(InventoryNotFoundException ex) {
        return buildErrorResponse(
            "404",
            "Inventario no encontrado",
            ex.getMessage(),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<JsonApiErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        return buildErrorResponse(
            "404",
            "Producto no encontrado",
            ex.getMessage(),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<JsonApiErrorResponse> handleNotEnoughStockException(NotEnoughStockException ex) {
        return buildErrorResponse(
            "400",
            "No hay suficiente stock",
            ex.getMessage(),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ProductServiceUnavaibleException.class)
    public ResponseEntity<JsonApiErrorResponse> handleProductServiceUnavaible(ProductServiceUnavaibleException ex) {
        return buildErrorResponse(
            "503",
            "Servicio de productos no disponible",
            ex.getMessage(),
            HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonApiErrorResponse> handleGenericException(Exception ex) {
        return buildErrorResponse(
            "500",
            "Error interno del servidor",
            ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    
}
