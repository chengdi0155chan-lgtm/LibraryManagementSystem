package com.library.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户数据传输对象
 */
@Data
public class UserDTO {
    
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String realName;
    private String role;
    private String status;
    private Integer maxBorrowLimit;
    private Integer currentBorrowed;
    private Double fineAmount;
    private LocalDateTime createdAt;
    
    // 构造函数
    public UserDTO() {}
    
    public UserDTO(Long id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}