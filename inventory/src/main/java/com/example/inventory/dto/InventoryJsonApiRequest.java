package com.example.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    name = "InventoryJsonApiRequest",
    description = "Representa una solicitud JSON API para crear o actualizar un inventario"
)
public class InventoryJsonApiRequest {

    @NotNull
    @Valid
    @Schema(
        description = "Datos del inventario"
    )
    private InventoryData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(
        name = "InventoryData",
        description = "Datos del inventario en formato JSON API"
    )
    public static class InventoryData {

        @NotNull(message = "El campo type es requerido")
        @Pattern(regexp = "inventory", message = "El campo type debe ser 'inventory'")
        @Schema(
            description = "Tipo de recurso, debe ser 'inventory'",
            example = "inventory"
        )
        private String type;

        @NotNull(message = "El campo id es requerido")
        @Schema(
            description = "ID del producto asociado al inventario",
            example = "1"
        )
        private String id;

        @NotNull(message = "La seccion attributes es requerida")
        @Valid
        @Schema(
            description = "Atributos del inventario"
        )
        private Attributes attributes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(
        name = "Attributes",
        description = "Atributos del inventario"
    )
    public static class Attributes {
        @Min(value = 0, message = "La cantidad no puede ser negativa")
        @Schema(
            description = "Cantidad de inventario disponible",
            example = "100",
            minimum = "0"
        )
        private int quantity;
    }
    
}
