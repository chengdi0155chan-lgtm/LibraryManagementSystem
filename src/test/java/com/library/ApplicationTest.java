package com.library;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ApplicationTest {
    
    @Test
    void contextLoads() {
        System.out.println("Spring Boot上下文加载成功！");
        // 简单的断言测试
        assert true;
    }
}