package com.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    
    /**
     * 用户名
     */
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    
    /**
     * 密码（存储加密后的密码）
     */
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    
    /**
     * 邮箱
     */
    @Column(unique = true, length = 100)
    private String email;
    
    /**
     * 电话
     */
    @Column(length = 20)
    private String phone;
    
    /**
     * 真实姓名
     */
    @Column(name = "real_name", length = 50)
    private String realName;
    
    /**
     * 用户角色
     * ADMIN: 管理员
     * USER: 普通用户
     * LIBRARIAN: 图书管理员
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;
    
    /**
     * 用户状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;
    
    /**
     * 最大借阅数量
     */
    @Column(name = "max_borrow_limit")
    private Integer maxBorrowLimit = 5;
    
    /**
     * 当前借阅数量
     */
    @Column(name = "current_borrowed")
    private Integer currentBorrowed = 0;
    
    /**
     * 罚款金额
     */
    @Column(name = "fine_amount")
    private Double fineAmount = 0.0;
    
    /**
     * 最后登录时间
     */
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    
    /**
     * 用户角色枚举
     */
    public enum Role {
        ADMIN,      // 管理员
        LIBRARIAN,  // 图书管理员
        USER        // 普通用户
    }
    
    /**
     * 用户状态枚举
     */
    public enum Status {
        ACTIVE,     // 活跃
        INACTIVE,   // 未激活
        SUSPENDED   // 暂停
    }
    
    // 辅助方法
    
    /**
     * 检查用户是否可以借阅更多书籍
     */
    public boolean canBorrowMore() {
        return currentBorrowed < maxBorrowLimit && status == Status.ACTIVE && fineAmount == 0;
    }
    
    /**
     * 增加借阅数量
     */
    public void incrementBorrowCount() {
        if (currentBorrowed < maxBorrowLimit) {
            currentBorrowed++;
        }
    }
    
    /**
     * 减少借阅数量
     */
    public void decrementBorrowCount() {
        if (currentBorrowed > 0) {
            currentBorrowed--;
        }
    }
}