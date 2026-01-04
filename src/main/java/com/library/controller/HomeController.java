package com.library.controller;

import com.library.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页控制器 - 处理根路径和欢迎页面
 */
@RestController
@Tag(name = "首页", description = "系统首页和欢迎信息")
public class HomeController {
    
    @GetMapping("/")
    @Operation(summary = "系统首页", description = "返回系统欢迎信息和基本导航")
    public ResponseEntity<ApiResponse<Map<String, Object>>> home() {
        Map<String, Object> data = new HashMap<>();
        data.put("system", "图书馆管理系统");
        data.put("version", "1.0.0");
        data.put("timestamp", LocalDateTime.now());
        data.put("description", "基于Spring Boot的图书馆管理系统");
        data.put("developer", "开发团队");
        data.put("links", List.of(
            Map.of("name", "API文档", "url", "/swagger-ui/index.html"),
            Map.of("name", "健康检查", "url", "/api/library/health"),
            Map.of("name", "用户管理", "url", "/api/users"),
            Map.of("name", "图书管理", "url", "/api/books")
        ));
        
        return ResponseEntity.ok(ApiResponse.success("欢迎使用图书馆管理系统", data));
    }
    
    @GetMapping("/api")
    @Operation(summary = "API根路径", description = "返回API根路径信息")
    public ResponseEntity<ApiResponse<Map<String, Object>>> apiRoot() {
        Map<String, Object> data = new HashMap<>();
        data.put("service", "Library Management System API");
        data.put("version", "1.0.0");
        data.put("timestamp", LocalDateTime.now());
        data.put("endpoints", List.of(
            Map.of("method", "GET", "path", "/api/users", "description", "用户管理"),
            Map.of("method", "GET", "path", "/api/books", "description", "图书管理"),
            Map.of("method", "GET", "path", "/api/borrow-records", "description", "借阅管理"),
            Map.of("method", "GET", "path", "/api/library", "description", "综合功能")
        ));
        data.put("documentation", "/swagger-ui/index.html");
        
        return ResponseEntity.ok(ApiResponse.success("API服务正常运行", data));
    }
}