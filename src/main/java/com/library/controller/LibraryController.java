package com.library.controller;

import com.library.ApiResponse;
import com.library.dto.BorrowRequestDTO;
import com.library.entity.Book;
import com.library.entity.BorrowRecord;
import com.library.entity.User;
import com.library.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 图书馆综合功能控制器
 */
@RestController
@RequestMapping("/api/library")
@Tag(name = "图书馆综合", description = "图书馆综合功能接口")
public class LibraryController {
    
    @Autowired
    private LibraryService libraryService;
    
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "新用户注册")
    public ResponseEntity<ApiResponse<User>> registerUser(
            @Valid @RequestBody User user) {
        
        try {
            User registeredUser = libraryService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("用户注册成功", registeredUser));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录验证")
    public ResponseEntity<ApiResponse<User>> loginUser(
            @Parameter(description = "用户名", required = true, example = "admin")
            @RequestParam String username,
            @Parameter(description = "密码", required = true, example = "password123")
            @RequestParam String password) {
        
        try {
            User user = libraryService.loginUser(username, password);
            return ResponseEntity.ok(ApiResponse.success("登录成功", user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PostMapping("/borrow")
    @Operation(summary = "借阅图书", description = "综合借阅图书接口")
    public ResponseEntity<ApiResponse<BorrowRecord>> borrowBook(
            @Valid @RequestBody BorrowRequestDTO borrowRequest) {
        
        try {
            BorrowRecord record = libraryService.borrowBook(borrowRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("图书借阅成功", record));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PostMapping("/batch-borrow")
    @Operation(summary = "批量借阅", description = "批量借阅多本图书")
    public ResponseEntity<ApiResponse<List<BorrowRecord>>> batchBorrow(
            @Parameter(description = "用户ID", required = true, example = "1")
            @RequestParam Long userId,
            @Parameter(description = "图书ID列表", required = true)
            @RequestParam List<Long> bookIds) {
        
        try {
            List<BorrowRecord> records = libraryService.batchBorrowBooks(userId, bookIds);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("批量借阅成功", records));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PostMapping("/return/{recordId}")
    @Operation(summary = "归还图书", description = "归还借阅的图书")
    public ResponseEntity<ApiResponse<BorrowRecord>> returnBook(
            @Parameter(description = "借阅记录ID", required = true, example = "1")
            @PathVariable Long recordId) {
        
        try {
            BorrowRecord record = libraryService.returnBook(recordId);
            return ResponseEntity.ok(ApiResponse.success("图书归还成功", record));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PostMapping("/batch-return")
    @Operation(summary = "批量归还", description = "批量归还多本图书")
    public ResponseEntity<ApiResponse<List<BorrowRecord>>> batchReturn(
            @Parameter(description = "用户ID", required = true, example = "1")
            @RequestParam Long userId,
            @Parameter(description = "借阅记录ID列表", required = true)
            @RequestParam List<Long> recordIds) {
        
        try {
            List<BorrowRecord> records = libraryService.batchReturnBooks(userId, recordIds);
            return ResponseEntity.ok(ApiResponse.success("批量归还成功", records));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PostMapping("/renew/{recordId}")
    @Operation(summary = "续借图书", description = "续借已借阅的图书")
    public ResponseEntity<ApiResponse<BorrowRecord>> renewBook(
            @Parameter(description = "借阅记录ID", required = true, example = "1")
            @PathVariable Long recordId,
            @Parameter(description = "续借天数", example = "7")
            @RequestParam(defaultValue = "7") Integer additionalDays) {
        
        try {
            BorrowRecord record = libraryService.renewBook(recordId, additionalDays);
            return ResponseEntity.ok(ApiResponse.success("图书续借成功", record));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PostMapping("/reserve")
    @Operation(summary = "预约图书", description = "预约当前不可借的图书")
    public ResponseEntity<ApiResponse<Book>> reserveBook(
            @Parameter(description = "用户ID", required = true, example = "1")
            @RequestParam Long userId,
            @Parameter(description = "图书ID", required = true, example = "1")
            @RequestParam Long bookId) {
        
        try {
            Book book = libraryService.reserveBook(userId, bookId);
            return ResponseEntity.ok(ApiResponse.success("图书预约成功", book));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @DeleteMapping("/reserve")
    @Operation(summary = "取消预约", description = "取消已预约的图书")
    public ResponseEntity<ApiResponse<Void>> cancelReservation(
            @Parameter(description = "用户ID", required = true, example = "1")
            @RequestParam Long userId,
            @Parameter(description = "图书ID", required = true, example = "1")
            @RequestParam Long bookId) {
        
        try {
            libraryService.cancelReservation(userId, bookId);
            return ResponseEntity.ok(ApiResponse.success("预约取消成功"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/overview")
    @Operation(summary = "图书馆概览", description = "获取图书馆整体统计概览")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getLibraryOverview() {
        Map<String, Object> overview = libraryService.getLibraryOverview();
        return ResponseEntity.ok(ApiResponse.success(overview));
    }
    
    @GetMapping("/recommendations")
    @Operation(summary = "获取推荐图书", description = "获取热门推荐图书")
    public ResponseEntity<ApiResponse<List<Book>>> getRecommendedBooks(
            @Parameter(description = "返回数量", example = "10")
            @RequestParam(defaultValue = "10") int limit) {
        
        List<Book> books = libraryService.getRecommendedBooks(limit);
        return ResponseEntity.ok(ApiResponse.success(books));
    }
    
    @GetMapping("/user/{userId}/history")
    @Operation(summary = "获取用户借阅历史", description = "获取用户的借阅历史记录")
    public ResponseEntity<ApiResponse<List<BorrowRecord>>> getUserBorrowHistory(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long userId) {
        
        List<BorrowRecord> history = libraryService.getUserBorrowHistory(userId);
        return ResponseEntity.ok(ApiResponse.success(history));
    }
    
    @GetMapping("/user/{userId}/current-borrows")
    @Operation(summary = "获取用户当前借阅", description = "获取用户当前正在借阅的图书")
    public ResponseEntity<ApiResponse<List<BorrowRecord>>> getUserCurrentBorrows(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long userId) {
        
        List<BorrowRecord> borrows = libraryService.getUserCurrentBorrows(userId);
        return ResponseEntity.ok(ApiResponse.success(borrows));
    }
    
    @GetMapping("/book/{bookId}/availability")
    @Operation(summary = "检查图书可用性", description = "检查图书是否可借")
    public ResponseEntity<ApiResponse<Boolean>> checkBookAvailability(
            @Parameter(description = "图书ID", required = true, example = "1")
            @PathVariable Long bookId) {
        
        boolean available = libraryService.checkBookAvailability(bookId);
        return ResponseEntity.ok(ApiResponse.success(available));
    }
    
    @PostMapping("/send-reminders")
    @Operation(summary = "发送借阅提醒", description = "发送借阅到期和超期提醒")
    public ResponseEntity<ApiResponse<Void>> sendBorrowReminders() {
        libraryService.sendBorrowReminders();
        return ResponseEntity.ok(ApiResponse.success("借阅提醒发送完成"));
    }
    
    @PostMapping("/process-fines")
    @Operation(summary = "处理超期罚款", description = "处理所有超期借阅的罚款")
    public ResponseEntity<ApiResponse<Void>> processOverdueFines() {
        libraryService.processOverdueFines();
        return ResponseEntity.ok(ApiResponse.success("罚款处理完成"));
    }
    
    @GetMapping("/health")
    @Operation(summary = "健康检查", description = "检查系统是否正常运行")
    public ResponseEntity<ApiResponse<Map<String, Object>>> healthCheck() {
        Map<String, Object> health = Map.of(
            "status", "UP",
            "service", "Library Management System",
            "timestamp", System.currentTimeMillis()
        );
        
        return ResponseEntity.ok(ApiResponse.success("系统运行正常", health));
    }
}