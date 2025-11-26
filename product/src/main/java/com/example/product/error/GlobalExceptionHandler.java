package com.example.product.error;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<JsonApiErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        JsonApiError error = new JsonApiError(
            "404",
            "Producto no encontrado",
            ex.getMessage()
        );
        return new ResponseEntity<>(
            new JsonApiErrorResponse(List.of(error)),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonApiErrorResponse> handleGenericException(Exception ex) {
        JsonApiError error = new JsonApiError(
            "500",
            "Error interno del servidor",
            ex.getMessage()
        );
        return new ResponseEntity<>(
            new JsonApiErrorResponse(List.of(error)),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }   

}
