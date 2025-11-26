package com.example.inventory.error;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
    name = "JsonApiErrorResponse",
    description = "Representa una respuesta de error en formato JSON API"
)
public class JsonApiErrorResponse {
    
    @Schema(
        description = "Lista de errores",
        example = "[{ \"status\": \"404\", \"title\": \"Inventario no encontrado\", \"detail\": \"No se encontró inventario para el ID de producto proporcionado\" }]"
    )
    private List<JsonApiError> errors;
    
}
