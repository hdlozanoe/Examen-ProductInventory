package com.example.product.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductJsonApiListResponse {

    private List<ProductJsonApiResponse.ProductData> data;
    private List<Object> included;
    private Links links;
    private Meta meta;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Links {
        private String first;
        private String last;
        private String prev;
        private String next;
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Meta {
        private Long totalItems;
        private Integer totalPages;
        private Integer currentPage;
        private Integer pageSize;
        private Long timestamp;
    }
    
}
