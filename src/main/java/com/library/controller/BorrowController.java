package com.library.controller;

import com.library.ApiResponse;
import com.library.dto.BorrowRequestDTO;
import com.library.entity.BorrowRecord;
import com.library.exception.BusinessException;
import com.library.service.BorrowRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 借阅管理控制器
 */
@RestController
@RequestMapping("/api/borrow-records")
@Tag(name = "借阅管理", description = "图书借阅、归还、续借等操作")
public class BorrowController {
    
    @Autowired
    private BorrowRecordService borrowRecordService;
    
    @PostMapping
    @Operation(summary = "创建借阅记录", description = "借阅一本图书")
    public ResponseEntity<ApiResponse<BorrowRecord>> createBorrowRecord(
            @Valid @RequestBody BorrowRequestDTO borrowRequest) {
        
        try {
            BorrowRecord record = borrowRecordService.createBorrowRecord(borrowRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("图书借阅成功", record));
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw BusinessException.badRequest(e.getMessage());
        }
    }
    
    @GetMapping("/{recordId}")
    @Operation(summary = "获取借阅记录", description = "根据ID获取借阅记录详情")
    public ResponseEntity<ApiResponse<BorrowRecord>> getBorrowRecord(
            @Parameter(description = "借阅记录ID", required = true, example = "1")
            @PathVariable Long recordId) {
        
        BorrowRecord record = borrowRecordService.getBorrowRecordById(recordId);
        return ResponseEntity.ok(ApiResponse.success(record));
    }
    
    @PostMapping("/{recordId}/return")
    @Operation(summary = "归还图书", description = "归还借阅的图书")
    public ResponseEntity<ApiResponse<BorrowRecord>> returnBook(
            @Parameter(description = "借阅记录ID", required = true, example = "1")
            @PathVariable Long recordId) {
        
        try {
            BorrowRecord record = borrowRecordService.returnBook(recordId);
            return ResponseEntity.ok(ApiResponse.success("图书归还成功", record));
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw BusinessException.badRequest(e.getMessage());
        }
    }
    
    @PostMapping("/{recordId}/renew")
    @Operation(summary = "续借图书", description = "续借已借阅的图书")
    public ResponseEntity<ApiResponse<BorrowRecord>> renewBook(
            @Parameter(description = "借阅记录ID", required = true, example = "1")
            @PathVariable Long recordId,
            @Parameter(description = "续借天数", example = "7")
            @RequestParam(defaultValue = "7") Integer additionalDays) {
        
        try {
            BorrowRecord record = borrowRecordService.renewBorrow(recordId, additionalDays);
            return ResponseEntity.ok(ApiResponse.success("图书续借成功", record));
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw BusinessException.badRequest(e.getMessage());
        }
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户的借阅记录", description = "获取指定用户的所有借阅记录")
    public ResponseEntity<ApiResponse<List<BorrowRecord>>> getBorrowRecordsByUser(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long userId) {
        
        List<BorrowRecord> records = borrowRecordService.getBorrowRecordsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(records));
    }
    
    @GetMapping("/user/{userId}/page")
    @Operation(summary = "分页获取用户的借阅记录", description = "分页获取指定用户的借阅记录")
    public ResponseEntity<ApiResponse<Page<BorrowRecord>>> getBorrowRecordsByUserPage(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long userId,
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "borrowDate"));
        Page<BorrowRecord> records = borrowRecordService.getBorrowRecordsByUserId(userId, pageable);
        
        return ResponseEntity.ok(ApiResponse.success(records));
    }
    
    @GetMapping("/book/{bookId}")
    @Operation(summary = "获取图书的借阅记录", description = "获取指定图书的所有借阅记录")
    public ResponseEntity<ApiResponse<List<BorrowRecord>>> getBorrowRecordsByBook(
            @Parameter(description = "图书ID", required = true, example = "1")
            @PathVariable Long bookId) {
        
        List<BorrowRecord> records = borrowRecordService.getBorrowRecordsByBookId(bookId);
        return ResponseEntity.ok(ApiResponse.success(records));
    }
    
    @GetMapping("/current")
    @Operation(summary = "获取当前借阅中的记录", description = "获取所有当前借阅中的记录")
    public ResponseEntity<ApiResponse<List<BorrowRecord>>> getCurrentBorrows() {
        List<BorrowRecord> records = borrowRecordService.getCurrentBorrows();
        return ResponseEntity.ok(ApiResponse.success(records));
    }
    
    @GetMapping("/overdue")
    @Operation(summary = "获取超期的借阅记录", description = "获取所有已超期未归还的记录")
    public ResponseEntity<ApiResponse<List<BorrowRecord>>> getOverdueRecords() {
        List<BorrowRecord> records = borrowRecordService.getOverdueRecords();
        return ResponseEntity.ok(ApiResponse.success(records));
    }
    
    @GetMapping("/due-today")
    @Operation(summary = "获取今日应还的记录", description = "获取今日应归还的借阅记录")
    public ResponseEntity<ApiResponse<List<BorrowRecord>>> getDueTodayRecords() {
        List<BorrowRecord> records = borrowRecordService.getDueTodayRecords();
        return ResponseEntity.ok(ApiResponse.success(records));
    }
    
    @GetMapping("/search")
    @Operation(summary = "搜索借阅记录", description = "根据条件搜索借阅记录")
    public ResponseEntity<ApiResponse<Page<BorrowRecord>>> searchBorrowRecords(
            @Parameter(description = "用户ID")
            @RequestParam(required = false) Long userId,
            @Parameter(description = "图书ID")
            @RequestParam(required = false) Long bookId,
            @Parameter(description = "借阅状态")
            @RequestParam(required = false) BorrowRecord.BorrowStatus status,
            @Parameter(description = "开始日期", example = "2024-01-01")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "结束日期", example = "2024-12-31")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Parameter(description = "页码", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每页大小", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "borrowDate"));
        Page<BorrowRecord> records = borrowRecordService.searchBorrowRecords(
                userId, bookId, status, startDate, endDate, pageable);
        
        return ResponseEntity.ok(ApiResponse.success(records));
    }
    
    @GetMapping("/{recordId}/calculate-fine")
    @Operation(summary = "计算超期罚款", description = "计算借阅记录的超期罚款金额")
    public ResponseEntity<ApiResponse<Double>> calculateFine(
            @Parameter(description = "借阅记录ID", required = true, example = "1")
            @PathVariable Long recordId) {
        
        double fine = borrowRecordService.calculateOverdueFine(recordId);
        return ResponseEntity.ok(ApiResponse.success(fine));
    }
    
    @PostMapping("/{recordId}/pay-fine")
    @Operation(summary = "支付罚款", description = "支付借阅记录的罚款")
    public ResponseEntity<ApiResponse<Void>> payFine(
            @Parameter(description = "借阅记录ID", required = true, example = "1")
            @PathVariable Long recordId,
            @Parameter(description = "支付金额", required = true, example = "5.0")
            @RequestParam Double amount) {
        
        try {
            borrowRecordService.payFine(recordId, amount);
            return ResponseEntity.ok(ApiResponse.success("罚款支付成功"));
        } catch (IllegalArgumentException e) {
            throw BusinessException.badRequest(e.getMessage());
        }
    }
    
    @GetMapping("/statistics")
    @Operation(summary = "借阅统计", description = "获取借阅相关的统计信息")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getBorrowStatistics() {
        Object[] stats = borrowRecordService.getBorrowStatistics();
        
        Map<String, Object> result = Map.of(
            "totalBorrows", stats[0],
            "currentBorrows", stats[1],
            "overdueBorrows", stats[2],
            "totalFines", stats[3]
        );
        
        return ResponseEntity.ok(ApiResponse.success(result));
    }
    
    @GetMapping("/monthly-stats")
    @Operation(summary = "月度借阅统计", description = "获取月度借阅统计数据")
    public ResponseEntity<ApiResponse<List<Object[]>>> getMonthlyBorrowStats() {
        List<Object[]> stats = borrowRecordService.getMonthlyBorrowStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
    
    @GetMapping("/user/{userId}/can-borrow")
    @Operation(summary = "检查用户是否可以借阅", description = "检查用户是否还可以借阅更多图书")
    public ResponseEntity<ApiResponse<Boolean>> canUserBorrowMore(
            @Parameter(description = "用户ID", required = true, example = "1")
            @PathVariable Long userId) {
        
        boolean canBorrow = borrowRecordService.canUserBorrowMore(userId);
        return ResponseEntity.ok(ApiResponse.success(canBorrow));
    }
    
    @GetMapping("/check-borrowed")
    @Operation(summary = "检查用户是否已借阅图书", description = "检查用户是否已经借阅了指定图书且未归还")
    public ResponseEntity<ApiResponse<Boolean>> hasUserBorrowedBook(
            @Parameter(description = "用户ID", required = true, example = "1")
            @RequestParam Long userId,
            @Parameter(description = "图书ID", required = true, example = "1")
            @RequestParam Long bookId) {
        
        boolean hasBorrowed = borrowRecordService.hasUserBorrowedBook(userId, bookId);
        return ResponseEntity.ok(ApiResponse.success(hasBorrowed));
    }
}