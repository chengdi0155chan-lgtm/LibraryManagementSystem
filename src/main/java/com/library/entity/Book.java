package com.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图书实体类
 */
@Entity
@Table(name = "books")
@Data
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity {
    
    /**
     * ISBN号（国际标准书号）
     */
    @Column(unique = true, nullable = false, length = 20)
    private String isbn;
    
    /**
     * 图书标题
     */
    @Column(nullable = false, length = 200)
    private String title;
    
    /**
     * 作者
     */
    @Column(nullable = false, length = 100)
    private String author;
    
    /**
     * 出版社
     */
    @Column(length = 100)
    private String publisher;
    
    /**
     * 出版日期
     */
    @Column(name = "publish_date")
    private String publishDate;
    
    /**
     * 图书分类
     */
    @Column(nullable = false, length = 50)
    private String category;
    
    /**
     * 总册数
     */
    @Column(name = "total_copies")
    private Integer totalCopies = 1;
    
    /**
     * 可借册数
     */
    @Column(name = "available_copies")
    private Integer availableCopies = 1;
    
    /**
     * 书架位置
     */
    @Column(length = 50)
    private String location;
    
    /**
     * 图书状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus status = BookStatus.AVAILABLE;
    
    /**
     * 价格
     */
    private Double price;
    
    /**
     * 描述
     */
    @Column(columnDefinition = "TEXT")
    private String description;
    
    /**
     * 图书状态枚举
     */
    public enum BookStatus {
        AVAILABLE,      // 可借阅
        BORROWED,       // 已借出
        RESERVED,       // 已预约
        MAINTENANCE,    // 维护中
        LOST            // 丢失
    }
    
    // 辅助方法
    
    /**
     * 检查图书是否可借
     */
    public boolean isAvailable() {
        return availableCopies > 0 && status == BookStatus.AVAILABLE;
    }
    
    /**
     * 借出一本书
     */
    public boolean borrowOne() {
        if (isAvailable()) {
            availableCopies--;
            if (availableCopies == 0) {
                status = BookStatus.BORROWED;
            }
            return true;
        }
        return false;
    }
    
    /**
     * 归还一本书
     */
    public void returnOne() {
        availableCopies++;
        if (status == BookStatus.BORROWED && availableCopies > 0) {
            status = BookStatus.AVAILABLE;
        }
    }
    
    /**
     * 获取图书摘要信息
     */
    public String getSummary() {
        return String.format("%s - %s (%s)", title, author, isbn);
    }
}