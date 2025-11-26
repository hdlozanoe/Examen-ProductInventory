package com.example.inventory.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
    name = "JsonApiError",
    description = "Representa un error en formato JSON API"
)
public class JsonApiError {

    @Schema(
        description = "Código de estado HTTP asociado al error",
        example = "404"
    )
    private String status;
    @Schema(
        description = "Título breve del error",
        example = "Inventario no encontrado"
    )
    private String title;
    @Schema(
        description = "Detalle adicional sobre el error",
        example = "No se encontró inventario para el ID de producto proporcionado"
    )
    private String detail;
    
}
