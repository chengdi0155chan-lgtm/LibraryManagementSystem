package com.library.service;

import com.library.dto.BorrowRequestDTO;
import com.library.entity.Book;
import com.library.entity.BorrowRecord;
import com.library.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 图书馆主服务接口
 */
public interface LibraryService {
    
    /**
     * 用户注册
     */
    User registerUser(User user);
    
    /**
     * 用户登录
     */
    User loginUser(String username, String password);
    
    /**
     * 借阅图书
     */
    BorrowRecord borrowBook(BorrowRequestDTO borrowRequest);
    
    /**
     * 批量借阅图书
     */
    List<BorrowRecord> batchBorrowBooks(Long userId, List<Long> bookIds);
    
    /**
     * 归还图书
     */
    BorrowRecord returnBook(Long recordId);
    
    /**
     * 批量归还图书
     */
    List<BorrowRecord> batchReturnBooks(Long userId, List<Long> recordIds);
    
    /**
     * 续借图书
     */
    BorrowRecord renewBook(Long recordId, Integer additionalDays);
    
    /**
     * 预约图书
     */
    Book reserveBook(Long userId, Long bookId);
    
    /**
     * 取消预约
     */
    void cancelReservation(Long userId, Long bookId);
    
    /**
     * 获取图书馆概览统计
     */
    Map<String, Object> getLibraryOverview();
    
    /**
     * 获取热门推荐图书
     */
    List<Book> getRecommendedBooks(int limit);
    
    /**
     * 获取用户借阅历史
     */
    List<BorrowRecord> getUserBorrowHistory(Long userId);
    
    /**
     * 获取用户当前借阅
     */
    List<BorrowRecord> getUserCurrentBorrows(Long userId);
    
    /**
     * 检查图书可用性
     */
    boolean checkBookAvailability(Long bookId);
    
    /**
     * 发送借阅提醒
     */
    void sendBorrowReminders();
    
    /**
     * 处理超期罚款
     */
    void processOverdueFines();
}