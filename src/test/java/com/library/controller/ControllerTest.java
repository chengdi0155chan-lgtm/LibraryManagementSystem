package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.config.TestSecurityConfig;
import com.library.entity.Book;
import com.library.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller层测试
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Import(TestSecurityConfig.class) // 导入测试安全配置
class ControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private User testUser;
    private Book testBook;
    
    @BeforeEach
    void setUp() {
        // 这里可以初始化测试数据
        // 由于使用@Transactional，测试结束后数据会自动回滚
    }
    
    @Test
    void testHealthCheck() throws Exception {
        System.out.println("=== 测试健康检查接口 ===");
        
        mockMvc.perform(get("/api/library/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data.status").value("UP"));
        
        System.out.println("健康检查接口测试通过 ✓");
    }
    
    @Test
    void testUserAPIs() throws Exception {
        System.out.println("=== 测试用户相关API ===");
        
        // 1. 创建用户
        User newUser = new User();
        newUser.setUsername("apitestuser");
        newUser.setPasswordHash("password123");
        newUser.setEmail("apitest@example.com");
        newUser.setRealName("API测试用户");
        
        String userJson = objectMapper.writeValueAsString(newUser);
        
        MvcResult result = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").exists())
                .andReturn();
        
        String response = result.getResponse().getContentAsString();
        System.out.println("创建用户响应: " + response);
        
        // 2. 获取用户列表
        mockMvc.perform(get("/api/users")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content").isArray());
        
        System.out.println("用户API测试通过 ✓");
    }
    
    @Test
    void testBookAPIs() throws Exception {
        System.out.println("=== 测试图书相关API ===");
        
        // 1. 创建图书
        Book newBook = new Book();
        newBook.setIsbn("978-7-111-55674-0");
        newBook.setTitle("API测试图书");
        newBook.setAuthor("API测试作者");
        newBook.setCategory("测试");
        newBook.setTotalCopies(5);
        newBook.setAvailableCopies(3);
        
        String bookJson = objectMapper.writeValueAsString(newBook);
        
        MvcResult result = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").exists())
                .andReturn();
        
        String response = result.getResponse().getContentAsString();
        System.out.println("创建图书响应: " + response);
        
        // 2. 搜索图书
        mockMvc.perform(get("/api/books/search")
                        .param("title", "API测试"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content").isArray());
        
        // 3. 获取可借阅图书
        mockMvc.perform(get("/api/books/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
        
        System.out.println("图书API测试通过 ✓");
    }
    
    @Test
    void testLibraryAPIs() throws Exception {
        System.out.println("=== 测试图书馆综合API ===");
        
        // 1. 获取图书馆概览
        mockMvc.perform(get("/api/library/overview"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists());
        
        // 2. 获取推荐图书
        mockMvc.perform(get("/api/library/recommendations")
                        .param("limit", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
        
        System.out.println("图书馆综合API测试通过 ✓");
    }
    
    @Test
    void testErrorHandling() throws Exception {
        System.out.println("=== 测试错误处理 ===");
        
        // 1. 测试不存在的用户
        mockMvc.perform(get("/api/users/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
        
        // 2. 测试无效的JSON
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ invalid json }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
        
        System.out.println("错误处理测试通过 ✓");
    }
}