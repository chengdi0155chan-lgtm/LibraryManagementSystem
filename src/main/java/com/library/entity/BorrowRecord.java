package com.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 借阅记录实体类
 */
@Entity
@Table(name = "borrow_records")
@Data
@EqualsAndHashCode(callSuper = true)
public class BorrowRecord extends BaseEntity {
    
    /**
     * 用户ID（外键）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    /**
     * 图书ID（外键）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    /**
     * 借阅日期
     */
    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;
    
    /**
     * 应还日期
     */
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    
    /**
     * 实际归还日期
     */
    @Column(name = "return_date")
    private LocalDate returnDate;
    
    /**
     * 借阅状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BorrowStatus status = BorrowStatus.BORROWED;
    
    /**
     * 罚款金额
     */
    @Column(name = "fine_amount")
    private Double fineAmount = 0.0;
    
    /**
     * 备注
     */
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    /**
     * 借阅状态枚举
     */
    public enum BorrowStatus {
        BORROWED,   // 借阅中
        RETURNED,   // 已归还
        OVERDUE,    // 超期
        LOST        // 丢失
    }
    
    // 辅助方法
    
    /**
     * 检查是否已归还
     */
    public boolean isReturned() {
        return status == BorrowStatus.RETURNED;
    }
    
    /**
     * 检查是否超期
     */
    public boolean isOverdue() {
        if (isReturned()) {
            return false;
        }
        LocalDate now = LocalDate.now();
        return now.isAfter(dueDate);
    }
    
    /**
     * 计算超期天数
     */
    public int getOverdueDays() {
        if (!isOverdue()) {
            return 0;
        }
        LocalDate now = LocalDate.now();
        return (int) java.time.temporal.ChronoUnit.DAYS.between(dueDate, now);
    }
    
    /**
     * 归还图书
     */
    public void returnBook() {
        this.returnDate = LocalDate.now();
        this.status = BorrowStatus.RETURNED;
        
        // 归还图书时检查是否超期
        if (isOverdue()) {
            this.status = BorrowStatus.OVERDUE;
        }
    }
    
    /**
     * 获取借阅摘要信息
     */
    public String getSummary() {
        return String.format("用户: %s, 图书: %s, 借阅日期: %s", 
                user.getUsername(), book.getTitle(), borrowDate);
    }
}