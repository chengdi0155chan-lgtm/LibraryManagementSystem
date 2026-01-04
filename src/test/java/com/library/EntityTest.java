package com.library;

import com.library.entity.Book;
import com.library.entity.User;
import org.junit.jupiter.api.Test;
import com.library.dto.BookDTO;

/**
 * 实体类简单测试
 */
public class EntityTest {
    
    @Test
    public void testUserEntity() {
        System.out.println("=== 测试用户实体 ===");
        
        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("hashed_password");
        user.setEmail("test@example.com");
        user.setRealName("测试用户");
        user.setMaxBorrowLimit(5);
        
        System.out.println("用户名: " + user.getUsername());
        System.out.println("邮箱: " + user.getEmail());
        System.out.println("可借阅更多: " + user.canBorrowMore());
        
        // 测试借阅方法
        user.incrementBorrowCount();
        System.out.println("当前借阅数: " + user.getCurrentBorrowed());
        System.out.println("可借阅更多: " + user.canBorrowMore());
    }
    
    @Test
    public void testBookEntity() {
        System.out.println("\n=== 测试图书实体 ===");
        
        Book book = new Book();
        book.setIsbn("978-7-111-55674-7");
        book.setTitle("Spring Boot实战");
        book.setAuthor("张三");
        book.setPublisher("机械工业出版社");
        book.setCategory("计算机");
        book.setTotalCopies(10);
        book.setAvailableCopies(5);
        book.setLocation("A101");
        
        System.out.println("图书: " + book.getSummary());
        System.out.println("是否可借: " + book.isAvailable());
        
        // 测试借阅和归还
        boolean canBorrow = book.borrowOne();
        System.out.println("借阅成功: " + canBorrow);
        System.out.println("剩余可借: " + book.getAvailableCopies());
        
        book.returnOne();
        System.out.println("归还后剩余可借: " + book.getAvailableCopies());
    }
    
    @Test
    public void testDTO() {
        System.out.println("\n=== 测试DTO ===");
        
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setIsbn("978-7-111-55674-7");
        bookDTO.setTitle("测试图书");
        bookDTO.setAuthor("测试作者");
        bookDTO.setAvailableCopies(5);
        bookDTO.setStatus("AVAILABLE");
        
        System.out.println("DTO图书: " + bookDTO.getTitle());
        System.out.println("是否可借: " + bookDTO.isAvailable());
    }
}