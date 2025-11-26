package com.example.inventory.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductJsonApiResponse {

    private ProductData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductData {
        private String type;
        private Long id;
        private Attributes attributes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Attributes {
        private String name;
        private BigDecimal price;
    }
    
}
