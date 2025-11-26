package com.example.product.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    name = "ProductJsonApiRequest",
    description = "Representa una solicitud JSON API para crear o actualizar un producto"
)
public class ProductJsonApiRequest {
    
    @NotNull(message = "La seccion data es requerida")
    @Valid
    @Schema(
        description = "Datos del producto"
    )
    private ProductData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(
        name = "ProductData",
        description = "Datos del producto en formato JSON API"
    )
    public static class ProductData{
        @NotNull(message = "El campo type es requerido")
        @Pattern(regexp = "products", message = "El campo type debe ser 'products'")
        @Schema(
            description = "Tipo de recurso, debe ser 'products'",
            example = "products"
        )
        private String type;
        @NotNull(message = "La seccion attributes es requerida")
        @Valid
        @Schema(
            description = "Atributos del producto"
        )
        private Attributes attributes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(
        name = "Attributes",
        description = "Atributos del producto"
    )
    public static class Attributes{
        @NotNull(message = "El nombre del producto es requerido")
        @Schema(
            description = "Nombre del producto",
            example = "Jabon de lavanda"
        )
        private String name;
        @NotNull(message = "El precio del producto es requerido")
        @Positive(message = "El precio del producto debe ser un valor positivo")
        @Schema(
            description = "Precio del producto",
            example = "19.99"
        )
        private BigDecimal price;
    }
    
}
