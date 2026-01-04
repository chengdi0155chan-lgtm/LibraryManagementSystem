package com.library;

/**
 * API响应构建器，用于链式构建响应
 */
public class ApiResponseBuilder {
    
    private boolean success;
    private String message;
    private Object data;
    private Integer code;
    
    private ApiResponseBuilder() {}
    
    public static ApiResponseBuilder create() {
        return new ApiResponseBuilder();
    }
    
    public ApiResponseBuilder success(boolean success) {
        this.success = success;
        return this;
    }
    
    public ApiResponseBuilder message(String message) {
        this.message = message;
        return this;
    }
    
    public ApiResponseBuilder data(Object data) {
        this.data = data;
        return this;
    }
    
    public ApiResponseBuilder code(Integer code) {
        this.code = code;
        return this;
    }
    
    public ApiResponse<Object> build() {
        if (code != null) {
            return new ApiResponse<>(success, message, data, code);
        }
        return new ApiResponse<>(success, message, data);
    }
}