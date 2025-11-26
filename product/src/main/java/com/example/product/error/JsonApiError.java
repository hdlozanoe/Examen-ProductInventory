package com.example.product.error;

public class JsonApiError {

    private String status;
    private String title;
    private String detail;

    public JsonApiError(String status, String title, String detail) {
        this.status = status;
        this.title = title;
        this.detail = detail;
    }

    public String getStatus() { return status; }
    public String getTitle() { return title; }
    public String getDetail() { return detail; }
    
}
