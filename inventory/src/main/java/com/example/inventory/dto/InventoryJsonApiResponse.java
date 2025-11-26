package com.example.inventory.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    name = "InventoryJsonApiResponse",
    description = "Representa una respuesta JSON API para un inventario"
)
public class InventoryJsonApiResponse {

    @Schema(
        description = "Datos del inventario"
    )
    private InventoryData data;
    @Schema(
        description = "Links relacionados con el inventario",
        example = "{\"timestamp\": 1627847265123}"
    )
    private Meta meta;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(
        name = "InventoryData",
        description = "Datos del inventario en formato JSON API"
    )
    public static class InventoryData{
        @Schema(
            description = "Tipo de recurso, debe ser 'inventory'"
        )
        private String type;
        @Schema(
            description = "ID del producto asociado al inventario",
            example = "1"
        )
        private String id;
        @Schema(
            description = "Atributos del inventario"
        )
        private Attributes attributes;
        @Schema(
            description = "Links relacionados con el inventario",
            example = "{\"self\": \"http://api.example.com/inventory/1\", \"related\": \"http://api.example.com/products/1\"}"
        )
        private Links links;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(
        name = "Attributes",
        description = "Atributos del inventario"
    )
    public static class Attributes{
        @Schema(
            description = "Cantidad de inventario disponible",
            example = "100"
        )
        private int quantity;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(
        name = "Links",
        description = "Links relacionados con el inventario"
    )
    public static class Links{
        @Schema(
            description = "Link al recurso de inventario",
            example = ".../inventory/1"
        )
        private String self;
        @Schema(
            description = "Link al recurso de producto asociado",
            example = ".../products/1"
        )
        private String related;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(
        name = "Meta",
        description = "Metadatos adicionales sobre la respuesta"
    )
    public static class Meta{
        @Schema(
            description = "Marca de tiempo de la respuesta en milisegundos",
            example = "1627847265123"
        )
        private Long timestamp;
    }
    
}
