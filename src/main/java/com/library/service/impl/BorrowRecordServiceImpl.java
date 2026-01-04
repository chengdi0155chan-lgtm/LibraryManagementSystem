package com.library.service.impl;

import com.library.dto.BorrowRequestDTO;
import com.library.entity.Book;
import com.library.entity.BorrowRecord;
import com.library.entity.User;
import com.library.repository.BookRepository;
import com.library.repository.BorrowRecordRepository;
import com.library.repository.UserRepository;
import com.library.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 借阅记录服务实现类
 */
@Service
@Transactional
public class BorrowRecordServiceImpl implements BorrowRecordService {
    
    @Autowired
    private BorrowRecordRepository borrowRecordRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Override
    public BorrowRecord createBorrowRecord(BorrowRequestDTO borrowRequest) {
        // 验证请求
        if (!borrowRequest.isValid()) {
            throw new IllegalArgumentException("借阅请求无效");
        }
        
        // 检查用户是否存在
        User user = userRepository.findById(borrowRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + borrowRequest.getUserId()));
        
        // 检查图书是否存在
        Book book = bookRepository.findById(borrowRequest.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("图书不存在: " + borrowRequest.getBookId()));
        
        // 检查用户是否可以借阅
        if (!canUserBorrowMore(user.getId())) {
            throw new IllegalStateException("用户已达到借阅上限");
        }
        
        // 检查图书是否可借
        if (!book.isAvailable()) {
            throw new IllegalStateException("图书不可借阅");
        }
        
        // 检查用户是否已借阅该书
        if (hasUserBorrowedBook(user.getId(), book.getId())) {
            throw new IllegalStateException("用户已借阅该书");
        }
        
        // 创建借阅记录
        BorrowRecord record = new BorrowRecord();
        record.setUser(user);
        record.setBook(book);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(borrowRequest.getBorrowDays()));
        record.setStatus(BorrowRecord.BorrowStatus.BORROWED);
        record.setNotes(borrowRequest.getNotes());
        
        // 更新用户借阅数量
        user.incrementBorrowCount();
        userRepository.save(user);
        
        // 更新图书库存
        boolean borrowed = book.borrowOne();
        if (!borrowed) {
            throw new IllegalStateException("借阅失败：图书库存不足");
        }
        bookRepository.save(book);
        
        return borrowRecordRepository.save(record);
    }
    
    @Override
    public BorrowRecord returnBook(Long recordId) {
        BorrowRecord record = borrowRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("借阅记录不存在: " + recordId));
        
        if (record.isReturned()) {
            throw new IllegalStateException("图书已归还");
        }
        
        // 归还图书
        record.returnBook();
        
        // 检查是否超期并计算罚款
        if (record.isOverdue()) {
            double fine = calculateOverdueFine(recordId);
            record.setFineAmount(fine);
            
            // 更新用户罚款
            User user = record.getUser();
            user.setFineAmount(user.getFineAmount() + fine);
            userRepository.save(user);
        }
        
        // 更新用户借阅数量
        User user = record.getUser();
        user.decrementBorrowCount();
        userRepository.save(user);
        
        // 更新图书库存
        Book book = record.getBook();
        book.returnOne();
        bookRepository.save(book);
        
        return borrowRecordRepository.save(record);
    }
    
    @Override
    public BorrowRecord renewBorrow(Long recordId, Integer additionalDays) {
        BorrowRecord record = borrowRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("借阅记录不存在: " + recordId));
        
        if (record.isReturned()) {
            throw new IllegalStateException("图书已归还，无法续借");
        }
        
        // 检查是否已超期
        if (record.isOverdue()) {
            throw new IllegalStateException("图书已超期，请先归还并支付罚款");
        }
        
        // 续借
        record.setDueDate(record.getDueDate().plusDays(additionalDays));
        
        return borrowRecordRepository.save(record);
    }
    
    @Override
    @Transactional(readOnly = true)
    public BorrowRecord getBorrowRecordById(Long recordId) {
        return borrowRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("借阅记录不存在: " + recordId));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BorrowRecord> getBorrowRecordsByUserId(Long userId) {
        return borrowRecordRepository.findByUserId(userId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<BorrowRecord> getBorrowRecordsByUserId(Long userId, Pageable pageable) {
        return borrowRecordRepository.findByUserId(userId, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BorrowRecord> getBorrowRecordsByBookId(Long bookId) {
        return borrowRecordRepository.findByBookId(bookId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BorrowRecord> getCurrentBorrows() {
        return borrowRecordRepository.findByStatus(BorrowRecord.BorrowStatus.BORROWED);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BorrowRecord> getOverdueRecords() {
        return borrowRecordRepository.findOverdueRecords(LocalDate.now());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BorrowRecord> getDueTodayRecords() {
        return borrowRecordRepository.findDueTodayRecords(LocalDate.now());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<BorrowRecord> searchBorrowRecords(Long userId, Long bookId, 
                                                 BorrowRecord.BorrowStatus status,
                                                 LocalDate startDate, LocalDate endDate,
                                                 Pageable pageable) {
        return borrowRecordRepository.searchBorrowRecords(userId, bookId, status, startDate, endDate, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public double calculateOverdueFine(Long recordId) {
        BorrowRecord record = getBorrowRecordById(recordId);
        
        if (!record.isOverdue()) {
            return 0.0;
        }
        
        int overdueDays = record.getOverdueDays();
        // 罚款规则：每天0.5元
        double dailyFine = 0.5;
        return overdueDays * dailyFine;
    }
    
    @Override
    public void payFine(Long recordId, double amount) {
        BorrowRecord record = getBorrowRecordById(recordId);
        
        if (amount <= 0) {
            throw new IllegalArgumentException("支付金额必须大于0");
        }
        
        if (amount > record.getFineAmount()) {
            throw new IllegalArgumentException("支付金额不能超过罚款金额");
        }
        
        // 更新罚款金额
        record.setFineAmount(record.getFineAmount() - amount);
        borrowRecordRepository.save(record);
        
        // 更新用户罚款金额
        User user = record.getUser();
        user.setFineAmount(Math.max(0, user.getFineAmount() - amount));
        userRepository.save(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Object[] getBorrowStatistics() {
        long totalBorrows = borrowRecordRepository.count();
        long currentBorrows = borrowRecordRepository.findByStatus(BorrowRecord.BorrowStatus.BORROWED).size();
        long overdueBorrows = getOverdueRecords().size();
        double totalFines = borrowRecordRepository.findAll().stream()
                .mapToDouble(BorrowRecord::getFineAmount)
                .sum();
        
        return new Object[]{totalBorrows, currentBorrows, overdueBorrows, totalFines};
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getMonthlyBorrowStats() {
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        return borrowRecordRepository.countBorrowsByMonth();
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean canUserBorrowMore(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + userId));
        
        return user.canBorrowMore();
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean hasUserBorrowedBook(Long userId, Long bookId) {
        return borrowRecordRepository.findByUserIdAndBookIdAndStatus(
                userId, bookId, BorrowRecord.BorrowStatus.BORROWED)
                .isPresent();
    }
}