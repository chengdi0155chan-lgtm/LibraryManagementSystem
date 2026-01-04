package com.library.repository;

import com.library.entity.Book;
import com.library.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Repository层测试
 */
@DataJpaTest
@ActiveProfiles("test")
class RepositoryTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Test
    void testUserRepository() {
        System.out.println("=== 测试UserRepository ===");
        
        // 1. 创建测试用户
        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("password123");
        user.setEmail("test@example.com");
        user.setRealName("测试用户");
        
        // 2. 保存用户
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());
        System.out.println("保存用户成功，ID: " + savedUser.getId());
        
        // 3. 根据ID查找
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
        System.out.println("根据ID查找成功");
        
        // 4. 根据用户名查找
        Optional<User> userByUsername = userRepository.findByUsername("testuser");
        assertTrue(userByUsername.isPresent());
        System.out.println("根据用户名查找成功");
        
        // 5. 检查用户名是否存在
        boolean exists = userRepository.existsByUsername("testuser");
        assertTrue(exists);
        System.out.println("用户名存在性检查成功");
        
        // 6. 统计用户数量
        long count = userRepository.count();
        assertTrue(count > 0);
        System.out.println("用户数量: " + count);
    }
    
    @Test
    void testBookRepository() {
        System.out.println("\n=== 测试BookRepository ===");
        
        // 1. 创建测试图书
        Book book = new Book();
        book.setIsbn("978-7-111-55674-7");
        book.setTitle("Spring Boot实战");
        book.setAuthor("张三");
        book.setPublisher("机械工业出版社");
        book.setCategory("计算机");
        book.setTotalCopies(10);
        book.setAvailableCopies(5);
        book.setLocation("A101");
        
        // 2. 保存图书
        Book savedBook = bookRepository.save(book);
        assertNotNull(savedBook.getId());
        System.out.println("保存图书成功，ID: " + savedBook.getId());
        
        // 3. 根据ISBN查找
        Optional<Book> bookByIsbn = bookRepository.findByIsbn("978-7-111-55674-7");
        assertTrue(bookByIsbn.isPresent());
        assertEquals("Spring Boot实战", bookByIsbn.get().getTitle());
        System.out.println("根据ISBN查找成功");
        
        // 4. 检查ISBN是否存在
        boolean exists = bookRepository.existsByIsbn("978-7-111-55674-7");
        assertTrue(exists);
        System.out.println("ISBN存在性检查成功");
        
        // 5. 根据作者查找
        var booksByAuthor = bookRepository.findByAuthorContainingIgnoreCase("张三");
        assertFalse(booksByAuthor.isEmpty());
        System.out.println("根据作者查找成功，找到 " + booksByAuthor.size() + " 本书");
        
        // 6. 统计图书数量
        long count = bookRepository.count();
        assertTrue(count > 0);
        System.out.println("图书数量: " + count);
    }
    
    @Test
    void testBookSearch() {
        System.out.println("\n=== 测试图书搜索 ===");
        
        // 添加一些测试数据
        Book book1 = new Book();
        book1.setIsbn("1111111111111");
        book1.setTitle("Java编程思想");
        book1.setAuthor("李四");
        book1.setCategory("计算机");
        book1.setPublisher("清华大学出版社");
        bookRepository.save(book1);
        
        Book book2 = new Book();
        book2.setIsbn("2222222222222");
        book2.setTitle("Spring实战");
        book2.setAuthor("王五");
        book2.setCategory("计算机");
        book2.setPublisher("电子工业出版社");
        bookRepository.save(book2);
        
        Book book3 = new Book();
        book3.setIsbn("3333333333333");
        book3.setTitle("百年孤独");
        book3.setAuthor("加西亚·马尔克斯");
        book3.setCategory("文学");
        book3.setPublisher("南海出版公司");
        bookRepository.save(book3);
        
        // 测试搜索
        var computerBooks = bookRepository.findByCategory("计算机");
        System.out.println("计算机类图书数量: " + computerBooks.size());
        assertEquals(2, computerBooks.size());
        
        var springBooks = bookRepository.findByTitleContainingIgnoreCase("Spring");
        System.out.println("Spring相关图书数量: " + springBooks.size());
        assertEquals(1, springBooks.size());
        
        System.out.println("图书搜索测试完成");
    }
}