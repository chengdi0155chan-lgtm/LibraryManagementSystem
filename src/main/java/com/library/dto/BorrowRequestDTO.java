package com.library.dto;

import lombok.Data;

/**
 * 借阅请求数据传输对象
 */
@Data
public class BorrowRequestDTO {
    
    private Long userId;
    private Long bookId;
    private Integer borrowDays = 30;  // 默认借阅30天
    private String notes;
    
    // 验证请求是否有效
    public boolean isValid() {
        return userId != null && userId > 0 
                && bookId != null && bookId > 0 
                && borrowDays != null && borrowDays > 0;
    }
}