package com.library.service.impl;

import com.library.dto.BorrowRequestDTO;
import com.library.entity.Book;
import com.library.entity.BorrowRecord;
import com.library.entity.User;
import com.library.repository.BookRepository;
import com.library.repository.BorrowRecordRepository;
import com.library.repository.UserRepository;
import com.library.service.BookService;
import com.library.service.BorrowRecordService;
import com.library.service.LibraryService;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 图书馆主服务实现类
 */
@Service
@Transactional
public class LibraryServiceImpl implements LibraryService {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private BorrowRecordService borrowRecordService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private BorrowRecordRepository borrowRecordRepository;
    
    @Override
    public User registerUser(User user) {
        return userService.createUser(user);
    }
    
    @Override
    public User loginUser(String username, String password) {
        boolean valid = userService.validateUserCredentials(username, password);
        if (!valid) {
            throw new IllegalArgumentException("用户名或密码错误");
        }
        
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        
        // 更新最后登录时间
        userService.updateLastLogin(user.getId());
        
        return user;
    }
    
    @Override
    public BorrowRecord borrowBook(BorrowRequestDTO borrowRequest) {
        return borrowRecordService.createBorrowRecord(borrowRequest);
    }
    
    @Override
    public List<BorrowRecord> batchBorrowBooks(Long userId, List<Long> bookIds) {
        List<BorrowRecord> records = new ArrayList<>();
        
        for (Long bookId : bookIds) {
            BorrowRequestDTO request = new BorrowRequestDTO();
            request.setUserId(userId);
            request.setBookId(bookId);
            request.setBorrowDays(30); // 默认30天
            
            try {
                BorrowRecord record = borrowRecordService.createBorrowRecord(request);
                records.add(record);
            } catch (Exception e) {
                // 记录错误但继续处理其他图书
                System.err.println("借阅图书失败: " + bookId + ", 错误: " + e.getMessage());
            }
        }
        
        return records;
    }
    
    @Override
    public BorrowRecord returnBook(Long recordId) {
        return borrowRecordService.returnBook(recordId);
    }
    
    @Override
    public List<BorrowRecord> batchReturnBooks(Long userId, List<Long> recordIds) {
        List<BorrowRecord> records = new ArrayList<>();
        
        for (Long recordId : recordIds) {
            try {
                BorrowRecord record = borrowRecordService.getBorrowRecordById(recordId);
                
                // 验证记录属于该用户
                if (!record.getUser().getId().equals(userId)) {
                    throw new IllegalArgumentException("记录不属于该用户");
                }
                
                BorrowRecord returnedRecord = borrowRecordService.returnBook(recordId);
                records.add(returnedRecord);
            } catch (Exception e) {
                System.err.println("归还图书失败: " + recordId + ", 错误: " + e.getMessage());
            }
        }
        
        return records;
    }
    
    @Override
    public BorrowRecord renewBook(Long recordId, Integer additionalDays) {
        return borrowRecordService.renewBorrow(recordId, additionalDays);
    }
    
    @Override
    public Book reserveBook(Long userId, Long bookId) {
        // 检查用户是否存在
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + userId));
        
        // 检查图书是否存在
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("图书不存在: " + bookId));
        
        // 检查图书是否可借
        if (!book.isAvailable()) {
            throw new IllegalStateException("图书当前不可借阅");
        }
        
        // 简单实现：标记图书为已预约状态
        // 实际应该创建预约记录
        book.setStatus(Book.BookStatus.RESERVED);
        bookRepository.save(book);
        
        return book;
    }
    
    @Override
    public void cancelReservation(Long userId, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("图书不存在: " + bookId));
        
        if (book.getStatus() == Book.BookStatus.RESERVED) {
            book.setStatus(Book.BookStatus.AVAILABLE);
            bookRepository.save(book);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getLibraryOverview() {
        Map<String, Object> overview = new HashMap<>();
        
        // 用户统计
        long totalUsers = userRepository.count();
        long activeUsers = userService.countActiveUsers();
        
        // 图书统计
        long totalBooks = bookRepository.count();
        List<Book> availableBooks = bookService.getAvailableBooks();
        List<Book> lowStockBooks = bookService.getLowStockBooks();
        
        // 借阅统计
        Object[] borrowStats = borrowRecordService.getBorrowStatistics();
        
        overview.put("totalUsers", totalUsers);
        overview.put("activeUsers", activeUsers);
        overview.put("totalBooks", totalBooks);
        overview.put("availableBooks", availableBooks.size());
        overview.put("lowStockBooks", lowStockBooks.size());
        overview.put("totalBorrows", borrowStats[0]);
        overview.put("currentBorrows", borrowStats[1]);
        overview.put("overdueBorrows", borrowStats[2]);
        overview.put("totalFines", borrowStats[3]);
        
        return overview;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Book> getRecommendedBooks(int limit) {
        // 简单实现：返回最新的图书
        // 实际应该根据借阅历史和用户偏好推荐
        return bookRepository.findAll().stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BorrowRecord> getUserBorrowHistory(Long userId) {
        return borrowRecordService.getBorrowRecordsByUserId(userId).stream()
                .filter(BorrowRecord::isReturned)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BorrowRecord> getUserCurrentBorrows(Long userId) {
        return borrowRecordService.getBorrowRecordsByUserId(userId).stream()
                .filter(record -> !record.isReturned())
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean checkBookAvailability(Long bookId) {
        return bookService.isBookAvailable(bookId);
    }
    
    @Override
    public void sendBorrowReminders() {
        // 获取今日应还的记录
        List<BorrowRecord> dueToday = borrowRecordService.getDueTodayRecords();
        
        for (BorrowRecord record : dueToday) {
            // 发送提醒（这里只是打印日志，实际应该发送邮件或短信）
            System.out.println("发送借阅提醒给用户: " + record.getUser().getUsername() + 
                    ", 图书: " + record.getBook().getTitle() + 
                    ", 应还日期: " + record.getDueDate());
        }
        
        // 获取超期记录
        List<BorrowRecord> overdue = borrowRecordService.getOverdueRecords();
        
        for (BorrowRecord record : overdue) {
            System.out.println("发送超期提醒给用户: " + record.getUser().getUsername() + 
                    ", 图书: " + record.getBook().getTitle() + 
                    ", 已超期: " + record.getOverdueDays() + "天");
        }
    }
    
    @Override
    public void processOverdueFines() {
        List<BorrowRecord> overdue = borrowRecordService.getOverdueRecords();
        LocalDate today = LocalDate.now();
        
        for (BorrowRecord record : overdue) {
            // 如果尚未计算罚款
            if (record.getFineAmount() == 0) {
                double fine = borrowRecordService.calculateOverdueFine(record.getId());
                record.setFineAmount(fine);
                borrowRecordRepository.save(record);
                
                // 更新用户罚款总额
                User user = record.getUser();
                user.setFineAmount(user.getFineAmount() + fine);
                userRepository.save(user);
                
                System.out.println("计算罚款: 用户 " + user.getUsername() + 
                        ", 图书 " + record.getBook().getTitle() + 
                        ", 罚款金额: " + fine);
            }
        }
    }
}