package com.example.product.error;

import java.util.List;

public class JsonApiErrorResponse {

    private List<JsonApiError> errors;

    public JsonApiErrorResponse(List<JsonApiError> errors) {
        this.errors = errors;
    }

    public List<JsonApiError> getErrors() { return errors; }
    
}
