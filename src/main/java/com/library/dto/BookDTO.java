package com.library.dto;

import lombok.Data;

/**
 * 图书数据传输对象
 */
@Data
public class BookDTO {
    
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String publishDate;
    private String category;
    private Integer totalCopies;
    private Integer availableCopies;
    private String location;
    private String status;
    private Double price;
    private String description;
    
    // 构造函数
    public BookDTO() {}
    
    public BookDTO(Long id, String isbn, String title, String author) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }
    
    // 检查是否可借
    public boolean isAvailable() {
        return availableCopies != null && availableCopies > 0 
                && "AVAILABLE".equals(status);
    }
}