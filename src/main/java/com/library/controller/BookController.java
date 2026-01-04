package com.library.controller;

import com.library.ApiResponse;
import com.library.dto.BookDTO;
import com.library.entity.Book;
import com.library.exception.BusinessException;
import com.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 图书管理控制器
 */
@RestController
@RequestMapping("/api/books")
@Tag(name = "图书管理", description = "图书相关的CRUD操作")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @PostMapping
    @Operation(summary = "添加新图书", description = "添加一本新图书到图书馆")
    public ResponseEntity<ApiResponse<Book>> addBook(
            @Valid @RequestBody Book book) {
        
        try {
            Book createdBook = bookService.addBook(book);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("图书添加成功", createdBook));
        } catch (IllegalArgumentException e) {
            throw BusinessException.badRequest(e.getMessage());
        }
    }
    
    @GetMapping("/{bookId}")
    @Operation(summary = "获取图书信息", description = "根据图书ID获取图书详细信息")
    public ResponseEntity<ApiResponse<BookDTO>> getBook(
            @Parameter(description = "图书ID", required = true, example = "1")
            @PathVariable Long bookId) {
        
        BookDTO bookDTO = bookService.getBookDTOById(bookId);
        if (bookDTO == null) {
            throw BusinessException.notFound("图书");
        }
        
        return ResponseEntity.ok(ApiResponse.success(bookDTO));
    }
    
    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "根据ISBN获取图书", description = "根据ISBN号获取图书信息")
    public ResponseEntity<ApiResponse<Book>> getBookByIsbn(
            @Parameter(description = "ISBN号", required = true, example = "978-7-111-55674-7")
            @PathVariable String isbn) {
        
        Book book = bookService.getBookByIsbn(isbn)
                .orElseThrow(() -> BusinessException.notFound("图书"));
        
        return ResponseEntity.ok(ApiResponse.success(book));
    }
    
    @PutMapping("/{bookId}")
    @Operation(summary = "更新图书信息", description = "更新指定图书的信息")
    public ResponseEntity<ApiResponse<Book>> updateBook(
            @Parameter(description = "图书ID", required = true, example = "1")
            @PathVariable Long bookId,
            @Valid @RequestBody Book book) {
        
        try {
            Book updatedBook = bookService.updateBook(bookId, book);
            return ResponseEntity.ok(ApiResponse.success("图书更新成功", updatedBook));
        } catch (IllegalArgumentException e) {
            throw BusinessException.badRequest(e.getMessage());
        }
    }
    
    @DeleteMapping("/{bookId}")
    @Operation(summary = "删除图书", description = "软删除指定图书")
    public ResponseEntity<ApiResponse<Void>> deleteBook(
            @Parameter(description = "图书ID", required = true, example = "1")
            @PathVariable Long bookId) {
        
        bookService.deleteBook(bookId);
        return ResponseEntity.ok(ApiResponse.success("图书删除成功"));
    }
    
    @GetMapping
    @Operation(summary = "获取图书列表", description = "分页获取所有图书，支持排序")
    public ResponseEntity<ApiResponse<Page<Book>>> getBooks(
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "排序字段，如：createdAt,desc", example = "id,desc")
            @RequestParam(defaultValue = "id,desc") String sort) {
        
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1]) 
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
        Page<Book> books = bookService.getBooks(pageable);
        
        return ResponseEntity.ok(ApiResponse.success(books));
    }
    
    @GetMapping("/search")
    @Operation(summary = "搜索图书", description = "根据条件搜索图书（支持按标题、作者、分类搜索）")
    public ResponseEntity<ApiResponse<Page<Book>>> searchBooks(
            @Parameter(description = "图书标题")
            @RequestParam(required = false) String title,
            @Parameter(description = "作者")
            @RequestParam(required = false) String author,
            @Parameter(description = "分类")
            @RequestParam(required = false) String category,
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Book> books = bookService.searchBooks(title, author, category, pageable);
        
        return ResponseEntity.ok(ApiResponse.success(books));
    }
    
    @GetMapping("/available")
    @Operation(summary = "获取可借阅的图书", description = "获取所有当前可借阅的图书")
    public ResponseEntity<ApiResponse<List<Book>>> getAvailableBooks() {
        List<Book> books = bookService.getAvailableBooks();
        return ResponseEntity.ok(ApiResponse.success(books));
    }
    
    @GetMapping("/category/{category}")
    @Operation(summary = "按分类获取图书", description = "根据分类获取图书列表")
    public ResponseEntity<ApiResponse<List<Book>>> getBooksByCategory(
            @Parameter(description = "图书分类", required = true, example = "计算机")
            @PathVariable String category) {
        
        List<Book> books = bookService.getBooksByCategory(category);
        return ResponseEntity.ok(ApiResponse.success(books));
    }
    
    @GetMapping("/popular")
    @Operation(summary = "获取热门图书", description = "获取热门推荐图书")
    public ResponseEntity<ApiResponse<List<Book>>> getPopularBooks(
            @Parameter(description = "返回数量", example = "10")
            @RequestParam(defaultValue = "10") int limit) {
        
        List<Book> books = bookService.getPopularBooks(limit);
        return ResponseEntity.ok(ApiResponse.success(books));
    }
    
    @GetMapping("/{bookId}/availability")
    @Operation(summary = "检查图书可用性", description = "检查指定图书是否可借阅")
    public ResponseEntity<ApiResponse<Boolean>> checkAvailability(
            @Parameter(description = "图书ID", required = true, example = "1")
            @PathVariable Long bookId) {
        
        boolean available = bookService.isBookAvailable(bookId);
        return ResponseEntity.ok(ApiResponse.success(available));
    }
    
    @PostMapping("/{bookId}/borrow")
    @Operation(summary = "借阅图书", description = "借阅一本图书（减少库存）")
    public ResponseEntity<ApiResponse<Boolean>> borrowBook(
            @Parameter(description = "图书ID", required = true, example = "1")
            @PathVariable Long bookId) {
        
        boolean success = bookService.borrowBook(bookId);
        if (success) {
            return ResponseEntity.ok(ApiResponse.success("图书借阅成功", true));
        } else {
            return ResponseEntity.ok(ApiResponse.error("图书借阅失败：库存不足", false));
        }
    }
    
    @PostMapping("/{bookId}/return")
    @Operation(summary = "归还图书", description = "归还一本图书（增加库存）")
    public ResponseEntity<ApiResponse<Void>> returnBook(
            @Parameter(description = "图书ID", required = true, example = "1")
            @PathVariable Long bookId) {
        
        bookService.returnBook(bookId);
        return ResponseEntity.ok(ApiResponse.success("图书归还成功"));
    }
    
    @PutMapping("/{bookId}/stock")
    @Operation(summary = "更新图书库存", description = "更新图书的总册数和可借册数")
    public ResponseEntity<ApiResponse<Book>> updateStock(
            @Parameter(description = "图书ID", required = true, example = "1")
            @PathVariable Long bookId,
            @Parameter(description = "总册数")
            @RequestParam(required = false) Integer totalCopies,
            @Parameter(description = "可借册数")
            @RequestParam(required = false) Integer availableCopies) {
        
        Book book = bookService.updateStock(bookId, totalCopies, availableCopies);
        return ResponseEntity.ok(ApiResponse.success("库存更新成功", book));
    }
    
    @GetMapping("/statistics")
    @Operation(summary = "图书统计信息", description = "获取图书相关的统计信息")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getBookStatistics() {
        Object[] stats = bookService.getBookStatistics();
        
        Map<String, Object> result = Map.of(
            "totalCopies", stats[0] != null ? stats[0] : 0,
            "availableCopies", stats[1] != null ? stats[1] : 0
        );
        
        return ResponseEntity.ok(ApiResponse.success(result));
    }
    
    @GetMapping("/low-stock")
    @Operation(summary = "获取库存不足的图书", description = "获取库存少于3本的图书")
    public ResponseEntity<ApiResponse<List<Book>>> getLowStockBooks() {
        List<Book> books = bookService.getLowStockBooks();
        return ResponseEntity.ok(ApiResponse.success(books));
    }
    
    @GetMapping("/categories/stats")
    @Operation(summary = "分类统计", description = "统计各类别图书数量")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getCategoryStats() {
        // 这里可以调用Repository的方法获取分类统计
        // 为了简单，我们返回一个空的列表
        return ResponseEntity.ok(ApiResponse.success(List.of()));
    }
}