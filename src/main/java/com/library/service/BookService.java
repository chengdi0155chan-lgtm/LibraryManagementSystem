package com.library.service;

import com.library.dto.BookDTO;
import com.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 图书服务接口
 */
public interface BookService {
    
    /**
     * 添加新图书
     */
    Book addBook(Book book);
    
    /**
     * 更新图书信息
     */
    Book updateBook(Long bookId, Book book);
    
    /**
     * 删除图书
     */
    void deleteBook(Long bookId);
    
    /**
     * 根据ID获取图书
     */
    Optional<Book> getBookById(Long bookId);
    
    /**
     * 根据ISBN获取图书
     */
    Optional<Book> getBookByIsbn(String isbn);
    
    /**
     * 获取所有图书
     */
    List<Book> getAllBooks();
    
    /**
     * 分页获取图书
     */
    Page<Book> getBooks(Pageable pageable);
    
    /**
     * 搜索图书
     */
    Page<Book> searchBooks(String title, String author, String category, Pageable pageable);
    
    /**
     * 获取图书DTO
     */
    BookDTO getBookDTOById(Long bookId);
    
    /**
     * 获取可借阅的图书
     */
    List<Book> getAvailableBooks();
    
    /**
     * 根据分类获取图书
     */
    List<Book> getBooksByCategory(String category);
    
    /**
     * 获取热门图书
     */
    List<Book> getPopularBooks(int limit);
    
    /**
     * 检查图书是否可借
     */
    boolean isBookAvailable(Long bookId);
    
    /**
     * 借阅图书
     */
    boolean borrowBook(Long bookId);
    
    /**
     * 归还图书
     */
    void returnBook(Long bookId);
    
    /**
     * 更新图书库存
     */
    Book updateStock(Long bookId, Integer totalCopies, Integer availableCopies);
    
    /**
     * 获取图书统计信息
     */
    Object[] getBookStatistics();
    
    /**
     * 获取库存不足的图书
     */
    List<Book> getLowStockBooks();
}