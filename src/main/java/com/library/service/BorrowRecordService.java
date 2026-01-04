package com.library.service;

import com.library.dto.BorrowRequestDTO;
import com.library.entity.BorrowRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * 借阅记录服务接口
 */
public interface BorrowRecordService {
    
    /**
     * 创建借阅记录
     */
    BorrowRecord createBorrowRecord(BorrowRequestDTO borrowRequest);
    
    /**
     * 归还图书
     */
    BorrowRecord returnBook(Long recordId);
    
    /**
     * 续借图书
     */
    BorrowRecord renewBorrow(Long recordId, Integer additionalDays);
    
    /**
     * 根据ID获取借阅记录
     */
    BorrowRecord getBorrowRecordById(Long recordId);
    
    /**
     * 获取用户的所有借阅记录
     */
    List<BorrowRecord> getBorrowRecordsByUserId(Long userId);
    
    /**
     * 分页获取用户的借阅记录
     */
    Page<BorrowRecord> getBorrowRecordsByUserId(Long userId, Pageable pageable);
    
    /**
     * 获取图书的所有借阅记录
     */
    List<BorrowRecord> getBorrowRecordsByBookId(Long bookId);
    
    /**
     * 获取当前借阅中的记录
     */
    List<BorrowRecord> getCurrentBorrows();
    
    /**
     * 获取超期的借阅记录
     */
    List<BorrowRecord> getOverdueRecords();
    
    /**
     * 获取今日应还的记录
     */
    List<BorrowRecord> getDueTodayRecords();
    
    /**
     * 搜索借阅记录
     */
    Page<BorrowRecord> searchBorrowRecords(Long userId, Long bookId, 
                                          BorrowRecord.BorrowStatus status,
                                          LocalDate startDate, LocalDate endDate,
                                          Pageable pageable);
    
    /**
     * 计算超期罚款
     */
    double calculateOverdueFine(Long recordId);
    
    /**
     * 支付罚款
     */
    void payFine(Long recordId, double amount);
    
    /**
     * 获取借阅统计
     */
    Object[] getBorrowStatistics();
    
    /**
     * 获取月度借阅统计
     */
    List<Object[]> getMonthlyBorrowStats();
    
    /**
     * 检查用户是否可以借阅更多图书
     */
    boolean canUserBorrowMore(Long userId);
    
    /**
     * 检查用户是否已借阅该书
     */
    boolean hasUserBorrowedBook(Long userId, Long bookId);
}