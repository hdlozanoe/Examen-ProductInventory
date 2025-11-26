package com.example.inventory.dto;

import java.math.BigDecimal;

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
    name = "ProductJsonApiResponse",
    description = "Representa una respuesta JSON API para un producto"
)
public class ProductJsonApiResponse {

    @Schema(
        description = "Datos del producto"
    )
    private ProductData data;
    @Schema(
        description = "Metadatos adicionales de la respuesta",
        example = "{\"timestamp\": 1625247600000}"
    )
    private Meta meta;
    @Schema(
        description = "Enlaces relacionados con el recurso",
        example = "{\"self\": \"/products/1\", \"related\": \"/products/1/inventory\"}"
    )
    private Links links;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(
        name = "ProductData",
        description = "Datos del producto en formato JSON API"
    )
    public static final class ProductData {
        private final String type = "products";
        @Schema(
            description = "ID del producto",
            example = "1"
        )
        private String id;
        @Schema(
            description = "Atributos del producto"
        )
        private Attributes attributes;
        @Schema(
            description = "Relaciones del producto con otros recursos"
        )
        private Relationships relationships;
        @Schema(
            description = "Enlaces relacionados con este recurso",
            example = "{\"self\": \"/products/1\", \"related\": \"/products/1/inventory\"}"
        )
        private Links links;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(
        name = "Attributes",
        description = "Atributos del producto"
    )
    public static class Attributes {
        @Schema(
            description = "Nombre del producto",
            example = "Jabon de lavanda"
        )
        private String name;
        @Schema(
            description = "Precio del producto",
            example = "19.99"
        )
        private BigDecimal price;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(
        name = "Relationships",
        description = "Relaciones del producto con otros recursos"
    )
    public static class Relationships {
        @Schema(
            description = "Relación con la categoría del producto"
        )
        private Reltionship category;
        @Schema(
            description = "Relación con la marca del producto"
        )
        private Reltionship brand;
        @Schema(
            description = "Relación con el inventario del producto"
        )
        private Reltionship inventory;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(
        name = "Reltionship",
        description = "Relación con otro recurso"
    )
    public static class Reltionship {
        @Schema(
            description = "Datos de la relación"
        )
        private RelationshipData data;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Schema(
            name = "RelationshipData",
            description = "Datos de la relación con otro recurso"
        )
        public static class RelationshipData {
            @Schema(
                description = "Tipo de recurso relacionado",
                example = "inventory"
            )
            private String type;
            @Schema(
                description = "ID del recurso relacionado",
                example = "2"
            )
            private String id;
        }

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(
        name = "Links",
        description = "Enlaces relacionados con el recurso"
    )
    public static final class Links {
        @Schema(
            description = "Enlace al recurso mismo",
            example = "/products/1"
        )
        private String self;
        @Schema(
            description = "Enlace relacionado con el recurso",
            example = "/products/1/inventory"
        )
        private String related;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(
        name = "Meta",
        description = "Metadatos adicionales de la respuesta"
    )
    public static final class Meta {
        @Schema(
            description = "Marca de tiempo de la respuesta en milisegundos desde epoch",
            example = "1625247600000"
        )
        private Long timestamp;
    }

    
}
