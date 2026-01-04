package com.library;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 统一的API响应格式
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private Integer code; // 状态码，成功时可不传
    
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
        this.code = success ? 200 : 500;
    }
    
    public ApiResponse(boolean success, String message, T data, Integer code) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
        this.code = code;
    }
    
    // 成功响应
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "操作成功", data);
    }
    
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }
    
    public static ApiResponse<Void> success() {
        return new ApiResponse<>(true, "操作成功", null);
    }
    
    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(true, message, null);
    }
    
    // 失败响应
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
    
    public static <T> ApiResponse<T> error(String message, Integer code) {
        return new ApiResponse<>(false, message, null, code);
    }
    
    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }
    
    // 新增：带数据和状态码的错误响应
    public static <T> ApiResponse<T> error(String message, T data, Integer code) {
        return new ApiResponse<>(false, message, data, code);
    }
    
    // 带状态码的响应
    public static <T> ApiResponse<T> of(boolean success, String message, T data) {
        return new ApiResponse<>(success, message, data);
    }
    
    public static <T> ApiResponse<T> of(boolean success, String message, T data, Integer code) {
        return new ApiResponse<>(success, message, data, code);
    }
}