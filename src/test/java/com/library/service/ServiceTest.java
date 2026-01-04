package com.library.service;

import com.library.config.TestSecurityConfig;
import com.library.dto.BorrowRequestDTO;
import com.library.entity.Book;
import com.library.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Service层测试
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Import(TestSecurityConfig.class) // 导入测试安全配置
class ServiceTest {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private BorrowRecordService borrowRecordService;
    
    @Autowired
    private LibraryService libraryService;
    
    // ... 其他测试方法保持不变
}