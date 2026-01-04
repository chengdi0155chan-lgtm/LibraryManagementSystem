package com.library.exception;

import lombok.Getter;

/**
 * 业务异常类
 */
@Getter
public class BusinessException extends RuntimeException {
    
    private final Integer errorCode;
    
    public BusinessException(String message) {
        super(message);
        this.errorCode = 400; // 默认错误码
    }
    
    public BusinessException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public BusinessException(String message, Throwable cause, Integer errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    // 常见的业务错误
    public static BusinessException notFound(String resource) {
        return new BusinessException(resource + "不存在", 404);
    }
    
    public static BusinessException alreadyExists(String resource) {
        return new BusinessException(resource + "已存在", 409);
    }
    
    public static BusinessException forbidden() {
        return new BusinessException("没有权限访问", 403);
    }
    
    public static BusinessException unauthorized() {
        return new BusinessException("未授权访问", 401);
    }
    
    public static BusinessException badRequest(String message) {
        return new BusinessException(message, 400);
    }
}