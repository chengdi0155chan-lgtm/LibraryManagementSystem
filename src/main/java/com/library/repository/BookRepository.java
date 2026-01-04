package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 图书数据访问接口
 */
@Repository
public interface BookRepository extends BaseRepository<Book, Long> {
    
    /**
     * 根据ISBN查找图书
     */
    Optional<Book> findByIsbn(String isbn);
    
    /**
     * 根据ISBN检查图书是否存在
     */
    boolean existsByIsbn(String isbn);
    
    /**
     * 根据标题查找图书
     */
    List<Book> findByTitle(String title);
    
    /**
     * 根据标题模糊搜索（不区分大小写）
     */
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    /**
     * 根据作者查找图书
     */
    List<Book> findByAuthor(String author);
    
    /**
     * 根据作者模糊搜索（不区分大小写）
     */
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    /**
     * 根据分类查找图书
     */
    List<Book> findByCategory(String category);
    
    /**
     * 根据分类模糊搜索（不区分大小写）
     */
    List<Book> findByCategoryContainingIgnoreCase(String category);
    
    /**
     * 根据出版社查找图书
     */
    List<Book> findByPublisher(String publisher);
    
    /**
     * 根据状态查找图书
     */
    List<Book> findByStatus(Book.BookStatus status);
    
    /**
     * 查找可借阅的图书（有库存且状态为可借）
     */
    List<Book> findByAvailableCopiesGreaterThanAndStatus(Integer availableCopies, Book.BookStatus status);
    
    /**
     * 查找所有可借阅的图书
     */
    @Query("SELECT b FROM Book b WHERE b.availableCopies > 0 AND b.status = 'AVAILABLE'")
    List<Book> findAvailableBooks();
    
    /**
     * 根据多个条件搜索图书（分页）
     */
    @Query("SELECT b FROM Book b WHERE " +
           "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
           "(:category IS NULL OR LOWER(b.category) LIKE LOWER(CONCAT('%', :category, '%'))) AND " +
           "(:publisher IS NULL OR LOWER(b.publisher) LIKE LOWER(CONCAT('%', :publisher, '%')))")
    Page<Book> searchBooks(@Param("title") String title,
                          @Param("author") String author,
                          @Param("category") String category,
                          @Param("publisher") String publisher,
                          Pageable pageable);
    
    /**
     * 统计各类别图书数量
     */
    @Query("SELECT b.category, COUNT(b) FROM Book b GROUP BY b.category ORDER BY COUNT(b) DESC")
    List<Object[]> countBooksByCategory();
    
    /**
     * 统计各出版社图书数量
     */
    @Query("SELECT b.publisher, COUNT(b) FROM Book b WHERE b.publisher IS NOT NULL GROUP BY b.publisher ORDER BY COUNT(b) DESC")
    List<Object[]> countBooksByPublisher();
    
    /**
     * 统计总库存和可借库存
     */
    @Query("SELECT SUM(b.totalCopies), SUM(b.availableCopies) FROM Book b")
    Object[] countTotalAndAvailableCopies();
    
    /**
     * 查找库存不足的图书
     */
    @Query("SELECT b FROM Book b WHERE b.availableCopies < 3 AND b.availableCopies > 0")
    List<Book> findLowStockBooks();
    
    /**
     * 查找无库存的图书
     */
    @Query("SELECT b FROM Book b WHERE b.availableCopies = 0")
    List<Book> findOutOfStockBooks();
    
    /**
     * 根据ISBN列表查找图书
     */
    List<Book> findByIsbnIn(List<String> isbnList);
}