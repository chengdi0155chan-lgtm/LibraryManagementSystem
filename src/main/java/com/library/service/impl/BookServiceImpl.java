package com.library.service.impl;

import com.library.dto.BookDTO;
import com.library.entity.Book;
import com.library.repository.BookRepository;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 图书服务实现类（修复版 - 移除缓存注解）
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Override
    public Book addBook(Book book) {
        // 验证ISBN
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new IllegalArgumentException("ISBN已存在: " + book.getIsbn());
        }
        
        // 设置默认值
        if (book.getTotalCopies() == null || book.getTotalCopies() < 1) {
            book.setTotalCopies(1);
        }
        if (book.getAvailableCopies() == null) {
            book.setAvailableCopies(book.getTotalCopies());
        }
        if (book.getStatus() == null) {
            book.setStatus(Book.BookStatus.AVAILABLE);
        }
        if (!StringUtils.hasText(book.getCategory())) {
            book.setCategory("未分类");
        }
        
        return bookRepository.save(book);
    }
    
    @Override
    public Book updateBook(Long bookId, Book book) {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("图书不存在: " + bookId));
        
        // 更新基本信息（不允许修改ISBN）
        if (StringUtils.hasText(book.getTitle())) {
            existingBook.setTitle(book.getTitle());
        }
        if (StringUtils.hasText(book.getAuthor())) {
            existingBook.setAuthor(book.getAuthor());
        }
        if (StringUtils.hasText(book.getPublisher())) {
            existingBook.setPublisher(book.getPublisher());
        }
        if (StringUtils.hasText(book.getCategory())) {
            existingBook.setCategory(book.getCategory());
        }
        if (StringUtils.hasText(book.getLocation())) {
            existingBook.setLocation(book.getLocation());
        }
        if (book.getPrice() != null) {
            existingBook.setPrice(book.getPrice());
        }
        if (StringUtils.hasText(book.getDescription())) {
            existingBook.setDescription(book.getDescription());
        }
        
        return bookRepository.save(existingBook);
    }
    
    @Override
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("图书不存在: " + bookId));
        
        book.setIsDeleted(true);
        bookRepository.save(book);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Book> searchBooks(String title, String author, String category, Pageable pageable) {
        return bookRepository.searchBooks(title, author, category, null, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookDTOById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("图书不存在: " + bookId));
        
        return convertToDTO(book);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Book> getAvailableBooks() {
        return bookRepository.findAvailableBooks();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Book> getPopularBooks(int limit) {
        // 简单实现：返回最新的图书
        return bookRepository.findAll().stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isBookAvailable(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        return bookOptional.isPresent() && bookOptional.get().isAvailable();
    }
    
    @Override
    public boolean borrowBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("图书不存在: " + bookId));
        
        return book.borrowOne();
    }
    
    @Override
    public void returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("图书不存在: " + bookId));
        
        book.returnOne();
        bookRepository.save(book);
    }
    
    @Override
    public Book updateStock(Long bookId, Integer totalCopies, Integer availableCopies) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("图书不存在: " + bookId));
        
        if (totalCopies != null && totalCopies >= 0) {
            book.setTotalCopies(totalCopies);
        }
        if (availableCopies != null && availableCopies >= 0) {
            book.setAvailableCopies(availableCopies);
            
            // 更新状态
            if (availableCopies == 0) {
                book.setStatus(Book.BookStatus.BORROWED);
            } else if (book.getStatus() == Book.BookStatus.BORROWED) {
                book.setStatus(Book.BookStatus.AVAILABLE);
            }
        }
        
        return bookRepository.save(book);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Object[] getBookStatistics() {
        return bookRepository.countTotalAndAvailableCopies();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Book> getLowStockBooks() {
        return bookRepository.findLowStockBooks();
    }
    
    // 辅助方法：转换为DTO
    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setIsbn(book.getIsbn());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublisher(book.getPublisher());
        dto.setPublishDate(book.getPublishDate());
        dto.setCategory(book.getCategory());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setAvailableCopies(book.getAvailableCopies());
        dto.setLocation(book.getLocation());
        dto.setStatus(book.getStatus().name());
        dto.setPrice(book.getPrice());
        dto.setDescription(book.getDescription());
        
        return dto;
    }
}